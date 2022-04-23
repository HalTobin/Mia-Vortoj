package com.chapeaumoineau.miavortoj.feature.words.presentation.words

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapeaumoineau.miavortoj.feature.words.domain.extensions.*
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Language
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Word
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.DictionaryUseCases
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.WordUseCases
import com.chapeaumoineau.miavortoj.feature.words.domain.util.OrderType
import com.chapeaumoineau.miavortoj.feature.words.domain.util.WordOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsViewModel @Inject constructor(private val wordUseCases: WordUseCases,
                                         private val dictionaryUseCases: DictionaryUseCases,
                                         savedStateHandle: SavedStateHandle): ViewModel() {

    private val _state = mutableStateOf(WordsState())
    val state: State<WordsState> = _state

    private var recentlyDeletedWord: Word? = null

    private var getWordsJob: Job? = null

    private val _dictionaryId = mutableStateOf(0)
    val dictionaryId: State<Int> = _dictionaryId

    private val _dictionaryTitle = mutableStateOf("")
    val title: MutableState<String> = _dictionaryTitle

    private val _dictionaryDescription = mutableStateOf("")
    val description: MutableState<String> = _dictionaryDescription

    private val _dictionaryLanguageIso = mutableStateOf(Language.getDefault().iso)
    val dictionaryLanguageIso: State<String> = _dictionaryLanguageIso

    private val _dictionaryColor = mutableStateOf(Language.getDefault().getDarkColor())
    val color: State<Color> = _dictionaryColor

    private val _dictionaryFlag = mutableStateOf(Language.getDefault().flag)
    val flag: State<Int> = _dictionaryFlag

    private val _isFromSource = mutableStateOf(true)
    val isFromSource: State<Boolean> = _isFromSource

    private val _wordList = mutableStateOf(listOf<Word>())
    val words: State<List<Word>> = _wordList

    private val _wordSearch = mutableStateOf("")
    val search: State<String> = _wordSearch

    private var currentDictionaryId: Int? = null

    init {
        savedStateHandle.get<Int>("dictionaryId")?.let {dictionaryId ->
            if(dictionaryId != -1) {
                currentDictionaryId = dictionaryId
                viewModelScope.launch {
                    dictionaryUseCases.getDictionary(dictionaryId)?.also { dictionary ->
                        val language = Language.getLanguageByIso(dictionary.languageIso)
                        _dictionaryId.value = dictionaryId
                        _dictionaryTitle.value = dictionary.title
                        _dictionaryDescription.value = dictionary.description
                        _dictionaryLanguageIso.value = dictionary.languageIso
                        _dictionaryColor.value = language.getDarkColor()
                        _dictionaryFlag.value = language.flag
                    }
                }
            }
        }
        getWords(currentDictionaryId, WordOrder.Source(OrderType.Ascending), "")
    }

    fun onEvent(event: WordsEvent) {
        when(event) {
            is WordsEvent.Order -> {
                if(state.value.wordOrder::class == event.wordOrder::class && state.value.wordOrder.orderType == event.wordOrder.orderType) {
                    return
                }
                getWords(currentDictionaryId, event.wordOrder, "")
                if (event.wordOrder is WordOrder.Source) _isFromSource.value = true
                if (event.wordOrder is WordOrder.Target) _isFromSource.value = false
                if (event.wordOrder is WordOrder.Theme) _isFromSource.value = true
            }

            //EVENT FOR SEARCH FIELD
            is WordsEvent.EnteredSearch -> {
                _wordSearch.value = event.value
                getWords(currentDictionaryId, state.value.wordOrder, event.value)
            }

            is WordsEvent.DeleteWord -> {
                _state.value = state.value.copy(wordEdit = -1)
                _state.value = state.value.copy(isDeleteDialogVisible = false)
                viewModelScope.launch {
                    wordUseCases.deleteWord(event.word)
                    recentlyDeletedWord = event.word
                }
            }
            is WordsEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
            }
            is WordsEvent.ToggleEditMode -> {
                _state.value = event.wordId?.let { state.value.copy(wordEdit = it) }!!
            }

            //EVENT RELATED TO DELETE DIALOG
            is WordsEvent.ToggleDeleteDialog -> {
                _state.value = state.value.copy(wordEdit = -1)
                _state.value = state.value.copy(wordDelete = event.word)
                _state.value = state.value.copy(isDeleteDialogVisible = true)
            }
            is WordsEvent.DismissDeleteDialog -> {
                _state.value = state.value.copy(isDeleteDialogVisible = false)
            }
        }
    }

    private fun getWords(id: Int?, wordOrder: WordOrder, search: String) {
        getWordsJob?.cancel()
        getWordsJob = id?.let {
            wordUseCases.getWordsFromDictionary(it, wordOrder).onEach {
                words -> _state.value = state.value.copy(words = words.filter { l -> l.sourceWord.containsCustom(search) || l.targetWord.containsCustom(search) }, wordOrder = wordOrder)
            }.launchIn(viewModelScope)
        }
    }
}