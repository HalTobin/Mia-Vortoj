package com.chapeaumoineau.miavortoj.feature.words.domain.model

import androidx.compose.ui.graphics.Color
import com.chapeaumoineau.miavortoj.R

sealed class Languages() {
    companion object {
        val languages = listOf(
            "Aucun",
            "Esperanto",
            "English",
            "Français",
            "Español",
            "Italiano",
            "Deutsch",
            "Slovenščina",
            "Русский",
            "日本")

        val flags_simple = listOf(
            R.drawable.globe,
            R.drawable.flag_simple_eo,
            R.drawable.flag_simple_gb,
            R.drawable.flag_simple_fr,
            R.drawable.flag_simple_es,
            R.drawable.flag_simple_it,
            R.drawable.flag_simple_de,
            R.drawable.flag_simple_si,
            R.drawable.flag_simple_ru,
            R.drawable.flag_simple_jp)

        val colors = listOf(
            Color(0xFF5AB8A4),
            Color(0xFF649964),
            Color(0xFF2F4681),
            Color(0xFF2C4594),
            Color(0xFFFFE282),
            Color(0xFF49946D),
            Color(0xFFDD6969),
            Color(0xFF515292),
            Color(0xFF4669AD),
            Color(0xFFFFFFFF)
        )
    }
}