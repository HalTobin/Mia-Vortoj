package com.chapeaumoineau.miavortoj.feature.words.presentation.quiz

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapeaumoineau.miavortoj.R
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Category
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
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
class QuizViewModel @Inject constructor(private val wordUseCases: WordUseCases,
                                        private val dictionaryUseCases: DictionaryUseCases,
                                        savedStateHandle: SavedStateHandle): ViewModel() {

    private val _word = mutableStateOf(Word("", "", "", "", 0, 0, 0,0, 0, 0, 0, 0))
    val word: State<Word> = _word

    private val _dictionary = mutableStateOf(Dictionary("", "","", 0))
    val dictionary: State<Dictionary> = _dictionary

    private val _language = mutableStateOf(Language("", "", R.drawable.globe, 0xFFFFFFFF, 0xFF00000000, false))
    val language: State<Language> = _language

    private val _category = mutableStateOf(Category("", R.drawable.theme_other, 0))
    val category: State<Category> = _category

    private val _userEntry = mutableStateOf("")
    val userEntry: State<String> = _userEntry

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("dictionaryId")?.let { dictionaryId ->
            if (dictionaryId != -1) {
                viewModelScope.launch {
                    dictionaryUseCases.getDictionary(dictionaryId)?.also { dictionaryFromDb ->
                        _dictionary.value = dictionaryFromDb
                        _language.value = Language.getLanguageByIso(dictionaryFromDb.languageIso)
                        findAndSelectOldestWord()
                    }
                }
            }
        }
    }

    private suspend fun findAndSelectOldestWord() {
        _dictionary.value.id?.let {
            wordUseCases.getOldWordByDictionaryId(it)?.also { wordFromDb ->
                _word.value = wordFromDb
                _category.value = Category.getCategoryById(wordFromDb.themeId)
            }
        }
    }

    fun onEvent(event: QuizEvent) {
        when(event) {
            // EVENT FOR USER TEXT INPUT
            is QuizEvent.EnteredEntry -> {
                _userEntry.value = event.value
            }

            is QuizEvent.CheckAnswer -> {
                if(_word.value.isValid(_userEntry.value)) {
                    viewModelScope.launch {
                        _word.value.id?.let { wordUseCases.changeWordLastTimestamp(it, System.currentTimeMillis()) }
                        findAndSelectOldestWord()
                        _userEntry.value = ""
                    }
                }
            }
            else -> {}
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String): UiEvent()
        object SaveWord: UiEvent()
    }

}