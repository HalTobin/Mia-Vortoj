package com.chapeaumoineau.miavortoj.feature.words.domain.use_case

import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.word.*

data class WordUseCases(val getWords: GetWordsUseCase,
                        val getWordsFromDictionary: GetWordsByDictionaryUseCase,
                        val getOldWordByDictionaryId: GetOldWordByDictionaryIdUseCase,
                        val deleteWordsFromDictionary: DeleteWordsFromDictionaryUseCase,
                        val deleteWord: DeleteWordUseCase,
                        val changeWordLastTimestamp: ChangeWordLastTimestampUseCase,
                        val addWord: AddWordUseCase,
                        val getWord: GetWordUseCase
)
