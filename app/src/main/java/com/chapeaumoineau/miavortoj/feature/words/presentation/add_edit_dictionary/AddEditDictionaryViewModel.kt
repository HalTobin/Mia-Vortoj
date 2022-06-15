package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapeaumoineau.miavortoj.feature.words.domain.extensions.containsCustom
import com.chapeaumoineau.miavortoj.feature.words.domain.extensions.formatCustom
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.domain.model.FavoriteLanguage
import com.chapeaumoineau.miavortoj.feature.words.domain.model.InvalidDictionaryException
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Language
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.DictionaryUseCases
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.FavoriteLanguageUseCases
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word.AddEditWordEvent
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word.AddEditWordViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.Normalizer
import javax.inject.Inject

@HiltViewModel
class AddEditDictionaryViewModel @Inject constructor(private val dictionaryUseCases: DictionaryUseCases,
                                                     private val favoriteLanguageUseCases: FavoriteLanguageUseCases,
                                                     savedStateHandle: SavedStateHandle): ViewModel() {

    private val _dictionaryTitle = mutableStateOf("")
    val title: State<String> = _dictionaryTitle

    private val _dictionaryDescription = mutableStateOf("")
    val description: State<String> = _dictionaryDescription

    private val _languageSearch = mutableStateOf("")
    val search: State<String> = _languageSearch

    private val _dictionaryLanguage = mutableStateOf(Language.getDefault())
    val dictionaryLanguage: State<Language> = _dictionaryLanguage

    private val _displayedLanguages = mutableStateOf(Language.defaultList)
    val languages: State<List<Language>> = _displayedLanguages

    private val _isLanguageDialogVisible = mutableStateOf(false)
    val dialog: State<Boolean> = _isLanguageDialogVisible

    private val _listFromClass = Language.languagesList
    private val _languagesList = mutableStateOf(_listFromClass)
    val languageList: State<List<Language>> = _languagesList

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var nextLanguageRemoveFromFavorite: FavoriteLanguage? = null

    private var currentDictionaryId: Int? = null

    private var getFavoriteLanguagesJob: Job? = null

    init {
        savedStateHandle.get<Int>("dictionaryId")?.let {dictionaryId ->
            if(dictionaryId != -1) {
                viewModelScope.launch {
                    dictionaryUseCases.getDictionary(dictionaryId)?.also { dictionary ->
                        currentDictionaryId = dictionary.id
                        _dictionaryTitle.value = dictionary.title
                        _dictionaryDescription.value = dictionary.description
                        _dictionaryLanguage.value = Language.getLanguageByIso(dictionary.languageIso)
                    }
                }
            }
            getFavoriteLanguagesJob?.cancel()
            getFavoriteLanguagesJob = favoriteLanguageUseCases.getFavoriteLanguages().onEach { languages ->
                _displayedLanguages.value = Language.getLanguagesFromIsos(FavoriteLanguage.getIsos(languages))
                if(dictionaryId == -1) _dictionaryLanguage.value = Language.getLanguageByIso(languages[3].iso)
                nextLanguageRemoveFromFavorite = languages[0]
                focusLanguage(_dictionaryLanguage.value.iso)
                _eventFlow.emit(UiEvent.ChangeLanguage(_dictionaryLanguage.value))
            }.launchIn(viewModelScope)
        }

    }

    fun onEvent(event: AddEditDictionaryEvent) {
        when(event) {
            // EVENT FOR TITLE TEXT INPUT
            is AddEditDictionaryEvent.EnteredTitle -> {
                _dictionaryTitle.value = event.value
            }

            //EVENT FOR DESCRIPTION TEXT INPUT
            is AddEditDictionaryEvent.EnteredDescription -> {
                _dictionaryDescription.value = event.value
            }

            //EVENT FOR SEARCH FIELD
            is AddEditDictionaryEvent.EnteredSearch -> {
                _languageSearch.value = event.value
                _languagesList.value = _listFromClass.sortedBy { it.translation.formatCustom() }.filter { l -> l.name.containsCustom(event.value) || l.iso.containsCustom(event.value) || l.translation.containsCustom(event.value)}
            }

            is AddEditDictionaryEvent.ChangeLanguage -> {
                _dictionaryLanguage.value = Language.getLanguageByIso(event.language)
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ChangeLanguage(Language.getLanguageByIso(event.language)))
                }
            }

            is AddEditDictionaryEvent.GetLanguageTranslation -> {
                event.listOfIndex.forEach { index ->
                    _listFromClass[index].translation = event.listOfTranslation[index]
                }
                _languagesList.value = _listFromClass.sortedBy { it.translation.formatCustom() }
            }

            is AddEditDictionaryEvent.MoreLanguage -> {
                _languageSearch.value = ""
                _languagesList.value = _listFromClass.sortedBy { it.translation.formatCustom() }
                if(_listFromClass[0].translation.isBlank()) viewModelScope.launch {
                    _eventFlow.emit(UiEvent.InitLanguageTranslations)
                }
                _isLanguageDialogVisible.value = true
            }

            is AddEditDictionaryEvent.OnNewLanguageSelected -> {
                viewModelScope.launch {
                    focusLanguage(event.language)
                }
            }

            is AddEditDictionaryEvent.DismissLanguageDialog -> {
                _isLanguageDialogVisible.value = false
            }

            is AddEditDictionaryEvent.SaveDictionary -> {
                viewModelScope.launch {
                    try {
                        dictionaryUseCases.addDictionary(Dictionary(title = title.value, description = description.value, languageIso = dictionaryLanguage.value.iso, id = currentDictionaryId))
                        _eventFlow.emit(UiEvent.SaveDictionary)
                    } catch(e: InvalidDictionaryException) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = e.message ?: "Couldn't saved dictionary"))
                    }
                }
            }
            else -> {}
        }
    }

    suspend fun focusLanguage(languageToFocusIso: String) {

        if(!_displayedLanguages.value.contains(Language.getLanguageByIso(languageToFocusIso))) {
            favoriteLanguageUseCases.addFavoriteLanguage(FavoriteLanguage(languageToFocusIso, System.currentTimeMillis()))
            nextLanguageRemoveFromFavorite?.let {
                favoriteLanguageUseCases.deleteFavoriteLanguage(it)
            }
        }

        _isLanguageDialogVisible.value = false
        _dictionaryLanguage.value = Language.getLanguageByIso(languageToFocusIso)

        _eventFlow.emit(UiEvent.ChangeLanguage(Language.getLanguageByIso(languageToFocusIso)))

    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String): UiEvent()
        data class ChangeLanguage(val language: Language): UiEvent()
        object SaveDictionary: UiEvent()
        object InitLanguageTranslations: UiEvent()
    }

}