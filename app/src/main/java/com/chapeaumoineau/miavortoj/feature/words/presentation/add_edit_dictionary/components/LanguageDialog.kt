package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.chapeaumoineau.miavortoj.R
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Language
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary.AddEditDictionaryEvent
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary.AddEditDictionaryViewModel

@Composable
fun LanguageDialog(isVisible: Boolean, viewModel: AddEditDictionaryViewModel = hiltViewModel()) {
    if(isVisible) Dialog(onDismissRequest = { viewModel.onEvent(AddEditDictionaryEvent.DismissLanguageDialog) }){
        Surface(
            modifier = Modifier.fillMaxHeight(0.9f),
            shape = RoundedCornerShape(8.dp),
            contentColor = Color.White
        ) {
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
                            Text(text = stringResource(R.string.add_edit_dictionary_language_dialog_search_hint), color = Color.LightGray)
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
}
