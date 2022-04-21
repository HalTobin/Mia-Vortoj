package com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DefaultRadioButton(text: String,
                       selected: Boolean,
                       onSelect: () -> Unit,
                       color: Color = MaterialTheme.colors.primary,
                       modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = onSelect, colors = RadioButtonDefaults.colors(selectedColor = color, unselectedColor = color))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.body1, color = color)
    }
}