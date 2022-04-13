package com.chapeaumoineau.miavortoj.feature.words.domain.use_case

import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.word.*

data class WordUseCases(val getWords: GetWordsUseCase,
                        val getWordsFromDictionary: GetWordsFromDictionaryUseCase,
                        val deleteWord: DeleteWordUseCase,
                        val addWord: AddWordUseCase,
                        val getWord: GetWordUseCase
)
