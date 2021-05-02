package com.udacity.asteroidradar

import android.app.Application
import androidx.work.*
import com.udacity.asteroidradar.work.RefreshDataWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        setupRecurringWork()
    }

    private fun setupRecurringWork() {
        applicationScope.launch {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true)
                .build()

            val work = PeriodicWorkRequestBuilder<RefreshDataWork>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()
            WorkManager.getInstance().enqueueUniquePeriodicWork(
                RefreshDataWork.WORKER_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                work
            )
        }
    }
}