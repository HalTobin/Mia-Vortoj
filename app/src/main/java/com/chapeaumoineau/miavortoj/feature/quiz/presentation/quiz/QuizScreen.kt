package com.chapeaumoineau.miavortoj.feature.quiz.presentation.quiz

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import com.chapeaumoineau.miavortoj.ui.theme.DarkGreen
import com.chapeaumoineau.miavortoj.ui.theme.DarkRed
import com.chapeaumoineau.miavortoj.ui.theme.Transparent
import kotlinx.coroutines.launch

@Composable
fun QuizScreen(navController: NavController,
               viewModel: QuizViewModel = hiltViewModel()) {

    val word = viewModel.word.value
    val category = viewModel.category.value
    val userEntry = viewModel.userEntry.value
    val language = viewModel.language.value

    var textFieldColor = Transparent

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val textFieldBackgroundAnimated = remember {
        Animatable(textFieldColor)
    }

    Scaffold(scaffoldState = scaffoldState) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(language.getDarkColor())
            .padding(16.dp)) {
            Row() {
                Icon(modifier = Modifier
                    .size(60.dp)
                    .padding(end = 16.dp)
                    .align(Alignment.CenterVertically),
                    imageVector = ImageVector.vectorResource(category.icon),
                    contentDescription = "")
                Text(modifier = Modifier.align(Alignment.CenterVertically),
                    text = word.sourceWord,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(64.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally), horizontalArrangement = Arrangement.Center) {
                Text(text = word.emote, style = MaterialTheme.typography.h1)
            }

            Spacer(modifier = Modifier.height(80.dp))

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

            Button(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(32.dp)),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.dark_green)),
                onClick = {
                    textFieldColor = if(word.isValid(userEntry)) DarkGreen else DarkRed
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