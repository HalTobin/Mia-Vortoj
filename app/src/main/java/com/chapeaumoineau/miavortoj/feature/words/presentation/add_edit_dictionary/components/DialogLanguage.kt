package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Language
import com.chapeaumoineau.miavortoj.feature.words.presentation.util.Screen
import com.chapeaumoineau.miavortoj.feature.words.presentation.words.WordsEvent
import com.chapeaumoineau.miavortoj.feature.words.presentation.words.components.WordItem
import kotlinx.coroutines.launch

@Composable
fun DialogLanguage(navController: NavController) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Dialog(onDismissRequest = { /* TODO */ }){
        Scaffold(scaffoldState = scaffoldState) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed(Language.languagesList) { index, language ->
                        DialogLanguageItem(
                            language = language,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    /* TODO */
                                }
                        )
                        if (index < Language.languagesList.lastIndex)
                            Divider(color = Color.Black, thickness = 1.dp)
                    }
                }
            }
        }
    }
}