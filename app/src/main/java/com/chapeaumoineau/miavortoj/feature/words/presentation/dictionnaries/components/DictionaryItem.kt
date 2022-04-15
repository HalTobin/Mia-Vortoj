package com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries.components

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Language

@Composable
fun DictionaryItem(dictionary: Dictionary,
                   modifier: Modifier = Modifier,
                   cornerRadius: Dp = 10.dp) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                lineTo(0f, 0f)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = Language.getLanguageByIso(dictionary.languageIso).getColor(),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(end = 32.dp)) {
            Text(text = dictionary.title, style = MaterialTheme.typography.h6, color = MaterialTheme.colors.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }

        Column(modifier = Modifier
            .padding(end = 16.dp)
            .align(Alignment.CenterEnd)) {
            Image(painter = painterResource(Language.getLanguageByIso(dictionary.languageIso).flag),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .shadow(15.dp, CircleShape)
                    .clip(CircleShape)
                    .border(
                        width = 3.dp,
                        color = Color.Black,
                        shape = CircleShape)
            )
        }
    }
}