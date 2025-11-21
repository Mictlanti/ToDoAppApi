package com.example.todoapp.data.remote

import android.util.Log
import com.example.todoapp.data.remote.api.ApiService
import com.example.todoapp.data.remote.dto.NoteDto
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteDataSources @Inject constructor(private val apiService: ApiService) {

    suspend fun getNotes(): List<NoteDto> = try {
        apiService.getNotes()
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("RemoteData", "Error getNote: ${e.message}")
        emptyList()
    }

    suspend fun createNote(dto: NoteDto) : NoteDto? = try {
        apiService.createNote(dto)
    } catch (e: Exception) {
        Log.e("RemoteData", "Error  create note: ${e.message}")
        null
    }

    suspend fun updateNote(id: Int, dto: NoteDto) : NoteDto? = try {
        apiService.updateNote(id, dto)
    } catch (e: Exception) {
        Log.e("RemoteData", "Error update ${e.message}")
        null
    }

    suspend fun deleteNote(id: Int) : Boolean = try {
        apiService.deleteNote(id)
        true
    } catch (e: Exception) {
        Log.e("RemoteData", "Error delete ${e.message}")
        false
    }

}