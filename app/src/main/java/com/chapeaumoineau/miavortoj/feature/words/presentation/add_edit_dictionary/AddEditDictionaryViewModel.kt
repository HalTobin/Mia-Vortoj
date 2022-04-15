package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapeaumoineau.miavortoj.R
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.domain.model.InvalidDictionaryException
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Language
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.DictionaryUseCases
import com.chapeaumoineau.miavortoj.feature.words.presentation.components.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditDictionaryViewModel @Inject constructor(private val dictionaryUseCases: DictionaryUseCases,
                                                     savedStateHandle: SavedStateHandle): ViewModel() {

    private val _dictionaryTitle = mutableStateOf(TextFieldState(hint = ""))
    val title: State<TextFieldState> = _dictionaryTitle

    private val _dictionaryDescription = mutableStateOf(TextFieldState(hint = ""))
    val description: State<TextFieldState> = _dictionaryDescription

    private val _dictionaryLanguageIso = mutableStateOf(Language.getDefault().iso)
    val dictionaryLanguageIso: State<String> = _dictionaryLanguageIso

    private val _dictionaryLanguageColor = mutableStateOf(Language.getDefault().getColor())
    val dictionaryLanguageColor: State<Color> = _dictionaryLanguageColor

    private val _lastFlags = mutableStateOf(Language.getFlagsFromIsos(Language.DUMB_FAV))
    val flags: State<List<Int>> = _lastFlags

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentDictionaryId: Int? = null

    init {
        savedStateHandle.get<Int>("dictionaryId")?.let {dictionaryId ->
            if(dictionaryId != -1) {
                viewModelScope.launch {
                    dictionaryUseCases.getDictionary(dictionaryId)?.also { dictionary ->
                        currentDictionaryId = dictionary.id
                        _dictionaryTitle.value = title.value.copy(text = dictionary.title, isHintVisible = false)
                        _dictionaryDescription.value = description.value.copy(text = dictionary.description, isHintVisible = false)
                        _dictionaryLanguageIso.value = dictionary.languageIso
                        _dictionaryLanguageColor.value = Language.getLanguageByIso(dictionary.languageIso).getColor()
                        _lastFlags.value = Language.getFlagsFromIsos(Language.DUMB_FAV)
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditDictionaryEvent) {
        when(event) {
            // EVENT FOR TITLE TEXT INPUT
            is AddEditDictionaryEvent.EnteredTitle -> {
                _dictionaryTitle.value = title.value.copy(text = event.value)
            }
            is AddEditDictionaryEvent.ChangeTitleFocus -> {
                _dictionaryTitle.value = title.value.copy(isHintVisible = !event.focusState.isFocused && title.value.text.isBlank())
            }

            //EVENT FOR DESCRIPTION TEXT INPUT
            is AddEditDictionaryEvent.EnteredDescription -> {
                _dictionaryDescription.value = description.value.copy(text = event.value)
            }
            is AddEditDictionaryEvent.ChangeDescriptionFocus -> {
                _dictionaryDescription.value = description.value.copy(isHintVisible = !event.focusState.isFocused && description.value.text.isBlank())
            }

            is AddEditDictionaryEvent.ChangeLanguage -> {
                _dictionaryLanguageIso.value = event.language
                _dictionaryLanguageColor.value = Language.getLanguageByIso(event.language).getColor()
            }

            is AddEditDictionaryEvent.MoreLanguage -> {
                /* TODO */
            }

            is AddEditDictionaryEvent.SaveDictionary -> {
                viewModelScope.launch {
                    try {
                        dictionaryUseCases.addDictionary(Dictionary(title = title.value.text, description = description.value.text, languageIso = dictionaryLanguageIso.value, id = currentDictionaryId))
                        _eventFlow.emit(UiEvent.SaveDictionary)
                    } catch(e: InvalidDictionaryException) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = e.message ?: "Couldn't saved dictionary"))
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String): UiEvent()
        object SaveDictionary: UiEvent()
    }

}