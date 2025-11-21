package com.example.todoapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.local.entity.NoteEntity
import com.example.todoapp.data.util.SyncState
import com.example.todoapp.data.util.toDomainList
import com.example.todoapp.domain.events.NoteEvent
import com.example.todoapp.domain.repository.NoteDataRepo
import com.example.todoapp.domain.repository.SharedPreferences
import com.example.todoapp.domain.state.GeneralState
import com.example.todoapp.domain.util.Constants
import com.example.todoapp.presentation.state.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewmodel @Inject constructor(
    private val prefs: SharedPreferences,
    private val noteDataRepo: NoteDataRepo
) : ViewModel() {

    private val _state = MutableStateFlow(GeneralState())
    val state: StateFlow<GeneralState> = _state.asStateFlow()

    private val _userState = MutableStateFlow(UserUiState())
    val userState: StateFlow<UserUiState> = _userState.asStateFlow()

    init {
        loadThemeSchema()
    }

    fun events(event: NoteEvent) {
        when (event) {
            is NoteEvent.SaveNote -> {
                saveNote()
            }

            is NoteEvent.EditNote -> {
                editNote()
            }

            is NoteEvent.DeleteNote -> {
                deleteNote()
            }

            is NoteEvent.SaveColor -> {
                saveColor(event.keyColor)
            }

            is NoteEvent.ChangeScheme -> {
                changeScheme(event.value)
            }

            is NoteEvent.SorterBy -> {
                sorterBy(event.index)
            }

            is NoteEvent.OnValueChane -> {
                onValueChange(event.isTitle, event.text)
            }
        }
    }

    private fun saveNote() {

        val newId = (_state.value.listNotes.minOfOrNull { it.id } ?: 0) - 1
        val note = NoteEntity(
            id = newId,
            title = _userState.value.title,
            body = _userState.value.body,
            keyColor = _userState.value.keyColor ?: Constants.COLOR_LAVANDA,
            syncState = SyncState.PENDING_CREATE
        )

        viewModelScope.launch(Dispatchers.IO) {
            noteDataRepo.createdNoteLocal(note)
        }
    }

    fun getAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val values = noteDataRepo.getNotes()
            _state.update { state ->
                state.copy(
                    listNotes = values.toDomainList()
                )
            }
        }

        _userState.update { state ->
            state.copy(
               id = 0,
                title = "",
                body = "",
                keyColor = "",
                onError = false
            )
        }
    }

    fun getNoteById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val note = noteDataRepo.getNoteById(id)
            _userState.update { state ->
                if(note != null) {
                    state.copy(
                        id = id,
                        title = note.title,
                        body = note.body,
                        onError = false
                    )
                } else {
                    state.copy(onError = true)
                }
            }
        }
    }

    private fun editNote() {

        val id = _userState.value.id
        val title = _userState.value.title
        val body = _userState.value.body
        val keyColor = _userState.value.keyColor

        val updateNote = NoteEntity(
            id = id,
            title = title,
            body = body,
            keyColor = keyColor ?: Constants.COLOR_LAVANDA,
            syncState = SyncState.PENDING_UPDATE
        )

        viewModelScope.launch {
            noteDataRepo.updateNoteLocal(updateNote)
        }

    }

    private fun deleteNote() {

        val idDoc = _userState.value.id

        viewModelScope.launch {
            noteDataRepo.deleteNoteLocal(idDoc)
        }
    }

    private fun saveColor(key: String) {
        _userState.update { uiState ->
            uiState.copy(
                keyColor = key
            )
        }
    }

    private fun changeScheme(value: Boolean) {
        _state.value = _state.value.copy(
            onDarkScheme = value
        )

        prefs.saveTheme(value)
    }

    private fun sorterBy(index: Int) {

        val sortedList = when (index) {
            0 -> _state.value.listNotes.sortedBy { it.id }
            1 -> _state.value.listNotes.sortedBy { it.title }
            2 -> _state.value.listNotes.sortedBy { it.keyColor }
            3 -> _state.value.listNotes.sortedByDescending { it.id }
            else -> _state.value.listNotes
        }

        _state.value = _state.value.copy(
            listNotes = sortedList
        )

    }

    private fun loadThemeSchema() {

        val theme = prefs.getThemeSchema()

        _state.update { state ->
            state.copy(onDarkScheme = theme)
        }

    }

    private fun onValueChange(isTitle: Boolean, text: String) {
        _userState.update { state ->
            if (isTitle) {
                state.copy(title = text)
            } else {
                state.copy(body = text)
            }
        }
    }

}