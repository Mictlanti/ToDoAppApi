package com.example.todoapp.presentation.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Reorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.todoapp.domain.events.NoteEvent
import com.example.todoapp.domain.model.Notes
import com.example.todoapp.domain.state.GeneralState
import com.example.todoapp.presentation.components.BodyLarge
import com.example.todoapp.presentation.components.CardNotes
import com.example.todoapp.presentation.components.HeadLineLarge
import com.example.todoapp.presentation.components.ReorderOption
import com.example.todoapp.presentation.components.TopAppBarHome
import com.example.todoapp.presentation.state.UserUiState
import com.example.todoapp.presentation.util.colorHelper
import com.example.todoapp.presentation.viewmodels.ToDoViewmodel

@Composable
fun HomeScreenRoute(
    viewmodel: ToDoViewmodel,
    onClickAddNote: () -> Unit,
    onClickEdit: (idDoc: Int) -> Unit
) {

    val state by viewmodel.state.collectAsState()
    val reorder = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewmodel.getAllNotes()
    }

    ReorderOption(
        reorder.value,
        onClickIndexReorder = { index -> viewmodel.events(NoteEvent.SorterBy(index)) }
    ) {
        reorder.value = false
    }

    Scaffold(
        topBar = {
            TopAppBarHome(viewmodel)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onClickAddNote() }
            ) {
                Icon(
                    Icons.Default.Add,
                    "Add note"
                )
            }
        }
    ) { pad ->
        HomeScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = pad)
                .padding(horizontal = 7.dp),
            listNotes = state.listNotes,
            generalState = state,
            onClickReorder = { reorder.value = true },
            onClickEdit = onClickEdit
        )
    }
}

@Composable
private fun HomeScreen(
    modifier: Modifier,
    listNotes: List<Notes>,
    generalState: GeneralState,
    onClickReorder: () -> Unit,
    onClickEdit: (idDoc: Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BodyLarge("Your notes")
                IconButton(onClick = onClickReorder) {
                    Icon(
                        Icons.Default.Reorder,
                        "Reorder"
                    )
                }
            }
        }
        if (listNotes.isEmpty()) {
            item { HeadLineLarge("No has agregado ninguna nota") }
        } else {
            items(listNotes) { state ->
                CardNotes(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    title = state.title,
                    body = state.body,
                    color = colorHelper(state.keyColor, generalState),
                    onEdit = { onClickEdit(state.id) }
                )
            }
        }
    }
}