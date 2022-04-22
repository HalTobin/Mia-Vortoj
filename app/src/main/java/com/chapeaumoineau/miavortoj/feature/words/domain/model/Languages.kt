package com.chapeaumoineau.miavortoj.feature.words.domain.model

import androidx.compose.ui.graphics.Color
import com.chapeaumoineau.miavortoj.R

data class Language(
    val name: String,
    val iso: String,
    val flag: Int,
    val colorLight: Long,
    val colorDark: Long,
    val favorite: Boolean,
    val id: Int? = null
) {

    fun getLightColor(): Color {
        return Color(this.colorLight)
    }

    fun getDarkColor(): Color {
        return Color(this.colorDark)
    }

    companion object {
        val languagesList = listOf(
            Language("Aucun / Autre", "0_N/A", R.drawable.globe, 0xFF5AB8A4, 0xFF2D404E, false, 0),
            Language("Esperanto", "EPO", R.drawable.flag_simple_eo, 0xFF649964, 0xFF008100,false, 1),
            Language("English", "ENG", R.drawable.flag_simple_gb, 0xFF5876C2, 0xFF00247D,false, 2),
            Language("Français", "FRA", R.drawable.flag_simple_fr, 0xFF2C4594, 0xFF001F85,false, 3),
            Language("Español", "ESP", R.drawable.flag_simple_es, 0xFFFFE282, 0xFF830713,false, 4),
            Language("Italiano", "ITA", R.drawable.flag_simple_it, 0xFF49946D,0xFF016330, false, 5),
            Language("Deutsch", "DEU", R.drawable.flag_simple_de, 0xFFDD6969, 0xFF860000,false, 6),
            Language("Slovenščina", "SVN", R.drawable.flag_simple_si, 0xFF515292, 0xFF272769,false, 7),
            Language("Русский", "RUS", R.drawable.flag_simple_ru, 0xFF4669AD, 0xFF002875,false, 8),
            Language("日本語", "JPN", R.drawable.flag_simple_jp, 0xFFFFFFFF, 0xFF74001C,false, 9),
            Language("Afrikaans", "ZAF", R.drawable.flag_simple_za, 0xFF4B8B74, 0xFF000000,false, 10),
            Language("Shqiptare", "ALB", R.drawable.flag_simple_al, 0xFFE46869, 0xFF000000,false, 11),
            Language("العربية", "SAU", R.drawable.flag_simple_sa, 0xFF5B9151, 0xFF000000,false, 12),
            Language("Հայերեն", "ARM", R.drawable.flag_simple_am, 0xFFFFC967, 0xFF000000,false, 13),
            Language("Azəri", "AZE", R.drawable.flag_simple_az, 0xFFFFC967, 0xFF000000,false, 14),
            Language("Euskara", "PV", R.drawable.flag_simple_pv, 0xFF4C946D, 0xFF000000,false, 15),
            Language("বাংলা", "BGD", R.drawable.flag_simple_bd, 0xFF45685E, 0xFF000000,false, 16),
            Language("Беларуская", "BLR", R.drawable.flag_simple_by, 0xFFB35353, 0xFF000000,false, 17),
            Language("မြန်မာ", "MMR", R.drawable.flag_simple_mm, 0xFF75AC75, 0xFF000000,false, 18),
            Language("Bosanski", "BIH", R.drawable.flag_simple_ba, 0xFFFFDB49, 0xFF000000,false, 19),
            Language("Български", "BGR", R.drawable.flag_simple_bg, 0xFF5A9182, 0xFF000000,false, 20),
            Language("Català", "ES-CT", R.drawable.flag_simple_esct, 0xFF5A9182, 0xFF000000,false, 21),
            Language("Cebuano", "PHL", R.drawable.flag_simple_ph, 0xFFCA6973, 0xFF000000,false, 22),
            Language("Chichewa", "MWI", R.drawable.flag_simple_mw, 0xFFF36962, 0xFF000000,false, 23),
            Language("中國語文", "CHN", R.drawable.flag_simple_cn, 0xFFDB6A5A, 0xFF000000,false, 24),
            Language("සිංහල", "LKA", R.drawable.flag_simple_lk, 0xFFFFD977, 0xFF000000,false, 25),
            Language("한국어", "KOR", R.drawable.flag_simple_kr, 0xFFFFFFFF, 0xFF000000,false, 26),
            Language("Corsu", "FR-20R", R.drawable.flag_simple_fr20r, 0xFFFFFFFF, 0xFF000000,false, 27),
            Language("Norsk", "NOR", R.drawable.flag_simple_no, 0xFFEB7A83, 0xFF000000,false, 28),
            Language("Dansk", "DNK", R.drawable.flag_simple_dk, 0xFFC2596A, 0xFF000000,false, 29),
            Language("Svenska", "SWE", R.drawable.flag_simple_se, 0xFF4A83A7, 0xFF000000,false, 30),
            Language("Suomi", "FIN", R.drawable.flag_simple_fi, 0xFFFFFFFF, 0xFF000000,false, 31),
            Language("Українська", "UKR", R.drawable.flag_simple_ua, 0xFFFFEA77, 0xFF000000,false, 32),
            Language("Eesti keel", "EST", R.drawable.flag_simple_ee, 0xFF74BFFF, 0xFF000000,false, 33),
            Language("Latviešu valoda", "LVA", R.drawable.flag_simple_lv, 0xFF9C5A65, 0xFF000000,false, 34),
            Language("Lietuvių kalba", "LTU", R.drawable.flag_simple_lt, 0xFF548171, 0xFF000000,false, 35),
        )

        val test: Color = Color(0xFF74001C)

        val defaultList = listOf(getDefault(), getDefault(), getDefault(), getDefault())

        fun getDefault(): Language {
            return languagesList[0]
        }

        fun getSortedLanguageList(): List<Language> {
            return languagesList.sortedBy { it.iso }
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
    }
}