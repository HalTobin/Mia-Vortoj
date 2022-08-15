package com.chapeaumoineau.miavortoj.feature.words.presentation.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
