package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapeaumoineau.miavortoj.feature.words.domain.model.*
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.DictionaryUseCases
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.WordUseCases
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary.AddEditDictionaryEvent
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

    private val _wordSource = mutableStateOf(TextFieldState(hint = ""))
    val source: State<TextFieldState> = _wordSource

    private val _wordTarget = mutableStateOf(TextFieldState(hint = ""))
    val target: State<TextFieldState> = _wordTarget

    private val _wordEmote = mutableStateOf(TextFieldState(hint = ""))
    val emote: State<TextFieldState> = _wordEmote

    private val _wordNotes = mutableStateOf(TextFieldState(hint = ""))
    val notes: State<TextFieldState> = _wordNotes

    private val _wordCategory = mutableStateOf(Category.getDefaultCategory())
    val category: State<Category> = _wordCategory

    private val _wordTargetHint = mutableStateOf("")
    val targetHint: State<String> = _wordTargetHint

    private val _color = mutableStateOf(Language.getDefault().getColor())
    val color: State<Color> = _color

    private val _isCategoryDialogVisible = mutableStateOf(false)
    val dialog: State<Boolean> = _isCategoryDialogVisible

    private val _categorySearch = mutableStateOf("")
    val search: State<String> = _categorySearch

    private val _categoryList = mutableStateOf(Category.defaultCategories)
    val categories: State<List<Category>> = _categoryList

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentDictionaryId: Int? = null

    init {
        savedStateHandle.get<Int>("dictionaryId")?.let { dictionaryId ->
            if (dictionaryId != -1) {
                currentDictionaryId = dictionaryId
                viewModelScope.launch {
                    dictionaryUseCases.getDictionary(dictionaryId)?.also { dictionary ->
                        _color.value = Language.getLanguageByIso(dictionary.languageIso).getColor()
                        _wordTargetHint.value = Language.getLanguageByIso(dictionary.languageIso).name
                    }
                }
            }
        }
        savedStateHandle.get<Int>("wordId")?.let { wordId ->
            if (wordId != -1) {
                viewModelScope.launch {
                    wordUseCases.getWord(wordId)?.also { word ->
                        _wordSource.value =
                            source.value.copy(text = word.sourceWord, isHintVisible = false)
                        _wordTarget.value =
                            target.value.copy(text = word.targetWord, isHintVisible = false)
                        _wordEmote.value =
                            emote.value.copy(text = word.emote, isHintVisible = false)
                        _wordNotes.value =
                            notes.value.copy(text = word.notes, isHintVisible = false)
                        _wordCategory.value = Category.getCategoryById(word.themeId)
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

            //EVENT FOR EMOTE TEXT INPUT
            is AddEditWordEvent.EnteredEmote -> {
                _wordEmote.value = emote.value.copy(text = event.value)
            }
            is AddEditWordEvent.ChangeEmoteFocus -> {
                _wordEmote.value = emote.value.copy(isHintVisible = !event.focusState.isFocused && emote.value.text.isBlank())
            }

            //EVENT FOR CATEGORY SELECTION
            is AddEditWordEvent.MoreCategory -> {
                _isCategoryDialogVisible.value = true
            }

            is AddEditWordEvent.OnNewCategorySelected -> {
                _isCategoryDialogVisible.value = false
                _wordCategory.value = Category.getCategoryById(event.category)
            }

            is AddEditWordEvent.DismissCategoryDialog -> {
                _isCategoryDialogVisible.value = false
            }

            is AddEditWordEvent.SaveWord -> {
                viewModelScope.launch {
                    try {
                        wordUseCases.addWord(Word(sourceWord = source.value.text,
                            targetWord = target.value.text,
                            emote = emote.value.text,
                            notes = notes.value.text,
                            themeId = category.value.id,
                            mastery = 0,
                            dictionaryId = currentDictionaryId!!,
                            timestamp = System.currentTimeMillis(),
                            lastTestTimestamp = System.currentTimeMillis()))
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