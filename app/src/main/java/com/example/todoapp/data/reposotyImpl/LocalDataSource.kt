package com.example.todoapp.data.reposotyImpl

import com.example.todoapp.data.local.dao.NoteDao
import com.example.todoapp.data.local.entity.NoteEntity
import com.example.todoapp.data.util.SyncState
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val noteDao: NoteDao) {

    fun getAllNotes() : List<NoteEntity> = noteDao.getAllNotes()

    suspend fun getNoteById(id: Int) : NoteEntity? {
        return noteDao.getNoteById(id)
    }

    suspend fun insertNotes(notes: List<NoteEntity>) {
        noteDao.insertNoteRest(notes)
    }

    suspend fun insertNote(note: NoteEntity) = noteDao.insertNote(note)

    suspend fun updateNote(note: NoteEntity) = noteDao.updateNote(note)

    suspend fun deleteNoteById(id: Int) = noteDao.deleteNoteById(id)

    suspend fun getPendingNote() = noteDao.getPendingNotes()

    suspend fun updateSyncState(id: Int, state : SyncState) = noteDao.updateSyncState(id, state)

}