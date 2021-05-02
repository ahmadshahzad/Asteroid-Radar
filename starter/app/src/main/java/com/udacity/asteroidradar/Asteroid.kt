package com.udacity.asteroidradar

import android.os.Parcelable
import com.udacity.asteroidradar.database.Asteroid
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(
    val id: Long, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable {

    fun toAsteroidDatabaseModel(): Asteroid {
        return Asteroid(
            id = id,
            codeName = codename,
            closeApproachDate = closeApproachDate,
            absoluteMagnitude = absoluteMagnitude,
            estimatedDiameter = estimatedDiameter,
            relativeVelocity = relativeVelocity,
            distanceFromEarth = distanceFromEarth,
            isPotentiallyHazardous = isPotentiallyHazardous
        )
    }
}