package com.chapeaumoineau.miavortoj.feature.words.domain.model

import androidx.compose.ui.graphics.Color
import com.chapeaumoineau.miavortoj.R

data class Language(
    val name: String,
    val iso: String,
    val flag: Int,
    val color: Long,
    val favorite: Boolean,
    val id: Int? = null
) {

    fun getColor(): Color {
        return Color(this.color)
    }

    companion object {
        val languagesList = listOf(
            Language("Aucun", "N/A", R.drawable.globe, 0xFF5AB8A4, false, 0),
            Language("Esperanto", "EPO", R.drawable.flag_simple_eo, 0xFF649964, false, 1),
            Language("English", "ENG", R.drawable.flag_simple_gb, 0xFF49946D, false, 2),
            Language("Français", "FRA", R.drawable.flag_simple_fr, 0xFF2C4594, false, 3),
            Language("Español", "ESP", R.drawable.flag_simple_es, 0xFFFFE282, false, 4),
            Language("Italiano", "ITA", R.drawable.flag_simple_it, 0xFF49946D, false, 5),
            Language("Deutsch", "DEU", R.drawable.flag_simple_de, 0xFFDD6969, false, 6),
            Language("Slovenščina", "SVN", R.drawable.flag_simple_si, 0xFF515292, false, 7),
            Language("Русский", "RUS", R.drawable.flag_simple_ru, 0xFF4669AD, false, 8),
            Language("日本語", "JPN", R.drawable.flag_simple_jp, 0xFFFFFFFF, false, 9),
            Language("Afrikaans", "ZAF", R.drawable.flag_simple_za, 0xFF4B8B74, false, 10),
            Language("Shqiptare", "ALB", R.drawable.flag_simple_al, 0xFFE46869, false, 11),
            Language("العربية", "SAU", R.drawable.flag_simple_sa, 0xFF5B9151, false, 12),
            Language("Հայերեն", "ARM", R.drawable.flag_simple_am, 0xFFFFC967, false, 13),
            Language("Azəri", "AZE", R.drawable.flag_simple_az, 0xFFFFC967, false, 14),
            Language("Euskara", "PV", R.drawable.flag_simple_pv, 0xFF4C946D, false, 15),
            Language("বাংলা", "BGD", R.drawable.flag_simple_bd, 0xFF45685E, false, 16),
            Language("Беларуская", "BLR", R.drawable.flag_simple_by, 0xFFB35353, false, 17),
            Language("မြန်မာ", "MMR", R.drawable.flag_simple_mm, 0xFF75AC75, false, 18),
            Language("Bosanski", "BIH", R.drawable.flag_simple_ba, 0xFFFFDB49, false, 19),
            Language("Български", "BGR", R.drawable.flag_simple_bg, 0xFF5A9182, false, 20),
            Language("Català", "ES-CT", R.drawable.flag_simple_esct, 0xFF5A9182, false, 21),
            Language("Cebuano", "PHL", R.drawable.flag_simple_ph, 0xFFCA6973, false, 22),
            Language("Chichewa", "MWI", R.drawable.flag_simple_mw, 0xFFF36962, false, 23),
            Language("中國語文", "CHN", R.drawable.flag_simple_cn, 0xFFDB6A5A, false, 24),
            Language("සිංහල", "LKA", R.drawable.flag_simple_lk, 0xFFFFD977, false, 25),
            Language("한국어", "KOR", R.drawable.flag_simple_kr, 0xFFFFFFFF, false, 26),
            Language("Corsu", "FR-20R", R.drawable.flag_simple_fr20r, 0xFFFFFFFF, false, 27),
            Language("Norsk", "NOR", R.drawable.flag_simple_no, 0xFFEB7A83, false, 28),
            Language("Dansk", "DNK", R.drawable.flag_simple_dk, 0xFFC2596A, false, 29),
            Language("Svenska", "SWE", R.drawable.flag_simple_se, 0xFF4A83A7, false, 30),
            Language("Suomi", "FIN", R.drawable.flag_simple_fi, 0xFFFFFFFF, false, 31),
            Language("Українська", "UKR", R.drawable.flag_simple_ua, 0xFFFFEA77, false, 32),
            Language("Eesti keel", "EST", R.drawable.flag_simple_ee, 0xFF74BFFF, false, 33),
            Language("Latviešu valoda", "LVA", R.drawable.flag_simple_lv, 0xFF9C5A65, false, 34),
            Language("Lietuvių kalba", "LTU", R.drawable.flag_simple_lt, 0xFF548171, false, 35),
        )

        val defaultList = listOf(getDefault(), getDefault(), getDefault(), getDefault())

        val DUMB_FAV = listOf("EPO", "KOR", "UKR", "EST")

        fun getDefault(): Language {
            return languagesList[0]
        }

        fun getLanguagesFromIsos(isos: List<String>): ArrayList<Language> {
            val myLanguages = arrayListOf<Language>()
            for (iso in isos) {
                myLanguages.add(getLanguageByIso(iso))
            }
            return myLanguages
        }

        fun getLanguageByIso(iso: String): Language {
            var selectLanguage: Language = languagesList[0]
            var keepLooking = true
            var index = 0

            while(keepLooking) {
                if(languagesList[index].iso == iso) {
                    keepLooking = false
                    selectLanguage = languagesList[index]
                }
                if(index >= languagesList.size -1) keepLooking = false
                else index++
            }

            return selectLanguage
        }

        fun getFlagsFromIsos(isos: List<String>): ArrayList<Int> {
            val myFlags = arrayListOf<Int>()
            for (iso in isos) {
                myFlags.add(this.getLanguageByIso(iso).flag)
            }
            return myFlags
        }

        val test: Color = Color(0xFF74BFFF)

    }
}