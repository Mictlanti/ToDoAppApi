package com.example.todoapp.data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.todoapp.domain.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {

    @Provides
    @Singleton
    fun provideSharedPref(
        @ApplicationContext context: Context
    ) : SharedPreferences {
        return context.getSharedPreferences(Constants.USER_PREFS, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPrefEdit(
        sharedPreferences: SharedPreferences
    ) : SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

}