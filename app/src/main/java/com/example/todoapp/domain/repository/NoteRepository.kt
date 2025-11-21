package com.example.todoapp.domain.repository

import com.example.todoapp.data.local.dao.NoteDao
import com.example.todoapp.data.local.entity.NoteEntity
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    suspend fun insertNotes(note: NoteEntity) = noteDao.insertNote(note)

    fun getAllNotes() = noteDao.getAllNotes()

    fun getNoteById(id: Int) = noteDao.getNoteById(id)

    suspend fun deleteNoteById(id: Int) = noteDao.deleteNoteById(id)

}