package com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries.components

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
                   dictionaryEdited: Int = -1,
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
                    color = Language.getLanguageByIso(dictionary.languageIso).getLightColor(),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(end = 32.dp)) {
            Text(text = dictionary.title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        }

        Box(modifier = Modifier
            .padding(end = 16.dp)
            .align(Alignment.CenterEnd),
            contentAlignment = Alignment.CenterEnd) {

            AnimatedVisibility(visible = (dictionaryEdited != dictionary.id),
                enter = fadeIn() + slideInHorizontally(
                    initialOffsetX = { 150 }, // small slide 300px
                    animationSpec = tween(
                        durationMillis = 125,
                        easing = LinearEasing // interpolator
                    )
                ),
                exit = fadeOut() + slideOutHorizontally()) {
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

            AnimatedVisibility(visible = (dictionaryEdited == dictionary.id),
                enter = fadeIn() + slideInHorizontally(
                    initialOffsetX = { 150 }, // small slide 300px
                    animationSpec = tween(
                        durationMillis = 125,
                        easing = LinearEasing // interpolator
                    )
                ),
                exit = fadeOut() + slideOutHorizontally()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Edit,
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                }
            }

        }
    }


}