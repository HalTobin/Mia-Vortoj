package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapeaumoineau.miavortoj.feature.words.domain.model.InvalidDictionaryException
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Word
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.WordUseCases
import com.chapeaumoineau.miavortoj.feature.words.presentation.components.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditWordViewModel @Inject constructor(private val wordUseCases: WordUseCases,
                                               savedStateHandle: SavedStateHandle): ViewModel() {

    private val _wordSource = mutableStateOf(TextFieldState(hint = "Enter title..."))
    val source: State<TextFieldState> = _wordSource

    private val _wordTarget = mutableStateOf(TextFieldState(hint = "Enter title..."))
    val target: State<TextFieldState> = _wordTarget

    private val _wordNotes = mutableStateOf(TextFieldState(hint = "Enter a description..."))
    val notes: State<TextFieldState> = _wordNotes

    private val _dictionaryLanguage = mutableStateOf(0)
    val dictionaryLanguage: State<Int> = _dictionaryLanguage

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentDictionaryId: Int? = null

    init {
        savedStateHandle.get<Int>("dictionaryId")?.let {dictionaryId ->
            if(dictionaryId != -1) {
                viewModelScope.launch {
                    wordUseCases.getWord(dictionaryId)?.also { word ->
                        currentDictionaryId = word.id
                        _wordSource.value = source.value.copy(text = word.sourceWord, isHintVisible = false)
                        _wordTarget.value = target.value.copy(text = word.targetWord, isHintVisible = false)
                        _wordNotes.value = notes.value.copy(text = word.notes, isHintVisible = false)
                        //_dictionaryLanguage.value = word.language
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditWordEvent) {
        when(event) {
            // EVENT FOR SOURCE TEXT INPUT
            is AddEditWordEvent.EnteredSource -> {
                _wordSource.value = source.value.copy(text = event.value)
            }
            is AddEditWordEvent.ChangeSourceFocus -> {
                _wordSource.value = source.value.copy(isHintVisible = !event.focusState.isFocused && source.value.text.isBlank())
            }

            // EVENT FOR TARGET TEXT INPUT
            is AddEditWordEvent.EnteredTarget -> {
                _wordTarget.value = target.value.copy(text = event.value)
            }
            is AddEditWordEvent.ChangeTargetFocus -> {
                _wordTarget.value = target.value.copy(isHintVisible = !event.focusState.isFocused && target.value.text.isBlank())
            }

            //EVENT FOR DESCRIPTION TEXT INPUT
            is AddEditWordEvent.EnteredDescription -> {
                _wordNotes.value = notes.value.copy(text = event.value)
            }
            is AddEditWordEvent.ChangeDescriptionFocus -> {
                _wordNotes.value = notes.value.copy(isHintVisible = !event.focusState.isFocused && notes.value.text.isBlank())
            }

            is AddEditWordEvent.ChangeLanguage -> {
                _dictionaryLanguage.value = event.language
            }

            is AddEditWordEvent.SaveDictionary -> {
                viewModelScope.launch {
                    try {
                        wordUseCases.addWord(Word(sourceWord = source.value.text,
                            targetWord = target.value.text,
                            emote = "",
                            notes = notes.value.text,
                            themeId = 0,
                            mastery = 0,
                            dictionaryId = 0))
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