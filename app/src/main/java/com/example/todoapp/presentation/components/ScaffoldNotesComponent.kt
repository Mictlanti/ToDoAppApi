package com.example.todoapp.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.todoapp.domain.events.NoteEvent
import com.example.todoapp.domain.state.GeneralState
import com.example.todoapp.presentation.state.UserUiState
import com.example.todoapp.presentation.viewmodels.ToDoViewmodel

@Composable
fun ScaffoldEditOrCreatedNotes(
    titleTopBar: String = "Edit Note",
    isSaveNote: Boolean = false,
    viewModel: ToDoViewmodel,
    uiState: UserUiState,
    state: GeneralState,
    onDeleteNote: (() -> Unit)? = null,
    onNavClick: () -> Unit
) {

    val selectedColor = rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopBarEditOrCreated(
                titleTopBar = titleTopBar,
                selectColorOption = selectedColor.value,
                state = uiState,
                onNavClick = onNavClick,
                onDeleteNote = onDeleteNote,
                onOpenColors = {
                    keyboardController?.hide()
                    selectedColor.value = true
                },
                onCloseColors = { selectedColor.value = false },
                onClickSave = {
                    if (uiState.title.isNotEmpty() || uiState.body.isNotEmpty()) {
                        onNavClick()
                        if (!isSaveNote) {
                            viewModel.events(NoteEvent.EditNote)
                        } else {
                            viewModel.events(NoteEvent.SaveNote)
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (selectedColor.value) BottomBarNotes(
                modifier = Modifier.fillMaxWidth(),
                state,
                uiState
            ) { color ->
                viewModel.events(NoteEvent.SaveColor(color))
            }
        }
    ) { pad ->
        NoteComponent(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(horizontal = 12.dp),
            title = uiState.title,
            body = uiState.body,
            keyboardController = keyboardController,
            onTitleChange = { s -> viewModel.events(NoteEvent.OnValueChane(true, s)) },
            onBodyChange = { s -> viewModel.events(NoteEvent.OnValueChane(false, s)) }
        )
    }
}