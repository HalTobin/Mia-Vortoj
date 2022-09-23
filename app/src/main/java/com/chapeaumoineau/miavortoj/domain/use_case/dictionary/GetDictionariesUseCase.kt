package com.chapeaumoineau.miavortoj.domain.use_case.dictionary

import com.chapeaumoineau.miavortoj.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.domain.repository.DictionaryRepository
import com.chapeaumoineau.miavortoj.feature.dictionary.util.DictionaryOrder
import com.chapeaumoineau.miavortoj.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetDictionariesUseCase(private val repository: DictionaryRepository) {

    operator fun invoke(dictionaryOrder: DictionaryOrder = DictionaryOrder.Words(OrderType.Ascending)): Flow<List<Dictionary>> {
        return repository.getDictionaries().map {
            dictionaries -> when(dictionaryOrder.orderType) {
                is OrderType.Ascending -> {
                    when(dictionaryOrder) {
                        is DictionaryOrder.Title -> dictionaries.sortedBy { it.title.lowercase() }
                        is DictionaryOrder.Language -> dictionaries.sortedBy { it.languageIso }
                        is DictionaryOrder.Words -> dictionaries.sortedBy { it.nbWords }
                    }
                }
                is OrderType.Descending -> {
                    when(dictionaryOrder) {
                        is DictionaryOrder.Title -> dictionaries.sortedByDescending { it.title.lowercase() }
                        is DictionaryOrder.Language -> dictionaries.sortedByDescending { it.languageIso }
                        is DictionaryOrder.Words -> dictionaries.sortedByDescending { it.nbWords }
                    }
                }
            }
        }
    }

}