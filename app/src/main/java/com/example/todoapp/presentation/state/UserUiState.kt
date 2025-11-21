package com.example.todoapp.presentation.state

data class UserUiState(
    val id : Int = 0,
    val title: String = "",
    val body: String = "",
    val keyColor: String? = null,
    val onError : Boolean = false
)