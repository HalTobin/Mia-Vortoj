package com.chapeaumoineau.miavortoj.feature.quiz.presentation.quiz

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chapeaumoineau.miavortoj.R
import com.chapeaumoineau.miavortoj.feature.words.presentation.word_card.WordCardEvent
import com.chapeaumoineau.miavortoj.ui.theme.DarkGreen
import com.chapeaumoineau.miavortoj.ui.theme.DarkOrange
import com.chapeaumoineau.miavortoj.ui.theme.DarkRed
import com.chapeaumoineau.miavortoj.ui.theme.Transparent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun QuizScreen(navController: NavController,
               viewModel: QuizViewModel = hiltViewModel()) {

    val word = viewModel.word.value
    val category = viewModel.category.value
    val userEntry = viewModel.userEntry.value
    val language = viewModel.language.value
    val progress = viewModel.progress.value

    var textFieldColor = Transparent

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val textFieldBackgroundAnimated = remember {
        Animatable(textFieldColor)
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is QuizViewModel.UiEvent.CloseQuiz -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(language.getDarkColor())
            .padding(16.dp)) {

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                    progress = progress)
            }

            Spacer(modifier = Modifier.height(64.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally), horizontalArrangement = Arrangement.Center) {
                Text(text = word.emote, style = MaterialTheme.typography.h1)
            }

            Spacer(modifier = Modifier.height(64.dp))

            /** Start speech **/

            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(modifier = Modifier.align(Alignment.CenterVertically),
                    text = word.sourceWord,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(32.dp))
                Button(modifier = Modifier
                    //.align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(8.dp)),
                    //colors = ButtonDefaults.buttonColors(backgroundColor = if(speech) colorResource(R.color.dark_green) else colorResource(R.color.dark_gray)),
                    onClick = {
                        //viewModel.onEvent(WordCardEvent.PlayWord)
                    }
                ) {
                    Icon(
                        Icons.Default.VolumeUp,
                        contentDescription = "Text to speech",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            TextField(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp),
                value = userEntry,
                onValueChange = {
                    viewModel.onEvent(QuizEvent.EnteredEntry(it))
                },
                label = {
                    Text(text = stringResource(R.string.quiz_enter_response), style = MaterialTheme.typography.h6, color = Color.LightGray)
                },
                colors = TextFieldDefaults.textFieldColors(backgroundColor = textFieldBackgroundAnimated.value),
                textStyle = MaterialTheme.typography.h6,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp)) {

                Button(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(6.dp)),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.dark_red)),
                    onClick = {
                        scope.launch {
                            viewModel.onEvent(QuizEvent.NextWord)
                        }
                    },
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 8.dp,
                        end = 20.dp,
                        bottom = 8.dp
                    )
                ) {
                    Text(text = stringResource(R.string.quiz_ask_answer), style = MaterialTheme.typography.h6)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(6.dp)),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.dark_green)),
                    onClick = {
                        textFieldColor =
                            if(word.isValid(userEntry)) DarkGreen
                            else if (word.isClose(userEntry)) DarkOrange
                                else DarkRed
                        scope.launch {
                            viewModel.onEvent(QuizEvent.CheckAnswer)
                            textFieldBackgroundAnimated.animateTo(
                                targetValue = textFieldColor,
                                animationSpec = tween(durationMillis = 250),
                            )
                            textFieldColor = Transparent
                            textFieldBackgroundAnimated.animateTo(
                                targetValue = textFieldColor,
                                animationSpec = tween(durationMillis = 500),
                            )
                        }
                    },
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 8.dp,
                        end = 20.dp,
                        bottom = 8.dp
                    )
                ) {
                    Text(text = stringResource(R.string.quiz_validate_answer), style = MaterialTheme.typography.h6)
                }

            }

        }
    }

}