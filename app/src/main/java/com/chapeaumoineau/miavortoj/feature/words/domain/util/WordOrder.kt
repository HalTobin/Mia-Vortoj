package com.chapeaumoineau.miavortoj.feature.words.domain.util

sealed class WordOrder(val orderType: OrderType) {
    class SourceWord(orderType: OrderType): WordOrder(orderType)
    class TargetWord(orderType: OrderType): WordOrder(orderType)
    class Theme(orderType: OrderType): WordOrder(orderType)

    fun copy(orderType: OrderType): WordOrder {
        return when(this) {
            is SourceWord -> SourceWord(orderType)
            is TargetWord -> TargetWord(orderType)
            is Theme -> Theme(orderType)
        }
    }
}