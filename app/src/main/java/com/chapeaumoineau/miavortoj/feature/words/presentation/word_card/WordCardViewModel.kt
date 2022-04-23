package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word

import android.app.Application
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
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
import com.chapeaumoineau.miavortoj.feature.words.presentation.word_card.WordCardEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WordCardViewModel @Inject constructor(application: Application,
                                            private val wordUseCases: WordUseCases,
                                            private val dictionaryUseCases: DictionaryUseCases,
                                            savedStateHandle: SavedStateHandle): AndroidViewModel(application) {

    private val _word = mutableStateOf(Word("", "", "", "", 0, 0, 0,0, 0, 0, 0, 0))
    val word: State<Word> = _word

    private val _dictionary = mutableStateOf(Dictionary("", "","", 0))
    val dictionary: State<Dictionary> = _dictionary

    private val _language = mutableStateOf(Language.getDefault())
    val language: State<Language> = _language

    private val _category = mutableStateOf(Category.getDefaultCategory())
    val category: State<Category> = _category

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    lateinit var tts: TextToSpeech

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

    /* TODO - Look for API to get word in context */

    /* TODO - Text To Speech */

    fun onEvent(event: WordCardEvent) {
        when(event) {
            is WordCardEvent.EditWord -> {
            }
            is WordCardEvent.PlayWord -> {
                tts = TextToSpeech(getApplication<Application>().applicationContext) {
                    if (it == TextToSpeech.SUCCESS && _language.value.locale != null) {
                        if(tts.isLanguageAvailable(_language.value.locale) == TextToSpeech.LANG_AVAILABLE)
                        tts.language = _language.value.locale
                        tts.setSpeechRate(1.0f)
                        tts.speak(_word.value.targetWord, TextToSpeech.QUEUE_ADD, null)
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