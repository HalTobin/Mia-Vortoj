package com.chapeaumoineau.miavortoj.feature.words.presentation.words.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
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