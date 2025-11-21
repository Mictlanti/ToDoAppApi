package com.example.todoapp.data.util

import com.example.todoapp.data.local.entity.NoteEntity
import com.example.todoapp.data.mappers.toDomain
import com.example.todoapp.data.mappers.toEntity
import com.example.todoapp.data.remote.dto.NoteDto
import com.example.todoapp.domain.model.Notes

fun List<NoteDto>.toEntityList() = map { it.toEntity() }
fun List<NoteEntity>.toDomainList() : List<Notes> = map { it.toDomain() }