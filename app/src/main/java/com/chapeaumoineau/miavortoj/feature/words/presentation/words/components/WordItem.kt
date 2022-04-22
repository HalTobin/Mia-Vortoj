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
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp)) {
            Text(text = if(isFromSource) word.sourceWord else word.targetWord,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
            Text(text = if(isFromSource) word.targetWord else word.sourceWord,
                style = MaterialTheme.typography.subtitle2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        }
        Row(modifier = Modifier. padding(end = 16.dp).fillMaxSize().align(Alignment.CenterVertically),
            horizontalArrangement = Arrangement.End) {
            Icon(modifier = Modifier
                .fillMaxHeight()
                .size(38.dp),
                imageVector = ImageVector.vectorResource(Category.getCategoryById(word.themeId).icon),
                contentDescription = "")
        }
    }
}