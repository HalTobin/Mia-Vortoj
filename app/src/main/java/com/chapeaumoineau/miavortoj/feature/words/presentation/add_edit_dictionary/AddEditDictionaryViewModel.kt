package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.domain.model.FavoriteLanguage
import com.chapeaumoineau.miavortoj.feature.words.domain.model.InvalidDictionaryException
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Language
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.DictionaryUseCases
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.FavoriteLanguageUseCases
import com.chapeaumoineau.miavortoj.feature.words.presentation.components.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

    private val _languagesList = mutableStateOf(Language.getSortedLanguageList())
    val languageList: State<List<Language>> = _languagesList

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var nextLanguageRemoveFromFavorite: FavoriteLanguage? = null

    private var currentDictionaryId: Int? = null

    private var getFavoriteLanguagesJob: Job? = null

    /* TODO - Focus a language at launch */

    /* TODO - Focus the language selected in the dialog */

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
        }
        getFavoriteLanguagesJob?.cancel()
        getFavoriteLanguagesJob = favoriteLanguageUseCases.getFavoriteLanguages().onEach { languages ->
            _displayedLanguages.value = Language.getLanguagesFromIsos(FavoriteLanguage.getIsos(languages))
            _dictionaryLanguage.value = Language.getLanguageByIso(languages[3].iso)
            nextLanguageRemoveFromFavorite = languages[0]
            _eventFlow.emit(UiEvent.ChangeLanguage(_dictionaryLanguage.value))
        }.launchIn(viewModelScope)
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
                _languagesList.value = Language.getSortedLanguageList().filter { l -> l.name.contains(event.value) || l.iso.contains(event.value)}
            }

            is AddEditDictionaryEvent.ChangeLanguage -> {
                _dictionaryLanguage.value = Language.getLanguageByIso(event.language)
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ChangeLanguage(Language.getLanguageByIso(event.language)))
                }
            }

            is AddEditDictionaryEvent.MoreLanguage -> {
                _isLanguageDialogVisible.value = true
            }

            is AddEditDictionaryEvent.OnNewLanguageSelected -> {
                if(!_displayedLanguages.value.contains(Language.getLanguageByIso(event.language)))
                viewModelScope.launch {
                    favoriteLanguageUseCases.addFavoriteLanguage(FavoriteLanguage(event.language, System.currentTimeMillis()))
                    nextLanguageRemoveFromFavorite?.let {
                        favoriteLanguageUseCases.deleteFavoriteLanguage(it)
                    }
                }
                _isLanguageDialogVisible.value = false
                _dictionaryLanguage.value = Language.getLanguageByIso(event.language)
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ChangeLanguage(Language.getLanguageByIso(event.language)))
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

    sealed class UiEvent {
        data class ShowSnackBar(val message: String): UiEvent()
        data class ChangeLanguage(val language: Language): UiEvent()
        object SaveDictionary: UiEvent()
    }

}