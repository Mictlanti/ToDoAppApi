package com.example.todoapp.data.mappers

import com.example.todoapp.data.local.entity.NoteEntity
import com.example.todoapp.data.remote.dto.NoteDto
import com.example.todoapp.data.util.SyncState
import com.example.todoapp.domain.model.Notes

fun NoteDto.toEntity() : NoteEntity = NoteEntity(
    id = id,
    title = title,
    body = body,
    keyColor = color,
    lastModified = System.currentTimeMillis(),
    syncState = SyncState.SYNCED
)

fun NoteEntity.toDomain() = Notes(
    id = id,
    title = title,
    body = body,
    keyColor = keyColor
)

fun NoteEntity.toDto(): NoteDto = NoteDto(
    id = this.id,
    title = title,
    body = body,
    color = keyColor
)