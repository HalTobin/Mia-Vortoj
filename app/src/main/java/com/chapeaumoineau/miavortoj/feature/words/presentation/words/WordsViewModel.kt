package com.chapeaumoineau.miavortoj.feature.words.presentation.words

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Word
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.DictionaryUseCases
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.WordUseCases
import com.chapeaumoineau.miavortoj.feature.words.domain.util.OrderType
import com.chapeaumoineau.miavortoj.feature.words.domain.util.WordOrder
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary.AddEditDictionaryViewModel
import com.chapeaumoineau.miavortoj.feature.words.presentation.components.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _dictionaryTitle = mutableStateOf(TextFieldState(hint = "Enter title..."))
    val title: State<TextFieldState> = _dictionaryTitle

    private val _dictionaryDescription = mutableStateOf(TextFieldState(hint = "Enter a description..."))
    val description: State<TextFieldState> = _dictionaryDescription

    private val _dictionaryLanguage = mutableStateOf(0)
    val dictionaryLanguage: State<Int> = _dictionaryLanguage

    private val _eventFlow = MutableSharedFlow<AddEditDictionaryViewModel.UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentDictionaryId: Int? = null

    init {
        getWords(WordOrder.SourceWord(OrderType.Descending))

        savedStateHandle.get<Int>("dictionaryId")?.let {dictionaryId ->
            if(dictionaryId != -1) {
                viewModelScope.launch {
                    dictionaryUseCases.getDictionary(dictionaryId)?.also { dictionary ->
                        currentDictionaryId = dictionary.id
                        _dictionaryTitle.value = title.value.copy(text = dictionary.title, isHintVisible = false)
                        _dictionaryDescription.value = description.value.copy(text = dictionary.description, isHintVisible = false)
                        _dictionaryLanguage.value = dictionary.language
                    }
                }
            }
        }
    }

    fun onEvent(event: WordsEvent) {
        when(event) {
            is WordsEvent.Order -> {
                if(state.value.wordOrder::class == event.wordOrder::class && state.value.wordOrder.orderType == event.wordOrder.orderType) {
                    return
                }
                getWords(event.wordOrder)
            }
            is WordsEvent.DeleteWord -> {
                viewModelScope.launch {
                    wordUseCases.deleteWord(event.word)
                    recentlyDeletedWord = event.word
                }
            }
            is WordsEvent.RestoreWord -> {
                viewModelScope.launch {
                    wordUseCases.addWord(recentlyDeletedWord ?: return@launch)
                    recentlyDeletedWord = null
                }
            }
            is WordsEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
            }
            is WordsEvent.ToggleDeleteButton -> {
                _state.value = state.value.copy(isDeleteButtonVisible = !state.value.isDeleteButtonVisible)
            }
        }
    }

    private fun getWords(wordOrder: WordOrder) {
        getWordsJob?.cancel()
        getWordsJob = wordUseCases.getWords(wordOrder).onEach {
                words -> _state.value = state.value.copy(words = words, wordOrder = wordOrder)
        }.launchIn(viewModelScope)
    }
}