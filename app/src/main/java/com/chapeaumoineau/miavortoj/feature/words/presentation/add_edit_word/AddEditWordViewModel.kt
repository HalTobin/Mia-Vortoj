package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapeaumoineau.miavortoj.feature.words.domain.model.InvalidDictionaryException
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Word
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.DictionaryUseCases
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.WordUseCases
import com.chapeaumoineau.miavortoj.feature.words.presentation.components.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditWordViewModel @Inject constructor(private val wordUseCases: WordUseCases,
                                               private val dictionaryUseCases: DictionaryUseCases,
                                               savedStateHandle: SavedStateHandle): ViewModel() {

    private val _wordSource = mutableStateOf(TextFieldState(hint = "Enter title..."))
    val source: State<TextFieldState> = _wordSource

    private val _wordTarget = mutableStateOf(TextFieldState(hint = "Enter title..."))
    val target: State<TextFieldState> = _wordTarget

    private val _wordNotes = mutableStateOf(TextFieldState(hint = "Enter a description..."))
    val notes: State<TextFieldState> = _wordNotes

    private val _language = mutableStateOf(0)
    val language: State<Int> = _language

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentDictionaryId: Int? = null

    init {
        savedStateHandle.get<Int>("dictionaryId")?.let { dictionaryId ->
            if (dictionaryId != -1) {
                currentDictionaryId = dictionaryId
                viewModelScope.launch {
                    dictionaryUseCases.getDictionary(dictionaryId)?.also { dictionary ->
                        _language.value = dictionary.language
                    }
                }
            }
        }
        savedStateHandle.get<Int>("wordId")?.let { wordId ->
            if (wordId != 1) {
                viewModelScope.launch {
                    wordUseCases.getWord(wordId)?.also { word ->
                        _wordSource.value =
                            source.value.copy(text = word.sourceWord, isHintVisible = false)
                        _wordTarget.value =
                            target.value.copy(text = word.targetWord, isHintVisible = false)
                        _wordNotes.value =
                            notes.value.copy(text = word.notes, isHintVisible = false)
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

            is AddEditWordEvent.SaveDictionary -> {
                viewModelScope.launch {
                    try {
                        wordUseCases.addWord(Word(sourceWord = source.value.text,
                            targetWord = target.value.text,
                            emote = "",
                            notes = notes.value.text,
                            themeId = 0,
                            mastery = 0,
                            dictionaryId = currentDictionaryId!!))
                        _eventFlow.emit(UiEvent.SaveWord)
                    } catch(e: InvalidDictionaryException) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = e.message ?: "Couldn't saved word"))
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String): UiEvent()
        object SaveWord: UiEvent()
    }

}