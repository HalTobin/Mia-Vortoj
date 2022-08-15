package com.chapeaumoineau.miavortoj.feature.quiz.presentation.quiz

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapeaumoineau.miavortoj.domain.model.Category
import com.chapeaumoineau.miavortoj.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.domain.model.Language
import com.chapeaumoineau.miavortoj.domain.model.Word
import com.chapeaumoineau.miavortoj.domain.use_case.DictionaryUseCases
import com.chapeaumoineau.miavortoj.domain.use_case.WordUseCases
import com.chapeaumoineau.miavortoj.domain.util.addMostPertinentWords
import com.chapeaumoineau.miavortoj.feature.quiz.model.GameSet
import com.chapeaumoineau.miavortoj.feature.quiz.model.Rules
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
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

    private val _language = mutableStateOf(Language.getDefault())
    val language: State<Language> = _language

    private val _category = mutableStateOf(Category.getDefaultCategory())
    val category: State<Category> = _category

    private val _userEntry = mutableStateOf("")
    val userEntry: State<String> = _userEntry

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val gameSet = GameSet(rules = Rules(duration = 10, categoryId = Rules.CATEGORY_ALL, difficulty = Rules.DIFFICULTY_ALL))

    init {
        savedStateHandle.get<Int>("dictionaryId")?.let { dictionaryId ->
            if (dictionaryId != -1) {
                viewModelScope.launch {
                    dictionaryUseCases.getDictionary(dictionaryId)?.also { dictionaryFromDb ->
                        _dictionary.value = dictionaryFromDb
                        _language.value = Language.getLanguageByIso(dictionaryFromDb.languageIso)
                        initializeGameSet()
                    }
                }
            }
        }
    }

    private suspend fun initializeGameSet() {
        _dictionary.value.id?.let {
            val words = wordUseCases.getWordsFromDictionary(it).first().toMutableList()

            val gameWords: MutableList<Word> = mutableListOf()
            val duration = if(gameSet.rules.duration > words.size) words.size else gameSet.rules.duration

            gameWords.addMostPertinentWords(words, duration)

            gameSet.setGameWords(gameWords)

            _word.value = gameSet.getWordCurrentWord()
        }
    }

    private suspend fun proceed() {
        _userEntry.value = ""
        val next = gameSet.next()
        if (next != null) _word.value = next
        else _eventFlow.emit(UiEvent.CloseQuiz)
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
                        _word.value.id?.let {
                            wordUseCases.changeWordLastTimestamp(it, System.currentTimeMillis())
                            wordUseCases.changeWordNbPlayed(it, _word.value.nbPlayed+1)
                            wordUseCases.changeWordNbSucceed(it, _word.value.nbSucceed+1)
                        }
                        proceed()
                    }
                }
            }

            is QuizEvent.NextWord -> {
                viewModelScope.launch {
                    _word.value.id?.let {
                        //wordUseCases.changeWordLastTimestamp(it, System.currentTimeMillis())
                        wordUseCases.changeWordNbPlayed(it, _word.value.nbPlayed+1)
                    }
                    proceed()
                }
            }

            else -> {}
        }
    }

    sealed class UiEvent {
        object CloseQuiz: UiEvent()
    }

}