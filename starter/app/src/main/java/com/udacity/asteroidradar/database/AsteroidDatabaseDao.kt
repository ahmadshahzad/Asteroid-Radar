package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroid: Asteroid)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroidList: List<Asteroid>)

    @Update
    suspend fun update(asteroid: Asteroid)

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate DESC")
    fun getAllAsteroids(startDate: String, endDate: String): LiveData<List<Asteroid>>

    @Query("DELETE FROM asteroid_table WHERE closeApproachDate < :startDate")
    fun deleteOldAsteroids(startDate: String)
}