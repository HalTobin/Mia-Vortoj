package com.chapeaumoineau.miavortoj.feature.words.presentation.words.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Word

@Composable
fun WordItem(word: Word,
             isFromSource: Boolean,
             modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp,
                top = 4.dp,
                bottom = 4.dp)) {
            Text(text = if(isFromSource) word.sourceWord else word.targetWord,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
            Text(text = if(isFromSource) word.targetWord else word.sourceWord,
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        }

    }
}