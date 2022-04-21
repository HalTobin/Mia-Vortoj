package com.chapeaumoineau.miavortoj.feature.words.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word (
    val sourceWord: String,
    val targetWord: String,
    val emote: String,
    val notes: String,
    val mastery: Int,
    val timestamp: Long,
    val lastTestTimestamp: Long,
    val dictionaryId: Int,
    val themeId: Int,
    @PrimaryKey val id: Int? = null
) {

    fun isValid(input: String): Boolean {
        return (removeSpace(input.lowercase()) == removeSpace(targetWord.lowercase()))
    }

    companion object {
        fun removeSpace(input: String): String {
            return input.replace("\\s".toRegex(),"")
        }
    }

}