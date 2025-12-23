package com.example.todoapp.data.remote.api

import com.example.todoapp.data.remote.dto.NoteDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("Mictlanti/ToDoAppGsons/refs/heads/main/NoteGson")
    suspend fun getNotes() : List<NoteDto>

    //Endpoint hipotético para la escritura en API
    @POST("notes")
    suspend fun createNote(@Body dto: NoteDto) : NoteDto

    @PUT("notes/{id}")
    suspend fun updateNote(@Path("id") id: Int, @Body dto: NoteDto) : NoteDto

    @DELETE("notes/{id}")
    suspend fun deleteNote(@Path("id") id: Int)

}