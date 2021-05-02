package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asteroid_table")
data class Asteroid(
    @PrimaryKey
    val id: Long = 0,
    val codeName: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) {
    fun toAsteroidDataModel(): com.udacity.asteroidradar.Asteroid {
        return com.udacity.asteroidradar.Asteroid(
            id = id,
            codename = codeName,
            closeApproachDate = closeApproachDate,
            absoluteMagnitude = absoluteMagnitude,
            estimatedDiameter = estimatedDiameter,
            relativeVelocity = relativeVelocity,
            distanceFromEarth = distanceFromEarth,
            isPotentiallyHazardous = isPotentiallyHazardous
        )
    }
}