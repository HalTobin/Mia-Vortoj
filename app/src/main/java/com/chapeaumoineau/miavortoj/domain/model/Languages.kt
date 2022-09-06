package com.chapeaumoineau.miavortoj.domain.model

import androidx.compose.ui.graphics.Color
import com.chapeaumoineau.miavortoj.R
import java.util.*
import kotlin.collections.ArrayList

data class Language(
    val name: String,
    val iso: String,
    val flag: Int,
    val colorLight: Long,
    val colorDark: Long,
    val favorite: Boolean,
    val locale: Locale? = null,
) {

    fun getLightColor(): Color {
        return Color(this.colorLight)
    }

    fun getDarkColor(): Color {
        return Color(this.colorDark)
    }

    companion object {
        val languagesList = listOf(
            Language("Aucun / Autre", "0_N/A", R.drawable.globe,0xFF5AB8A4, 0xFF2D404E, false),
            Language("Esperanto", "EPO", R.drawable.flag_simple_eo,0xFF649964, 0xFF008100,false),
            Language("Iţkuîl", "ITHKUIL", R.drawable.flag_simple_ithkuil,0xFFEE4242, 0xFF000000,false),
            Language("Toki Pona", "TKPN", R.drawable.flag_simple_tkpn,0xFFFFFFA7, 0xFF00008B,false),
            Language("Меджусловјанскы", "ISLV", R.drawable.flag_simple_islv,0xFF435A9E, 0xFF001A66,false),
            Language("Interlingua", "ILNG", R.drawable.flag_simple_ilng,0xFFFF6767, 0xFFA70000,false),

            Language("Latin", "LAT", R.drawable.flag_simple_la,0xFFFFF083, 0xFFA89400,false),
            Language("Ελληνικά", "GRE", R.drawable.flag_simple_gr,0xFF2B9AFF, 0xFF004D92,false),

            Language("Brezhoneg", "FR-BRE", R.drawable.flag_simple_frbre,0xFFFFFFFF, 0xFF000000,false),
            Language("Cymraeg", "WEL", R.drawable.flag_simple_cy,0xFF5DC784, 0xFF006D27,false),
            Language("Kernewek", "COR", R.drawable.flag_simple_kw,0xFFFFFFFF, 0xFF000000,false),
            Language("Gaeilge", "GLE", R.drawable.flag_simple_ie,0xFFFFA85A, 0xFFA54E00,false),
            Language("Gàidhlig", "GLA", R.drawable.flag_simple_gd,0xFF49A6FF, 0xFF003E79,false),
            Language("Scots", "SCO", R.drawable.flag_simple_gd,0xFF49A6FF, 0xFF003E79,false),
            Language("Gaelg", "GLG", R.drawable.flag_simple_im,0xFFE96868, 0xFF7E0000,false),

            Language("Română", "RUM", R.drawable.flag_simple_ro,0xFFFFED74,0xFF851309, false),
            Language("Français", "FRA", R.drawable.flag_simple_fr,0xFF2C4594, 0xFF001F85,false, Locale.FRENCH),
            Language("Italiano", "ITA", R.drawable.flag_simple_it,0xFF49946D,0xFF016330, false),
            Language("Corsu", "FR-20R", R.drawable.flag_simple_fr20r,0xFFFFFFFF, 0xFF000000,false),
            Language("Sard", "SRD", R.drawable.flag_simple_it88,0xFFFFFFFF,0xFF8D0F14, false),
            Language("Occitan", "FR-OCC", R.drawable.flag_simple_frocc,0xFFD33F53, 0xFFA30016,false),
            Language("Bearnés", "FR-OCC-BEAR", R.drawable.flag_simple_oc_bear,0xFFE7CE49, 0xFF746100,false),
            Language("Español", "ESP", R.drawable.flag_simple_es,0xFFFFE282, 0xFF830713,false, Locale("es")),
            Language("Català", "ES-CT", R.drawable.flag_simple_esct,0xFFFFE282, 0xFF830713,false, Locale("ca")),
            Language("Galego", "GLV", R.drawable.flag_simple_glv,0xFFFFFFFF, 0xFF004B9C,false),
            Language("Português (Portugal)", "PT-P", R.drawable.flag_simple_pt,0xFF52AF52, 0xFF005000,false),
            Language("Português (Brasil)", "PT-B", R.drawable.flag_simple_br,0xFF5ECE7D, 0xFF147730,false),

            Language("Euskara", "PV", R.drawable.flag_simple_pv,0xFFD65247, 0xFF961D13,false, Locale("eu")),

            Language("English", "ENG", R.drawable.flag_simple_gb,0xFF5876C2, 0xFF00247D,false, Locale.ENGLISH),
            Language("Deutsch", "DEU", R.drawable.flag_simple_de,0xFFDD6969, 0xFF860000,false, Locale.GERMAN),
            Language("Nederlands", "NLD", R.drawable.flag_simple_nl,0xFF3166C9, 0xFF214485,false, Locale("nl")),
            Language("Frysk", "FRY", R.drawable.flag_simple_fry,0xFF4A92D6, 0xFF00407E,false),
            Language("Nordfriisk", "FRR", R.drawable.flag_simple_frr,0xFF2C67DF, 0xFF062A74,false),
            Language("Norsk", "NOR", R.drawable.flag_simple_no,0xFFEB7A83, 0xFFAD1B27,false, Locale("no")),
            Language("Dansk", "DNK", R.drawable.flag_simple_dk,0xFFC2596A, 0xFF8A0B20,false, Locale("da")),
            Language("Svenska", "SWE", R.drawable.flag_simple_se,0xFF4A83A7, 0xFF045586,false, Locale("sv")),
            Language("íslenska", "ice", R.drawable.flag_simple_is,0xFF4A88F1, 0xFF003081,false),

            Language("Magyar nyelv", "HUN", R.drawable.flag_simple_hu,0xFF67B136, 0xFF245C00,false),
            Language("Eesti keel", "EST", R.drawable.flag_simple_ee,0xFF74BFFF, 0xFF0D60A8,false, Locale("es")),
            Language("Suomi", "FIN", R.drawable.flag_simple_fi,0xFFFFFFFF, 0xFF003074,false, Locale("fi")),

            Language("Līvõ kēļ", "LIV", R.drawable.flag_simple_livonians,0xFF529958, 0xFF094B0F,false),

            Language("Karjala", "RU-KRL", R.drawable.flag_simple_ru_kr,0xFF51B6E9, 0xFF004F77,false, Locale("fi")),

            Language("Latviešu valoda", "LVA", R.drawable.flag_simple_lv,0xFF9C5A65, 0xFF721625,false, Locale("lv")),
            Language("Lietuvių kalba", "LTU", R.drawable.flag_simple_lt,0xFF548171, 0xFF005033,false, Locale("lt")),
            Language("Hrvatski", "HRV", R.drawable.flag_simple_hr, 0xFF6D6DC2, 0xFF0A0A6F,false),
            Language("Српски", "SRP", R.drawable.flag_simple_rs, 0xFF5E86B1, 0xFF032D58,false),
            Language("Bosanski", "BIH", R.drawable.flag_simple_ba, 0xFFFFE062, 0xFF000081,false),
            Language("Čeština", "CZE", R.drawable.flag_simple_cz,0xFF4570A0, 0xFF0E3968,false),
            Language("Slovenščina", "SVN", R.drawable.flag_simple_si,0xFF797ADF, 0xFF272769,false),
            Language("Slovenčina", "SVK", R.drawable.flag_simple_sk,0xFF5184C4, 0xFF093770,false),
            Language("Polski", "POL", R.drawable.flag_simple_pl,0xFFF74B6D, 0xFF970623,false, Locale("pl")),
            Language("Українська", "UKR", R.drawable.flag_simple_ua,0xFFFFEA77, 0xFF004592,false, Locale("uk")),
            Language("Беларуская", "BLR", R.drawable.flag_simple_by,0xFFB35353, 0xFF810000,false, Locale("be")),
            Language("Русский", "RUS", R.drawable.flag_simple_ru,0xFF4679DA, 0xFF002875,false, Locale("ru")),

            Language("ייִדיש", "YID", R.drawable.flag_simple_yi,0xFFFFFFFF, 0xFF000000,false),

            Language("हिन्दी", "HIN", R.drawable.flag_simple_in,0xFFFFBE7E, 0xFFA35100,false),
            Language("తెలుగు", "TEL", R.drawable.flag_simple_in,0xFFFFBE7E, 0xFFA35100,false),
            Language("தமிழ்", "TAM", R.drawable.flag_simple_tam,0xFF8ACEFA, 0xFF175B86,false),
            Language("اُردُو", "URD", R.drawable.flag_simple_pk,0xFF4E864D, 0xFF0A4E09,false),

            Language("Къырымтатар тили", "CRH", R.drawable.flag_simple_crimea,0xFFFFFFFF, 0xFF00466F,false),
            Language("Себер тел", "RU-STY", R.drawable.flag_simple_ru_oms,0xFFF36262, 0xFF7A0000,false),
            Language("Лезги чӏал", "RU-LEZ", R.drawable.flag_simple_ru_dage,0xFF5AE295, 0xFF02692F,false),
            Language("Агъул чӀал", "RU-AGX", R.drawable.flag_simple_ru_dage,0xFF5AE295, 0xFF02692F,false),
            Language("Магӏарул мацӏ", "RU-AVA", R.drawable.flag_simple_ru_dage,0xFF5AE295, 0xFF02692F,false),
            Language("Дарган мез", "RU-AVA", R.drawable.flag_simple_ru_dage,0xFF5AE295, 0xFF02692F,false),
            Language("Къумукъ тил", "RU-KUM", R.drawable.flag_simple_ru_dage,0xFF5AE295, 0xFF02692F,false),
            Language("Лакку маз", "RU-LBE", R.drawable.flag_simple_ru_dage,0xFF5AE295, 0xFF02692F,false),
            Language("МыхӀабишды чӀел", "RU-RUT", R.drawable.flag_simple_ru_dage,0xFF5AE295, 0xFF02692F,false),
            Language("Табасаран чIал", "RU-TAB", R.drawable.flag_simple_ru_dage,0xFF5AE295, 0xFF02692F,false),
            Language("ЦӀаӀхна миз", "RU-TKR", R.drawable.flag_simple_ru_dage,0xFF5AE295, 0xFF02692F,false),
            Language("Нохчийн мотт", "RU-CHE", R.drawable.flag_simple_ru_ce,0xFF5CA068, 0xFF1C5025,false),
            Language("Ногай тили", "RU-NOG", R.drawable.flag_simple_ru_kc,0xFF5CC087, 0xFF00441D,false),
            Language("Абаза бызшва", "RU-ABQ", R.drawable.flag_simple_ru_kc,0xFF5CC087, 0xFF00441D,false),
            Language("Адыгэбзэ", "RU-QBD", R.drawable.flag_simple_ru_kb,0xFF69B4FF, 0xFF0A5299,false),
            Language("Къарачай-малкъар тил", "RU-KRC", R.drawable.flag_simple_ru_kb,0xFF69B4FF, 0xFF0A5299,false),
            Language("Zuhun tati", "RU-TTT", R.drawable.flag_simple_az,0xFF47DCFF, 0xFF006C85, false),

            Language("Azəri", "AZE", R.drawable.flag_simple_az,0xFF47DCFF, 0xFF006C85,false),
            Language("Türkçe", "TUR", R.drawable.flag_simple_tr, 0xFFDD5E66, 0xFFA0000A,false),
            Language("Татар теле", "TAT", R.drawable.flag_simple_ruta,0xFF60BD60, 0xFF004B00,false),
            Language("Türkmençe", "TUK", R.drawable.flag_simple_tm,0xFF39AF70, 0xFF005326,false),
            Language("Тоҷикӣ", "TGK", R.drawable.flag_simple_tj,0xFF4AA04A, 0xFF004400,false),
            Language("پارسی", "PER", R.drawable.flag_simple_ir,0xFF73C486, 0xFF136426,false),
            Language("Oʻzbekcha", "UZB", R.drawable.flag_simple_uz,0xFF4BAABB, 0xFF006A7E,false),
            Language("Qaraqalpaq tili", "UZ-KAA", R.drawable.flag_simple_uz_kaa,0xFFFFD56A, 0xFF916800,false),
            Language("Qazaq", "KAZ", R.drawable.flag_simple_kz,0xFF63C6D3, 0xFF006F7E,false),

            Language("日本語", "JPN", R.drawable.flag_simple_jp,0xFFFFFFFF, 0xFF74001C,false, Locale.JAPANESE),
            Language("官话", "CHN", R.drawable.flag_simple_cn,0xFFDB6A5A, 0xFFA7200E,false, Locale.CHINESE),
            Language("粤语", "YUE", R.drawable.flag_simple_yue,0xFFDB6A5A, 0xFFA01319,false, Locale.CHINESE),
            Language("한국어", "KOR", R.drawable.flag_simple_kr,0xFFFFFFFF, 0xFF003B85,false, Locale.KOREAN),
            Language("Tiếng Việt", "VIE", R.drawable.flag_simple_vn,0xFFF06874, 0xFF8A000C,false),
            Language("ภาษาไทย", "THA", R.drawable.flag_simple_th,0xF2C9485E, 0xFF272441,false),

            Language("Afrikaans", "ZAF", R.drawable.flag_simple_za,0xFF4B8B74, 0xFF006F25,false, Locale("af")),
            Language("isiZulu", "ZUL", R.drawable.flag_simple_za,0xFF4B8B74, 0xFF006F25,false),
            Language("isiXhosa", "XHO", R.drawable.flag_simple_xh,0xFF91B1E2, 0xFF004BB9,false),
            Language("Yorùbá", "YOR", R.drawable.flag_simple_ng,0xFF4ABD91, 0xFF01643E,false),
            Language("Twi", "TWI", R.drawable.flag_simple_gh,0xFFFFE46F, 0xFF005230,false),
            Language("Xitsonga", "TSO", R.drawable.flag_simple_sz,0xFF7B99DD, 0xFF253968,false),
            Language("ትግርኛ", "TIR", R.drawable.flag_simple_er,0xFF6DADEB, 0xFF194E81,false),

            Language("Shqiptare", "ALB", R.drawable.flag_simple_al,0xFFE46869, 0xFFAC1516,false),
            Language("العربية", "SAU", R.drawable.flag_simple_sa,0xFF5B9151, 0xFF137700,false, Locale("ar")),
            Language("עִבְרִית", "HEB", R.drawable.flag_simple_il,0xFFFFFFFF, 0xFF0101BB,false),
            Language("Հայերեն", "ARM", R.drawable.flag_simple_am,0xFFFFC967, 0xFF915D00,false),
            Language("বাংলা", "BGD", R.drawable.flag_simple_bd,0xFF45685E, 0xFF00523C,false, Locale("bn")),
            Language("မြန်မာစာ", "MMR", R.drawable.flag_simple_mm,0xFF75AC75, 0xFF1E6D1E,false),
            Language("Български", "BGR", R.drawable.flag_simple_bg,0xFF5A9182, 0xFF006348,false, Locale("bg")),
            Language("Cebuano", "PHL", R.drawable.flag_simple_ph,0xFFFFFFFF, 0xFF9E0D1D,false),
            Language("Chichewa", "MWI", R.drawable.flag_simple_mw,0xFFF36962, 0xFFAA0D05,false),
            Language("සිංහල", "LKA", R.drawable.flag_simple_lk,0xFFFFD977, 0xFF771C23,false),

            Language("Nawatlahtolli", "NAH", R.drawable.flag_simple_mx,0xFF68BBA1, 0xFF005339,false),

            Language("TlhIngan Hol", "FI-KLG", R.drawable.flag_simple_fiklg,0xFFFF6060, 0xFF9C0B0B,false),
            Language("Quenya", "FI-ELV-QYA", R.drawable.flag_simple_fielf,0xFF68A5F3, 0xFF001E44,false),
            Language("Sindarin", "FI-ELV-SJN", R.drawable.flag_simple_fielf,0xFF68A5F3, 0xFF001E44,false),
            Language("Lìʼfya leNaʼvi", "FI-NAVI", R.drawable.flag_simple_finavi,0xFF368CFF, 0xFF01439B,false),
            Language("Dothraki", "FI-DTK", R.drawable.flag_simple_fidtk,0xFF2DB2FF, 0xFF005688,false),
            Language("Lapine", "FI-LPN", R.drawable.flag_simple_filpn,0xFF7ED575, 0xFF0B7900,false),
            Language("Dovahzul", "FI-DOV", R.drawable.flag_simple_fidov,0xFFFFFFFF, 0xFF000000,false)
        )

        val testl: Color = Color(0xFF529958)
        val testd: Color = Color(0xFF094B0F)

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