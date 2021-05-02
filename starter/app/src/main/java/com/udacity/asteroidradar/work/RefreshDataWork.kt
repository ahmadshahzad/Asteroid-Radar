package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.main.reposiory.AsteroidRepository
import com.udacity.asteroidradar.network.retrofit.AsteroidService
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*

class RefreshDataWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        return try {
            val sdf = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
            val startDate = sdf.format(Date())
            val endDate = sdf.format(Calendar.getInstance().let {
                it.add(Calendar.DAY_OF_YEAR, Constants.DEFAULT_END_DATE_DAYS)
                it.time
            })

            val dataSource = AsteroidDatabase.getInstance(applicationContext).asteroidDatabaseDao
            dataSource.deleteOldAsteroids(startDate)

            val repository = AsteroidRepository(AsteroidService.api, dataSource)
            repository.fetchAsteroids(startDate, endDate)
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    companion object {
        const val WORKER_NAME = "RefreshDataWork"
    }
}