package com.chapeaumoineau.miavortoj.domain.use_case.word

import com.chapeaumoineau.miavortoj.domain.model.Word
import com.chapeaumoineau.miavortoj.domain.repository.WordRepository

class GetWordUseCase(private val repository: WordRepository) {
    suspend operator fun invoke(id: Int): Word? {
        return repository.getWordById(id)
    }
}