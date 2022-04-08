package com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnary_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.chapeaumoineau.miavortoj.ui.theme.CleanArchitectureNoteAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitectureNoteAppTheme {
                
            }
        }
    }
}
