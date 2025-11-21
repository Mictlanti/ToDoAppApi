package com.example.todoapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NoteDto(
    val id : Int,
    val title: String,
    val body: String,
    val color: String
)
