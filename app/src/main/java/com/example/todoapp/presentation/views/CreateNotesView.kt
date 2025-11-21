package com.example.todoapp.presentation.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.todoapp.presentation.components.ScaffoldEditOrCreatedNotes
import com.example.todoapp.presentation.viewmodels.ToDoViewmodel

@Composable
fun CreateNotesRoute(viewModel: ToDoViewmodel, onNavClick: () -> Unit) {

    val uiState by viewModel.userState.collectAsState()
    val state by viewModel.state.collectAsState()

    ScaffoldEditOrCreatedNotes(
        titleTopBar = "Create Note",
        isSaveNote = true,
        viewModel = viewModel,
        uiState = uiState,
        state = state,
        onNavClick = onNavClick
    )
}