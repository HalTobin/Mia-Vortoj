package com.chapeaumoineau.miavortoj.feature.words.domain.use_case.word

import com.chapeaumoineau.miavortoj.feature.words.domain.model.Word
import com.chapeaumoineau.miavortoj.feature.words.domain.repository.WordRepository

class GetWordUseCase(private val repository: WordRepository) {
    suspend operator fun invoke(id: Int): Word? {
        return repository.getWordById(id)
    }
}