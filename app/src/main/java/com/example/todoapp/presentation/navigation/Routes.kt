package com.example.todoapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {
    @Serializable
    data object HomeRoute : Routes
    @Serializable
    data object CreatedNotes : Routes

    @Serializable
    data class EditNote(val id: Int) : Routes

}