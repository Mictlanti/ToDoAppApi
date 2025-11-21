package com.example.todoapp.presentation.util

import androidx.compose.ui.graphics.Color
import com.example.todoapp.domain.state.GeneralState
import com.example.todoapp.presentation.theme.NoteColors

fun colorHelper(key: String, state: GeneralState) : Color {
    val (light, dark) = NoteColors[key]!!
    return if (state.onDarkScheme) dark else light
}