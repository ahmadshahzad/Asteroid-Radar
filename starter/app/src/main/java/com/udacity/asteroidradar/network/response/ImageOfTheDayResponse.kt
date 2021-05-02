package com.udacity.asteroidradar.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class ImageOfTheDayResponse(
    @Json(name = "copyright") val copyright: String,
    @Json(name = "date") val date: String,
    @Json(name = "explanation") val explanation: String,
    @Json(name = "hdurl") val hdUrl: String,
    @Json(name = "media_type") val mediaType: String,
    @Json(name = "service_version") val serviceVersion: String,
    @Json(name = "title") val title: String,
    @Json(name = "url") val url: String
)