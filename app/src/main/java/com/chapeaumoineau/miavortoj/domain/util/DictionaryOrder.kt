package com.chapeaumoineau.miavortoj.domain.util

sealed class DictionaryOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): DictionaryOrder(orderType)
    class Language(orderType: OrderType): DictionaryOrder(orderType)

    fun copy(orderType: OrderType): DictionaryOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Language -> Language(orderType)
        }
    }
}