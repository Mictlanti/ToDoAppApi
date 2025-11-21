package com.example.todoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.local.entity.NoteEntity
import com.example.todoapp.data.util.SyncState
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteRest(listNotes: List<NoteEntity>)

    @Query("SELECT * FROM note_table")
    fun getAllNotes(): List<NoteEntity>

    @Query("SELECT * FROM note_table WHERE id = :id")
    fun getNoteById(id: Int): NoteEntity?

    @Query("DELETE FROM note_table WHERE id = :id")
    suspend fun deleteNoteById(id: Int)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Query("SELECT * FROM note_table WHERE syncState != :synced")
    suspend fun getPendingNotes(synced: SyncState = SyncState.SYNCED) : List<NoteEntity>

    @Query("UPDATE note_table SET syncState = :state WHERE id = :id")
    suspend fun updateSyncState(id: Int, state: SyncState)

}