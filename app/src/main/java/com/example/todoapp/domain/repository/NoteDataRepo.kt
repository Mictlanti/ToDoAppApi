package com.example.todoapp.domain.repository

import com.example.todoapp.data.local.entity.NoteEntity
import com.example.todoapp.domain.model.Notes

interface NoteDataRepo {

    suspend fun getNotes() : List<NoteEntity>

    suspend fun getNoteById(id: Int) : NoteEntity?

    suspend fun createdNoteLocal(note: NoteEntity)

    suspend fun updateNoteLocal(note: NoteEntity)

    suspend fun deleteNoteLocal(id: Int)

    suspend fun syncPending()

}