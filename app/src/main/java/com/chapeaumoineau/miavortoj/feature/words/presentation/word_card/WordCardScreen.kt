package com.chapeaumoineau.miavortoj.feature.words.presentation.word_card

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Category
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word.WordCardViewModel
import com.chapeaumoineau.miavortoj.feature.words.presentation.util.Screen

@Composable
fun WordCardScreen(navController: NavController,
                    viewModel: WordCardViewModel = hiltViewModel()) {

    val word = viewModel.word.value
    val category = viewModel.category.value
    val dictionary = viewModel.dictionary.value
    val language = viewModel.language.value

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(Screen.AddEditWordScreen.route + "?dictionaryId=${word.dictionaryId}&wordId=${word.id}")
        }, backgroundColor = MaterialTheme.colors.primary) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit word")
        }
    }, scaffoldState = scaffoldState) {
        Column(modifier = Modifier.fillMaxSize().background(language.getDarkColor()).padding(16.dp)) {
            Row() {
                Icon(modifier = Modifier.size(60.dp).padding(end = 16.dp).align(Alignment.CenterVertically),
                    imageVector = ImageVector.vectorResource(category.icon),
                    contentDescription = "")
                Column() {
                    Text(text = word.targetWord, style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text(text = word.sourceWord, style = MaterialTheme.typography.h5, color = Color.Black)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = word.notes, style = MaterialTheme.typography.subtitle1, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally), horizontalArrangement = Arrangement.Center) {
                Text(text = word.emote, style = MaterialTheme.typography.h1, color = Color.Black)
            }
        }
    }

}