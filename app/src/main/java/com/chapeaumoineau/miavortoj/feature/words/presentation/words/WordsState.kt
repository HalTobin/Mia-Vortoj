package com.chapeaumoineau.miavortoj.feature.words.presentation.words

import com.chapeaumoineau.miavortoj.feature.words.domain.model.Word
import com.chapeaumoineau.miavortoj.feature.words.domain.util.OrderType
import com.chapeaumoineau.miavortoj.feature.words.domain.util.WordOrder

data class WordsState(val words: List<Word> = emptyList(),
                      val wordOrder: WordOrder = WordOrder.SourceWord(OrderType.Ascending),
                      val isOrderSectionVisible: Boolean = false,
                      val isDeleteButtonVisible: Boolean = false)