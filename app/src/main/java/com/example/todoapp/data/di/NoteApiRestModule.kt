package com.example.todoapp.data.di

import com.example.todoapp.data.remote.RemoteDataSources
import com.example.todoapp.data.remote.api.ApiService
import com.example.todoapp.data.reposotyImpl.LocalDataSource
import com.example.todoapp.data.reposotyImpl.NoteDataRepoImpl
import com.example.todoapp.domain.repository.NoteDataRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteApiRestModule {

    private const val BASE_URL = "https://raw.githubusercontent.com/"

    @Provides
    @Singleton
    fun providesRetrofit() : Retrofit {

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit) : ApiService = retrofit
        .create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesNoteRepo(
        remoteDataSources: RemoteDataSources,
        localDataSource: LocalDataSource
    ) : NoteDataRepo {
        return NoteDataRepoImpl(remoteDataSources, localDataSource)
    }

}