package com.chapeaumoineau.miavortoj.feature.words.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
