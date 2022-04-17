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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chapeaumoineau.miavortoj.R
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Language
import com.chapeaumoineau.miavortoj.feature.words.domain.util.DictionaryOrder
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary.AddEditDictionaryEvent
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary.AddEditDictionaryViewModel
import com.chapeaumoineau.miavortoj.feature.words.presentation.components.TransparentHintTextField
import com.chapeaumoineau.miavortoj.feature.words.presentation.util.Screen
import com.chapeaumoineau.miavortoj.feature.words.presentation.words.WordsEvent
import com.chapeaumoineau.miavortoj.feature.words.presentation.words.components.WordItem
import kotlinx.coroutines.launch

@Composable
fun LanguageDialog(isVisible: Boolean, viewModel: AddEditDictionaryViewModel = hiltViewModel()) {
    if(isVisible) Dialog(onDismissRequest = { viewModel.onEvent(AddEditDictionaryEvent.DismissLanguageDialog) }){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(modifier = Modifier.fillMaxWidth(),
                    value = viewModel.search.value,
                    onValueChange = {
                        viewModel.onEvent(AddEditDictionaryEvent.EnteredSearch(it))
                    },
                    label = {
                        Text(text = stringResource(R.string.add_edit_dictionary_language_dialog_search_hint))
                    },
                    maxLines = 1
                )
            }
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(viewModel.languageList.value) { index, language ->
                    LanguageDialogItem(
                        language = language,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onEvent(
                                    AddEditDictionaryEvent.OnNewLanguageSelected(
                                        language.iso
                                    )
                                )
                            }
                    )
                    if (index < Language.languagesList.lastIndex)
                        Divider(color = Color.Black, thickness = 1.dp)
                }
            }
        }
    }
}
