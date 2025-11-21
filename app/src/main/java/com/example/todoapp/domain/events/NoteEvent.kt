package com.example.todoapp.domain.events

sealed class NoteEvent {

    data object SaveNote : NoteEvent()
    data object EditNote : NoteEvent()
    data object DeleteNote : NoteEvent()
    data class SaveColor(val keyColor: String) : NoteEvent()
    data class ChangeScheme(val value: Boolean) : NoteEvent()
    data class SorterBy(val index: Int) : NoteEvent()
    data class OnValueChane(val isTitle: Boolean, val text: String) : NoteEvent()

}