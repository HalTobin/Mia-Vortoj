package com.chapeaumoineau.miavortoj.feature.words.presentation.words.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Category
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Word

@Composable
fun WordItem(word: Word,
             isFromSource: Boolean,
             modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Column(modifier = Modifier
            //.fillMaxSize()
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp)) {
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
        Column(modifier = Modifier.align(Alignment.CenterVertically).padding(end = 16.dp)) {
            Text(modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f),
                text = word.emote,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
            Icon(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .size(42.dp),
                imageVector = ImageVector.vectorResource(Category.getCategoryById(word.themeId).icon),
                contentDescription = "")
        }
    }
}