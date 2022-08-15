package com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries

import com.chapeaumoineau.miavortoj.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.presentation.util.DictionaryOrder
import com.chapeaumoineau.miavortoj.feature.words.presentation.util.OrderType

data class DictionariesState(
    val dictionaries: List<Dictionary> = emptyList(),
    val dictionaryOrder: DictionaryOrder = DictionaryOrder.Language(OrderType.Ascending),
    val isOrderSectionVisible: Boolean = false,
    val isDeleteDialogVisible: Boolean = false,
    val dictionaryEdit: Int = -1,
    val deleteConfirmationTextToEnter: String = "Delete",
    val dictionaryDelete: Dictionary? = null)