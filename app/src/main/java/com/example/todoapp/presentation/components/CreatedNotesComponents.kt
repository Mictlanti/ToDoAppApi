package com.example.todoapp.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todoapp.domain.state.GeneralState
import com.example.todoapp.presentation.state.UserUiState
import com.example.todoapp.presentation.theme.NoteColors
import com.example.todoapp.presentation.util.colorHelper

@Composable
fun BottomBarNotes(
    modifier: Modifier = Modifier,
    state: GeneralState,
    uiState: UserUiState,
    onClickAction: (key: String) -> Unit
) {

    val listKeys = NoteColors.map { it.key }

    BottomAppBar {
        LazyRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(listKeys) { key ->
                ColorItemsList(
                    modifier = Modifier
                        .size(if(uiState.keyColor == key) 80.dp else 40.dp)
                        .animateContentSize(),
                    colorHelper(key, state),
                    border = BorderStroke(
                        if(uiState.keyColor == key) 5.dp else 0.dp,
                        if(uiState.keyColor == key) MaterialTheme.colorScheme.tertiary else Color.Transparent
                    )
                ){ onClickAction(key) }
            }
        }
    }
}

@Composable
fun ColorItemsList(modifier: Modifier, color: Color, border: BorderStroke, onClickAction: () -> Unit) {
    ElevatedButton(
        onClick = onClickAction,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = CircleShape,
        border = border,
        modifier = modifier
    ) {

    }
}