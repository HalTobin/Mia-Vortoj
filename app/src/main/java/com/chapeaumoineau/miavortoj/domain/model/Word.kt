package com.chapeaumoineau.miavortoj.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chapeaumoineau.miavortoj.domain.extensions.equalsCustom

@Entity
data class Word (
    val sourceWord: String,
    val targetWord: String,
    val emote: String,
    val notes: String,
    val difficulty: Int,
    val mastery: Int,
    val timestamp: Long,
    val lastTestTimestamp: Long,
    val nbPlayed: Int? = 0,
    val nbSucceed: Int? = 0,
    val dictionaryId: Int,
    val themeId: Int,
    @PrimaryKey val id: Int? = null
) {

    fun isValid(input: String): Boolean {
        return targetWord.equalsCustom(input)
    }

}