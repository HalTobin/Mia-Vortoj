package com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chapeaumoineau.miavortoj.R

import com.chapeaumoineau.miavortoj.feature.words.presentation.util.DictionaryOrder
import com.chapeaumoineau.miavortoj.feature.words.presentation.util.OrderType

@Composable
fun OrderDictionariesSection(modifier: Modifier = Modifier,
                             dictionaryOrder: DictionaryOrder = DictionaryOrder.Language(OrderType.Ascending),
                             onOrderChange: (DictionaryOrder) -> Unit) {
    Column(modifier = modifier) {
        Row(modifier = modifier.fillMaxWidth()) {
            DefaultRadioButton(text = stringResource(id = R.string.order_dictionary_language),
                selected = dictionaryOrder is DictionaryOrder.Language,
                onSelect = { onOrderChange(DictionaryOrder.Language(dictionaryOrder.orderType)) })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(text = stringResource(id = R.string.order_dictionary_title),
                selected = dictionaryOrder is DictionaryOrder.Title,
                onSelect = { onOrderChange(DictionaryOrder.Title(dictionaryOrder.orderType)) })
            Spacer(modifier = Modifier.width(16.dp))
        }
        Row(modifier = modifier.fillMaxWidth()) {
            DefaultRadioButton(text = stringResource(id = R.string.order_ascending),
                selected = dictionaryOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(dictionaryOrder.copy(OrderType.Ascending)) })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(text = stringResource(id = R.string.order_descending),
                selected = dictionaryOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChange(dictionaryOrder.copy(OrderType.Descending)) })
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}