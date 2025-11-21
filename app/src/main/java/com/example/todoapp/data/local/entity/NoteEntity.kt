package com.example.todoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.data.util.SyncState

@Entity(tableName = "note_table")
data class NoteEntity(
    @PrimaryKey val id: Int = 0,
    val title: String,
    val body: String,
    val keyColor: String,
    val lastModified: Long = System.currentTimeMillis(),
    val syncState: SyncState = SyncState.SYNCED
)
