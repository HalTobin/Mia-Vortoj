package com.chapeaumoineau.miavortoj.feature.words.domain.use_case

import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.dictionary.*

data class DictionaryUseCases(val getDictionaries: GetDictionariesUseCase,
                              val deleteDictionary: DeleteDictionaryUseCase,
                              val addDictionary: AddDictionaryUseCase,
                              val getDictionary: GetDictionaryUseCase
)
