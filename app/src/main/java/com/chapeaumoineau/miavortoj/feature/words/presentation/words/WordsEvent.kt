package com.chapeaumoineau.miavortoj.feature.words.presentation.words

import com.chapeaumoineau.miavortoj.feature.words.domain.model.Word
import com.chapeaumoineau.miavortoj.feature.words.domain.util.WordOrder

sealed class WordsEvent {
    data class Order(val wordOrder: WordOrder): WordsEvent()
    data class DeleteWord(val word: Word): WordsEvent()
    object RestoreWord: WordsEvent()
    object ToggleOrderSection: WordsEvent()
    object ToggleDeleteButton: WordsEvent()
}
