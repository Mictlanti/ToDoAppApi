package com.example.todoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.data.local.dao.NoteDao
import com.example.todoapp.data.local.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 8)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao() : NoteDao

}