package com.udacity.asteroidradar.network.retrofit

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.network.api.AsteroidServiceApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object AsteroidService {
    enum class GetAsteroidsFilter(val value: String) { TODAY("today"), WEEK("week"), SAVED("saved") }

    private val interceptor =
        if (BuildConfig.DEBUG)
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        else
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(httpClient)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        )
        .baseUrl(Constants.BASE_URL)
        .build()

    val api: AsteroidServiceApi by lazy { retrofit.create(AsteroidServiceApi::class.java) }
}