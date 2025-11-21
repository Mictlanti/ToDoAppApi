package com.example.todoapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.local.NoteDatabase
import com.example.todoapp.data.local.dao.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Provides
    @Singleton
    fun providesDb(
        @ApplicationContext context: Context
    ): NoteDatabase {
        return Room.databaseBuilder<NoteDatabase>(
            context,
            "note_db"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun providesDao(db: NoteDatabase) : NoteDao = db.noteDao()
}