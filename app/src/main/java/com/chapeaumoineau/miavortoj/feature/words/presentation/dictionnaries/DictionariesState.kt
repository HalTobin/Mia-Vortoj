package com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries

import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.domain.util.DictionaryOrder
import com.chapeaumoineau.miavortoj.feature.words.domain.util.OrderType

data class DictionariesState(
    val dictionaries: List<Dictionary> = emptyList(),
    val dictionaryOrder: DictionaryOrder = DictionaryOrder.Language(OrderType.Ascending),
    val isOrderSectionVisible: Boolean = false,
    val dictionaryEdit: Int = -1)