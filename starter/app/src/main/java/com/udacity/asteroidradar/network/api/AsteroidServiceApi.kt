package com.udacity.asteroidradar.network.api

import com.udacity.asteroidradar.PictureOfDay
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidServiceApi {
    companion object {
        private const val API_KEY = "WgVRPKbq94QqNoBOMRzGMDQmDwv290bKYxEeRftH"
    }

    @GET("/neo/rest/v1/feed?api_key=${API_KEY}")
    suspend fun getAsteroidsFeed(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): ResponseBody

    @GET("/planetary/apod?api_key=${API_KEY}")
    suspend fun getImageOfTheDay(): PictureOfDay
}