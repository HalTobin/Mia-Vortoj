package com.chapeaumoineau.miavortoj.feature.words.presentation.util

sealed class WordOrder(val orderType: OrderType) {
    class Source(orderType: OrderType): WordOrder(orderType)
    class Target(orderType: OrderType): WordOrder(orderType)
    class Theme(orderType: OrderType): WordOrder(orderType)

    fun copy(orderType: OrderType): WordOrder {
        return when(this) {
            is Source -> Source(orderType)
            is Target -> Target(orderType)
            is Theme -> Theme(orderType)
        }
    }
}