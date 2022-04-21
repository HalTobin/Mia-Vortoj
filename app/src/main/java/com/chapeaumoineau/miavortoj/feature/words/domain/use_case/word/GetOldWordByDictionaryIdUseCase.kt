package com.chapeaumoineau.miavortoj.feature.words.domain.use_case.word

import com.chapeaumoineau.miavortoj.feature.words.domain.model.Word
import com.chapeaumoineau.miavortoj.feature.words.domain.repository.WordRepository

class GetOldWordByDictionaryId(private val repository: WordRepository) {
    suspend operator fun invoke(dictionaryId: Int): Word? {
        return repository.getOldWordByDictionaryId(dictionaryId)
    }
}