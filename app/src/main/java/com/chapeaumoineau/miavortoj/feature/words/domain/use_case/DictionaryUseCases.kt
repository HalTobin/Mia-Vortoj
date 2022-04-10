package com.chapeaumoineau.miavortoj.feature.words.domain.use_case

data class DictionaryUseCases(val getDictionaries: GetDictionariesUseCase,
                              val deleteDictionary: DeleteDictionaryUseCase,
                              val addDictionary: AddDictionaryUseCase,
                              val getDictionary: GetDictionaryUseCase)
