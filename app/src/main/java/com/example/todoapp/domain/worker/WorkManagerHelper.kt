package com.example.todoapp.domain.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WorkManagerHelper {

    fun schedulePeriodicSync(context: Context) {
        val constrains = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constrains)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "notes_syn",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }

}