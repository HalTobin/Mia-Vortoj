package com.chapeaumoineau.miavortoj.feature.words.domain.model

import com.chapeaumoineau.miavortoj.R

data class Rules(
    val dictionaryId: Int,
    val duration: Int,
    val categories: ArrayList<Category>,
    val difficulty: Int,
    //val gameRulesId: Int,
    //var translation: String = ""
    ) {

    companion object {

        const val ALL = 0
        const val SOURCE_TO_TARGET_TEXT = 1
        const val TARGET_TO_SOURCE_TEXT = 2
        const val TARGET_TO_TARGET_SOUND = 3
        const val TARGET_TO_SOURCE_SOUND = 4

    }
}