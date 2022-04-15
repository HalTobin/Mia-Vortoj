package com.chapeaumoineau.miavortoj.feature.words.domain.use_case

import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.favorite_language.*

data class FavoriteLanguageUseCases(val getFavoriteLanguages: GetFavoriteLanguageUseCase,
                                    val deleteFavoriteLanguage: DeleteFavoriteLanguageUseCase,
                                    val addFavoriteLanguage: AddFavoriteLanguageUseCase,
                                    val getFavoriteLanguage: GetFavoriteLanguageUseCase
)
