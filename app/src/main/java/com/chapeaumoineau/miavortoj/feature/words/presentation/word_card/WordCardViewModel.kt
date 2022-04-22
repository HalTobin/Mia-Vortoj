package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapeaumoineau.miavortoj.R
import com.chapeaumoineau.miavortoj.feature.words.domain.model.*
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.DictionaryUseCases
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.WordUseCases
import com.chapeaumoineau.miavortoj.feature.words.presentation.word_card.WordCardEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordCardViewModel @Inject constructor(private val wordUseCases: WordUseCases,
                                           private val dictionaryUseCases: DictionaryUseCases,
                                           savedStateHandle: SavedStateHandle): ViewModel() {

    private val _word = mutableStateOf(Word("", "", "", "", 0, 0, 0,0, 0, 0, 0, 0))
    val word: State<Word> = _word

    private val _dictionary = mutableStateOf(Dictionary("", "","", 0))
    val dictionary: State<Dictionary> = _dictionary

    private val _language = mutableStateOf(Language("", "", R.drawable.globe, 0xFFFFFFFF, 0xFF00000000,false))
    val language: State<Language> = _language

    private val _category = mutableStateOf(Category("", R.drawable.theme_other, 0))
    val category: State<Category> = _category

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("wordId")?.let { wordId ->
            if (wordId != -1) {
                viewModelScope.launch {
                    wordUseCases.getWord(wordId)?.also { wordDb ->
                        _word.value = wordDb
                        _category.value = Category.getCategoryById(wordDb.themeId)
                        dictionaryUseCases.getDictionary(wordDb.dictionaryId)?.also { dictionaryDb ->
                            _dictionary.value = dictionaryDb
                            _language.value = Language.getLanguageByIso(dictionaryDb.languageIso)
                        }
                    }
                }
            }
        }
    }

    /* TODO - Refresh the card after word edit */

    /* TODO - Scraping from reverso / deepl | Look for API */

    /* TODO - Text To Speech */

    fun onEvent(event: WordCardEvent) {
        when(event) {
            is WordCardEvent.EditWord -> {
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String): UiEvent()
        object SaveWord: UiEvent()
    }

}