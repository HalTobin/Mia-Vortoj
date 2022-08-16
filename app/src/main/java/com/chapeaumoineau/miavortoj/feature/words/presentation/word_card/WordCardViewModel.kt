package com.chapeaumoineau.miavortoj.feature.words.presentation.word_card

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.chapeaumoineau.miavortoj.domain.model.Category
import com.chapeaumoineau.miavortoj.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.domain.model.Language
import com.chapeaumoineau.miavortoj.domain.model.Word
import com.chapeaumoineau.miavortoj.domain.use_case.DictionaryUseCases
import com.chapeaumoineau.miavortoj.domain.use_case.WordUseCases
import com.chapeaumoineau.miavortoj.util.CustomTextToSpeech
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordCardViewModel @Inject constructor(
    application: Application,
    private val wordUseCases: WordUseCases,
    private val dictionaryUseCases: DictionaryUseCases,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application), CustomTextToSpeech.OnTextToSpeechReady {

    private val _word = mutableStateOf(Word("", "", "", "", 0, 0, 0, 0, 0, 0, 0, 0))
    val word: State<Word> = _word

    private val _dictionary = mutableStateOf(Dictionary("", "", "", 0))
    val dictionary: State<Dictionary> = _dictionary

    private val _language = mutableStateOf(Language.getDefault())
    val language: State<Language> = _language

    private val _category = mutableStateOf(Category.getDefaultCategory())
    val category: State<Category> = _category

    private val _speech = mutableStateOf(false)
    val speech: State<Boolean> = _speech

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var tts = CustomTextToSpeech(this)

    init {
        savedStateHandle.get<Int>("wordId")?.let { wordId ->
            if (wordId != -1) {
                viewModelScope.launch {
                    wordUseCases.getWord(wordId)?.also { wordDb ->
                        _word.value = wordDb
                        _category.value = Category.getCategoryById(wordDb.themeId)
                        dictionaryUseCases.getDictionary(wordDb.dictionaryId)
                            ?.also { dictionaryDb ->
                                _dictionary.value = dictionaryDb
                                _language.value =
                                    Language.getLanguageByIso(dictionaryDb.languageIso)
                                tts.initTextToSpeech(getApplication<Application>().applicationContext, _language.value)
                            }
                    }
                }
            }
        }
    }

    /* TODO - Refresh the card after word edit */

    /* TODO - Look for API to get word in context */

    fun onEvent(event: WordCardEvent) {
        when (event) {
            is WordCardEvent.EditWord -> {

            }
            is WordCardEvent.PlayWord -> {
                tts.speak(_word.value.targetWord)
            }
        }
    }

    sealed class UiEvent {

    }

    override fun onTextToSpeechReady(isTextToSpeechReady: Boolean) {
        _speech.value = isTextToSpeechReady
    }

}