package com.example.todoapp.data.mappers

import com.example.todoapp.data.remote.dto.NoteDto
import com.example.todoapp.domain.model.Notes

fun NoteDto.toDomain() = Notes(
    id = id,
    title = title,
    body = body,
    keyColor = color
)