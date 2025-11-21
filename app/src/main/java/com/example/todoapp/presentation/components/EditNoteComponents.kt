package com.example.todoapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp.presentation.state.UserUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarEditOrCreated(
    titleTopBar: String = "Edit note",
    selectColorOption: Boolean,
    state: UserUiState,
    onNavClick: () -> Unit,
    onDeleteNote: (() -> Unit)? = null,
    onOpenColors: () -> Unit,
    onCloseColors: () -> Unit,
    onClickSave: () -> Unit
) {

    TopAppBar(
        title = {
            HeadLineLarge(titleTopBar)
        },
        navigationIcon = {
            IconButton(
                onClick = onNavClick
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBackIos,
                    "Nav back"
                )
            }
        },
        actions = {
            if (onDeleteNote != null) {
                IconButton(
                    onClick = onDeleteNote
                ) {
                    Icon(
                        Icons.Default.Delete,
                        "Delete note"
                    )
                }
            }
            AnimatedVisibility(!selectColorOption) {
                IconButton(onClick = onOpenColors) {
                    Icon(
                        Icons.Default.Done,
                        "Done"
                    )
                }
            }
            AnimatedVisibility(selectColorOption) {
                Row {
                    IconButton(onClick = onCloseColors ) {
                        Icon(
                            Icons.Default.Close,
                            "Close list colors"
                        )
                    }
                    Spacer(Modifier.width(7.dp))
                    IconButton(
                        onClick = onClickSave,
                        enabled = state.keyColor != null
                    ) {
                        Icon(
                            Icons.Default.Save,
                            "Save note"
                        )
                    }
                }
            }
        }
    )
}