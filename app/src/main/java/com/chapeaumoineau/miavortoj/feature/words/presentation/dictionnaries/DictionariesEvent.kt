package com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries

import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.domain.util.DictionaryOrder

sealed class DictionariesEvent {
    data class Order(val dictionaryOrder: DictionaryOrder): DictionariesEvent()
    data class DeleteDictionary(val dictionary: Dictionary): DictionariesEvent()
    object RestoreDictionary: DictionariesEvent()
    object ToggleOrderSection: DictionariesEvent()
    object ToggleDeleteButton: DictionariesEvent()
}
