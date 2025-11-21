package com.example.todoapp.presentation.views

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.todoapp.domain.events.NoteEvent
import com.example.todoapp.presentation.components.ErrorView
import com.example.todoapp.presentation.components.LoadingOverlay
import com.example.todoapp.presentation.components.ScaffoldEditOrCreatedNotes
import com.example.todoapp.presentation.viewmodels.ToDoViewmodel

@Composable
fun EditNotesRoute(viewModel: ToDoViewmodel, id: Int, onNavClick: () -> Unit) {

    val uiState by viewModel.userState.collectAsState()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getNoteById(id)
        Log.d("NoteView", "id note: $id")
    }

    when (uiState.onError) {
        true -> {
            LoadingOverlay()
        }

        false -> {
            ScaffoldEditOrCreatedNotes(
                viewModel = viewModel,
                uiState = uiState,
                state = state,
                onDeleteNote = {
                    viewModel.events(NoteEvent.DeleteNote)
                    onNavClick()
                },
                onNavClick = onNavClick
            )
        }
    }

}