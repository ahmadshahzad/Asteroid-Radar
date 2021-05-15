package com.udacity.asteroidradar.main.reposiory

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.network.api.AsteroidServiceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(
    private val api: AsteroidServiceApi,
    private val asteroidDao: AsteroidDatabaseDao
) {
    suspend fun fetchAsteroids(startDate: String, endDate: String) {
        return withContext(Dispatchers.IO) {
            val asteroidJson = api.getAsteroidsFeed(startDate, endDate)
            val asteroids: List<Asteroid> = parseAsteroidsJsonResult(JSONObject(asteroidJson.string()))
            val dbAsteroids = asteroids.map {
                it.toAsteroidDatabaseModel()
            }
            asteroidDao.insertAll(dbAsteroids)
        }
    }

    suspend fun getImageOfTheDay() : PictureOfDay{
        return withContext(Dispatchers.IO) {
            api.getImageOfTheDay()
        }
    }

    fun getAllAsteroids(startDate: String, endDate: String): LiveData<List<com.udacity.asteroidradar.database.Asteroid>> {
        return asteroidDao.getAllAsteroids(startDate, endDate)
    }
}