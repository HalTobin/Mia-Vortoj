package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Category
import com.chapeaumoineau.miavortoj.feature.words.domain.model.InvalidDictionaryException
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Language
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Word
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.DictionaryUseCases
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.WordUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditWordViewModel @Inject constructor(private val wordUseCases: WordUseCases,
                                               private val dictionaryUseCases: DictionaryUseCases,
                                               savedStateHandle: SavedStateHandle): ViewModel() {

    private val _wordSource = mutableStateOf("")
    val source: State<String> = _wordSource

    private val _wordTarget = mutableStateOf("")
    val target: State<String> = _wordTarget

    private val _wordEmote = mutableStateOf("")
    val emote: State<String> = _wordEmote

    private val _wordNotes = mutableStateOf("")
    val notes: State<String> = _wordNotes

    private val _wordCategory = mutableStateOf(Category.getDefaultCategory())
    val category: State<Category> = _wordCategory

    private val _wordTargetHint = mutableStateOf("")
    val targetHint: State<String> = _wordTargetHint

    private val _wordDifficulty = mutableStateOf(1)
    val difficulty: State<Int> = _wordDifficulty

    private val _color = mutableStateOf(Language.getDefault().getDarkColor())
    val color: State<Color> = _color

    private val _isCategoryDialogVisible = mutableStateOf(false)
    val dialog: State<Boolean> = _isCategoryDialogVisible

    private val _categorySearch = mutableStateOf("")
    val search: State<String> = _categorySearch

    private val _listFromClass = Category.defaultCategories
    private val _categoryList = mutableStateOf(_listFromClass)
    val categories: State<List<Category>> = _categoryList

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentWordId: Int? = null

    private var currentDictionaryId: Int? = null

    init {
        savedStateHandle.get<Int>("dictionaryId")?.let { dictionaryId ->
            if (dictionaryId != -1) {
                currentDictionaryId = dictionaryId
                viewModelScope.launch {
                    dictionaryUseCases.getDictionary(dictionaryId)?.also { dictionary ->
                        _color.value = Language.getLanguageByIso(dictionary.languageIso).getDarkColor()
                        _wordTargetHint.value = Language.getLanguageByIso(dictionary.languageIso).name
                    }
                }
            }
        }
        savedStateHandle.get<Int>("wordId")?.let { wordId ->
            if (wordId != -1) {
                viewModelScope.launch {
                    wordUseCases.getWord(wordId)?.also { word ->
                        currentWordId = word.id
                        _wordSource.value = word.sourceWord
                            //source.value.copy(text = word.sourceWord, isHintVisible = false)
                        _wordTarget.value = word.targetWord
                            //target.value.copy(text = word.targetWord, isHintVisible = false)
                        _wordEmote.value = word.emote
                            //emote.value.copy(text = word.emote, isHintVisible = false)
                        _wordNotes.value = word.notes
                            //notes.value.copy(text = word.notes, isHintVisible = false)
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
                _wordSource.value = event.value
            }

            // EVENT FOR TARGET TEXT INPUT
            is AddEditWordEvent.EnteredTarget -> {
                _wordTarget.value = event.value
            }

            //EVENT FOR DESCRIPTION TEXT INPUT
            is AddEditWordEvent.EnteredDescription -> {
                _wordNotes.value = event.value
            }

            //EVENT FOR EMOTE TEXT INPUT
            is AddEditWordEvent.EnteredEmote -> {
                _wordEmote.value = event.value
            }

            //EVENT FOR CATEGORY SELECTION
            is AddEditWordEvent.MoreCategory -> {
                _categorySearch.value = ""
                if(_listFromClass[0].translation.isBlank()) viewModelScope.launch {
                    _eventFlow.emit(UiEvent.InitWordTranslations)
                }
                _isCategoryDialogVisible.value = true
            }

            is AddEditWordEvent.GetCategoryTranslation -> {
                _listFromClass[event.index].translation = event.translation
            }

            is AddEditWordEvent.EnteredSearch -> {
                _categorySearch.value = event.value
                viewModelScope.launch {
                    _categoryList.value = _listFromClass.sortedBy { it.translation }.filter { l -> l.title.contains(event.value) || l.translation.contains(event.value)}
                }
            }

            is AddEditWordEvent.OnNewCategorySelected -> {
                _isCategoryDialogVisible.value = false
                _wordCategory.value = Category.getCategoryById(event.category)
            }

            is AddEditWordEvent.DismissCategoryDialog -> {
                _isCategoryDialogVisible.value = false
            }

            //EVENT FOR DIFFICULTY
            is AddEditWordEvent.ChangeDifficulty -> {
                _wordDifficulty.value = event.difficulty
            }

            is AddEditWordEvent.SaveWord -> {
                viewModelScope.launch {
                    try {
                        wordUseCases.addWord(Word(sourceWord = source.value,
                            targetWord = target.value,
                            emote = emote.value,
                            notes = notes.value,
                            themeId = category.value.id,
                            difficulty = difficulty.value,
                            mastery = 0,
                            dictionaryId = currentDictionaryId!!,
                            timestamp = System.currentTimeMillis(),
                            lastTestTimestamp = System.currentTimeMillis(),
                            id = currentWordId))
                        _eventFlow.emit(UiEvent.SaveWord)
                    } catch(e: InvalidDictionaryException) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = e.message ?: "Couldn't saved word"))
                    }
                }
            }
            else -> {}
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String): UiEvent()
        object SaveWord: UiEvent()
        object InitWordTranslations: UiEvent()
    }

}