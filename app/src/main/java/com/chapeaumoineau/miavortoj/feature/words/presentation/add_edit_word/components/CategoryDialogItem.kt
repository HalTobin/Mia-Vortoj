package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Category

@Composable
fun CategoryDialogItem(category: Category,
                       modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Column(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.Center)
            .padding(start = 16.dp)) {
            Text(text = category.title,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(modifier = Modifier
            .padding(end = 16.dp, bottom = 4.dp, top = 4.dp, start = 8.dp)
            .align(Alignment.CenterEnd)) {
            Image(painter = painterResource(category.icon),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .shadow(15.dp, CircleShape)
                    .clip(CircleShape)
            )
        }
    }

}