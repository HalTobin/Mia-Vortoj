package com.chapeaumoineau.miavortoj.feature.words.domain.use_case.dictionary

import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.domain.repository.DictionaryRepository
import com.chapeaumoineau.miavortoj.feature.words.domain.util.DictionaryOrder
import com.chapeaumoineau.miavortoj.feature.words.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetDictionariesUseCase(private val repository: DictionaryRepository) {

    operator fun invoke(dictionaryOrder: DictionaryOrder = DictionaryOrder.Language(OrderType.Ascending)): Flow<List<Dictionary>> {
        return repository.getDictionaries().map {
                dictionaries -> when(dictionaryOrder.orderType) {
                    is OrderType.Ascending -> {
                        when(dictionaryOrder) {
                            is DictionaryOrder.Language -> dictionaries.sortedBy { it.title.lowercase() }
                            is DictionaryOrder.Title -> dictionaries.sortedBy { it.language }
                        }
                    }
                    is OrderType.Descending -> {
                        when(dictionaryOrder) {
                            is DictionaryOrder.Language -> dictionaries.sortedByDescending { it.title.lowercase() }
                            is DictionaryOrder.Title -> dictionaries.sortedByDescending { it.language }
                        }
                    }
            }
        }
    }
}