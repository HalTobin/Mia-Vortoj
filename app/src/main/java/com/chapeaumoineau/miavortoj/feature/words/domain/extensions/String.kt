package com.chapeaumoineau.miavortoj.feature.words.domain.extensions

import java.text.Normalizer
import kotlin.String

fun String.lastChar(): Char {
    return this[this.length-1]
}

fun String.isLastCharASpace(): Boolean {
    return (this.lastChar() == ' ')
}

fun String.removeLastSpace(): String {
    return if (this.length > 0) if(this.isLastCharASpace()) this.dropLast(1) else this
    else this
}

fun String.formatCustom(): String {
    val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    var temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    temp = REGEX_UNACCENT.replace(temp, "").lowercase().removeLastSpace()
    return temp
}

fun String.equalsCustom(inStr: String): Boolean {
    return this.formatCustom() == inStr.formatCustom()
}

fun String.containsCustom(inStr: String): Boolean {
    return this.formatCustom().contains(inStr.formatCustom())
}

class String {

}