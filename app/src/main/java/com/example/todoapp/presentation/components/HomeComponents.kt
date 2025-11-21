package com.example.todoapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.domain.events.NoteEvent
import com.example.todoapp.presentation.util.limit
import com.example.todoapp.presentation.viewmodels.ToDoViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarHome(viewmodel: ToDoViewmodel) {

    val state by viewmodel.state.collectAsState()

    CenterAlignedTopAppBar(
        title = {
            HeadLineLarge("ToDoApp")
        },
        actions = {
            AnimatedVisibility(state.onDarkScheme) {
                Icon(
                    Icons.Default.DarkMode,
                    "Dark Mode"
                )
            }
            AnimatedVisibility(!state.onDarkScheme) {
                Icon(
                    Icons.Default.LightMode,
                    "Light Mode"
                )
            }
            Spacer(Modifier.width(10.dp))
            Switch(
                checked = state.onDarkScheme,
                onCheckedChange = {
                    viewmodel.events(NoteEvent.ChangeScheme(it))
                }
            )
        }
    )
}

@Composable
fun CardNotes(
    modifier: Modifier = Modifier,
    title: String,
    body: String,
    color: Color = MaterialTheme.colorScheme.primary,
    onEdit: () -> Unit
) {

    val titleModifier = title.limit(23)
    val bodyModifier = body.limit(65)

    ElevatedCard(
        onClick = { onEdit() },
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color),
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterVertically)
        ) {
            BodyLarge(titleModifier, color = MaterialTheme.colorScheme.onPrimary)
            BodyMedium(bodyModifier, color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReorderOption(showReorder: Boolean, onClickIndexReorder: (index: Int) -> Unit, onDismiss: () -> Unit) {

    val listOptions = listOf(
        "Default",
        "Abc",
        "Color",
        "Date",
    )

    if(showReorder){
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            dragHandle = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                    BodyLarge("Reodenar como:")
                }
            }
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                listOptions.forEachIndexed { index, string ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier.clickable { onClickIndexReorder(index) }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.CompareArrows,
                            string
                        )
                        BodyMedium(string)
                    }
                }
            }
        }
    }
}