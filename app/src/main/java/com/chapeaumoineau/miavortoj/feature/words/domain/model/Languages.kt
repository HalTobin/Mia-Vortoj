package com.chapeaumoineau.miavortoj.feature.words.domain.model

import androidx.compose.ui.graphics.Color
import com.chapeaumoineau.miavortoj.R
import java.util.*
import kotlin.collections.ArrayList

data class Language(
    val name: String,
    val iso: String,
    val flag: Int,
    val text: Int,
    val colorLight: Long,
    val colorDark: Long,
    val favorite: Boolean,
    val locale: Locale? = null,
    var translation: String = ""
) {

    fun getLightColor(): Color {
        return Color(this.colorLight)
    }

    fun getDarkColor(): Color {
        return Color(this.colorDark)
    }

    companion object {
        val languagesList = listOf(
            Language("Aucun / Autre", "0_N/A", R.drawable.globe, R.string.language_na,0xFF5AB8A4, 0xFF2D404E, false),
            Language("Esperanto", "EPO", R.drawable.flag_simple_eo, R.string.language_epo,0xFF649964, 0xFF008100,false),
            Language("English", "ENG", R.drawable.flag_simple_gb, R.string.language_eng,0xFF5876C2, 0xFF00247D,false, Locale.ENGLISH),
            Language("Français", "FRA", R.drawable.flag_simple_fr, R.string.language_fra,0xFF2C4594, 0xFF001F85,false, Locale.FRENCH),
            Language("Español", "ESP", R.drawable.flag_simple_es, R.string.language_esp,0xFFFFE282, 0xFF830713,false, Locale("es")),
            Language("Italiano", "ITA", R.drawable.flag_simple_it, R.string.language_ita,0xFF49946D,0xFF016330, false, Locale.ITALIAN),
            Language("Deutsch", "DEU", R.drawable.flag_simple_de, R.string.language_deu,0xFFDD6969, 0xFF860000,false, Locale.GERMAN),
            Language("Slovenščina", "SVN", R.drawable.flag_simple_si, R.string.language_svn,0xFF515292, 0xFF272769,false, Locale("sl")),
            Language("Русский", "RUS", R.drawable.flag_simple_ru, R.string.language_rus,0xFF4669AD, 0xFF002875,false, Locale("ru")),
            Language("日本語", "JPN", R.drawable.flag_simple_jp, R.string.language_jpn,0xFFFFFFFF, 0xFF74001C,false, Locale.JAPANESE),
            Language("Afrikaans", "ZAF", R.drawable.flag_simple_za, R.string.language_zaf,0xFF4B8B74, 0xFF006F25,false, Locale("af")),
            Language("Shqiptare", "ALB", R.drawable.flag_simple_al, R.string.language_alb,0xFFE46869, 0xFFAC1516,false),
            Language("العربية", "SAU", R.drawable.flag_simple_sa, R.string.language_sau,0xFF5B9151, 0xFF137700,false, Locale("ar")),
            Language("Հայերեն", "ARM", R.drawable.flag_simple_am, R.string.language_arm,0xFFFFC967, 0xFF915D00,false),
            Language("Azəri", "AZE", R.drawable.flag_simple_az, R.string.language_aze,0xFF47DCFF, 0xFF006C85,false),
            Language("Euskara", "PV", R.drawable.flag_simple_pv, R.string.language_pv,0xFFD65247, 0xFF961D13,false, Locale("eu")),
            Language("বাংলা", "BGD", R.drawable.flag_simple_bd, R.string.language_bgd,0xFF45685E, 0xFF00523C,false, Locale("bn")),
            Language("Беларуская", "BLR", R.drawable.flag_simple_by, R.string.language_blr,0xFFB35353, 0xFF810000,false, Locale("be")),
            Language("မြန်မာစာ", "MMR", R.drawable.flag_simple_mm, R.string.language_mmr,0xFF75AC75, 0xFF1E6D1E,false),
            Language("Bosanski", "BIH", R.drawable.flag_simple_ba, R.string.language_bih,0xFFFFE062, 0xFF000081,false, Locale("sr")),
            Language("Български", "BGR", R.drawable.flag_simple_bg, R.string.language_bgr,0xFF5A9182, 0xFF006348,false, Locale("bg")),
            Language("Català", "ES-CT", R.drawable.flag_simple_esct, R.string.language_esct,0xFFFFE282, 0xFF830713,false, Locale("ca")),
            Language("Cebuano", "PHL", R.drawable.flag_simple_ph, R.string.language_phl,0xFFFFFFFF, 0xFF9E0D1D,false),
            Language("Chichewa", "MWI", R.drawable.flag_simple_mw, R.string.language_mwi,0xFFF36962, 0xFFAA0D05,false),
            Language("官话", "CHN", R.drawable.flag_simple_cn, R.string.language_chn,0xFFDB6A5A, 0xFFA7200E,false, Locale.CHINESE),
            Language("粤语", "YUE", R.drawable.flag_simple_yue, R.string.language_yue,0xFFDB6A5A, 0xFFA01319,false, Locale.CHINESE),
            Language("සිංහල", "LKA", R.drawable.flag_simple_lk, R.string.language_lka,0xFFFFD977, 0xFF771C23,false),
            Language("한국어", "KOR", R.drawable.flag_simple_kr, R.string.language_kor,0xFFFFFFFF, 0xFF003B85,false, Locale.KOREAN),
            Language("Corsu", "FR-20R", R.drawable.flag_simple_fr20r, R.string.language_fr20r,0xFFFFFFFF, 0xFF000000,false),
            Language("Brezhoneg", "FR-BRE", R.drawable.flag_simple_frbre, R.string.language_frbre,0xFFFFFFFF, 0xFF000000,false),
            Language("Occitan", "FR-OCC", R.drawable.flag_simple_frocc, R.string.language_frocc,0xFFD33F53, 0xFFA30016,false),
            Language("Norsk", "NOR", R.drawable.flag_simple_no, R.string.language_nor,0xFFEB7A83, 0xFFAD1B27,false, Locale("no")),
            Language("Dansk", "DNK", R.drawable.flag_simple_dk, R.string.language_dnk,0xFFC2596A, 0xFF8A0B20,false, Locale("da")),
            Language("Svenska", "SWE", R.drawable.flag_simple_se, R.string.language_swe,0xFF4A83A7, 0xFF045586,false, Locale("sv")),
            Language("Suomi", "FIN", R.drawable.flag_simple_fi, R.string.language_fin,0xFFFFFFFF, 0xFF003074,false, Locale("fi")),
            Language("Українська", "UKR", R.drawable.flag_simple_ua, R.string.language_ukr,0xFFFFEA77, 0xFF004592,false, Locale("uk")),
            Language("Eesti keel", "EST", R.drawable.flag_simple_ee, R.string.language_est,0xFF74BFFF, 0xFF0D60A8,false, Locale("es")),
            Language("Latviešu valoda", "LVA", R.drawable.flag_simple_lv, R.string.language_lva,0xFF9C5A65, 0xFF721625,false, Locale("lv")),
            Language("Lietuvių kalba", "LTU", R.drawable.flag_simple_lt, R.string.language_ltu,0xFF548171, 0xFF005033,false, Locale("lt")),
            Language("Nederlands", "NLD", R.drawable.flag_simple_nl, R.string.language_nl,0xFF3166C9, 0xFF214485,false, Locale("nl")),
            Language("TlhIngan Hol", "FI-KLG", R.drawable.flag_simple_fiklg, R.string.language_fiklg,0xFFFF6060, 0xFF9C0B0B,false),
            Language("Elvish", "FI-ELV", R.drawable.flag_simple_fielf, R.string.language_fielf,0xFF68A5F3, 0xFF001E44,false),
            Language("Lìʼfya leNaʼvi", "FI-NAVI", R.drawable.flag_simple_finavi, R.string.language_finavi,0xFF368CFF, 0xFF01439B,false),
            Language("Dothraki", "FI-DTK", R.drawable.flag_simple_fidtk, R.string.language_fidtk,0xFF2DB2FF, 0xFF005688,false)
        )

        val test: Color = Color(0xFF2DB2FF)

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