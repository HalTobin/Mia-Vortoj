package com.chapeaumoineau.miavortoj.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
