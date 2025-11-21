package com.example.todoapp.domain.state

import com.example.todoapp.domain.model.Notes

data class GeneralState(
    val onDarkScheme: Boolean = false,
    val listNotes: List<Notes> = emptyList()
)
