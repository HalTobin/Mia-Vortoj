package com.chapeaumoineau.miavortoj.feature.words.domain.use_case.word

import com.chapeaumoineau.miavortoj.feature.words.domain.model.Word
import com.chapeaumoineau.miavortoj.feature.words.domain.repository.WordRepository
import com.chapeaumoineau.miavortoj.feature.words.domain.util.OrderType
import com.chapeaumoineau.miavortoj.feature.words.domain.util.WordOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetWordsUseCase(private val repository: WordRepository) {

    operator fun invoke(wordOrder: WordOrder = WordOrder.SourceWord(OrderType.Descending)): Flow<List<Word>> {
        return repository.getWords().map {
            words -> when(wordOrder.orderType) {
                is OrderType.Ascending -> {
                    when(wordOrder) {
                        is WordOrder.TargetWord -> words.sortedBy { it.targetWord.lowercase() }
                        is WordOrder.SourceWord -> words.sortedBy { it.sourceWord.lowercase() }
                        is WordOrder.Theme -> words.sortedBy { it.themeId }
                    }
                }
                is OrderType.Descending -> {
                    when(wordOrder) {
                        is WordOrder.TargetWord -> words.sortedByDescending { it.targetWord.lowercase() }
                        is WordOrder.SourceWord -> words.sortedByDescending { it.sourceWord.lowercase() }
                        is WordOrder.Theme -> words.sortedByDescending { it.themeId }
                    }
                }
            }
        }
    }

}