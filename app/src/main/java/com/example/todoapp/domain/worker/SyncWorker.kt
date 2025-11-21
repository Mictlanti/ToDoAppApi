package com.example.todoapp.domain.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.domain.repository.NoteDataRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams : WorkerParameters,
    private val repo: NoteDataRepo
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            repo.syncPending()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

}