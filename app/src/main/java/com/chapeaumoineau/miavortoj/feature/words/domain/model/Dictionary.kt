package com.chapeaumoineau.miavortoj.feature.words.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chapeaumoineau.miavortoj.R

@Entity
data class Dictionary (
    val title: String,
    val description: String,
    val languageIso: String,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        fun getDefault(): Dictionary {
            return Dictionary("Title", "Description...", "0_N/A")
        }
    }
}
class InvalidDictionaryException(message: String): Exception(message)