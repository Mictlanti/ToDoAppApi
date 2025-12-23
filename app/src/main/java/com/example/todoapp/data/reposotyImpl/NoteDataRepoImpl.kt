package com.example.todoapp.data.reposotyImpl

import android.util.Log
import com.example.todoapp.data.local.entity.NoteEntity
import com.example.todoapp.data.mappers.toDto
import com.example.todoapp.data.mappers.toEntity
import com.example.todoapp.data.remote.RemoteDataSources
import com.example.todoapp.data.util.SyncState
import com.example.todoapp.domain.repository.NoteDataRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteDataRepoImpl @Inject constructor(
    private val remoteDataSources: RemoteDataSources,
    private val localDataSource: LocalDataSource
) : NoteDataRepo {

    override suspend fun getNotes(): List<NoteEntity> {
        try {
            val remoteNote = remoteDataSources.getNotes()

            val entities = remoteNote.map { it.toEntity() }
            localDataSource.insertNotes(entities)

        } catch (e: Exception) {
            Log.e("NoteDataRepoImpl", "Error converting data: ${e.localizedMessage}")
        }
        return localDataSource.getAllNotes()
    }

    override suspend fun getNoteById(id: Int): NoteEntity? {
        return localDataSource.getNoteById(id)
    }

    override suspend fun createdNoteLocal(note: NoteEntity) {
        val toInsert = note.copy(
            syncState = SyncState.PENDING_CREATE,
            lastModified = System.currentTimeMillis()
        )
        localDataSource.insertNote(toInsert)
        tryUploadPending()
    }

    override suspend fun updateNoteLocal(note: NoteEntity) {
        val newState = if (note.id < 0) SyncState.PENDING_CREATE else SyncState.PENDING_UPDATE
        val toUpdate = note.copy(syncState = newState, lastModified = System.currentTimeMillis())
        localDataSource.updateNote(toUpdate)
        tryUploadPending()
    }

    override suspend fun deleteNoteLocal(id: Int) {
        if (id <= 0) {
            //Nunca estuvo en servidor, eliminar local
            localDataSource.deleteNoteById(id)
            return
        }
        localDataSource.updateSyncState(id, SyncState.PENDING_DELETE)
        tryUploadPending()
    }

    private fun tryUploadPending() {
        CoroutineScope(Dispatchers.IO).launch {
            syncPending()
        }
    }

    override suspend fun syncPending() {
        val pending = localDataSource.getPendingNote()

        pending.forEach { noteEntity ->
            when (noteEntity.syncState) {
                SyncState.PENDING_CREATE -> handlePendingCreate(noteEntity)
                SyncState.PENDING_UPDATE -> handlePendingUpdate(noteEntity)
                SyncState.PENDING_DELETE -> handlePendingDelete(noteEntity)
                else -> Unit
            }
        }
    }

    private suspend fun handlePendingCreate(note: NoteEntity) {
        val dto = note.toDto()
        val create = remoteDataSources.createNote(dto)
        if (create != null) {
            //Servidor devolvió nueva nota con id positivo
            localDataSource.deleteNoteById(note.id)
            localDataSource.insertNote(create.toEntity().copy(syncState = SyncState.SYNCED))
            Log.d("HandlePending", "Create state: note insert")
        } else {
            //Queda pendiente
            localDataSource.updateSyncState(note.id, SyncState.PENDING_CREATE)
            Log.d("HandlePending", "Create state: create pending")
        }
    }

    private suspend fun handlePendingUpdate(note: NoteEntity) {
        val dto = note.toDto()
        val res = remoteDataSources.updateNote(note.id, dto)
        if (res != null) {
            localDataSource.updateNote(note.copy(syncState = SyncState.SYNCED))
            Log.d("HandlePending", "Update state: note update")
        } else {
            localDataSource.updateSyncState(note.id, SyncState.PENDING_UPDATE)
            Log.d("HandlePending", "Update state: update pending")
        }

    }

    private suspend fun handlePendingDelete(note: NoteEntity) {
        val success = remoteDataSources.deleteNote(note.id)
        if (success) {
            localDataSource.deleteNoteById(note.id)
        } else {
            localDataSource.updateSyncState(note.id, SyncState.PENDING_DELETE)
        }

    }

}