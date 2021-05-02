package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.main.reposiory.AsteroidRepository
import com.udacity.asteroidradar.network.response.ImageOfTheDayResponse
import com.udacity.asteroidradar.network.retrofit.AsteroidService
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(private val repository: AsteroidRepository) : ViewModel() {
    private val filterLiveData = MutableLiveData<AsteroidService.GetAsteroidsFilter>()
    private lateinit var startDate: String
    private lateinit var endDate: String
    private val _imageOfTheDay = MutableLiveData<ImageOfTheDayResponse>()
    val imageOfTheDay: LiveData<ImageOfTheDayResponse>
        get() = _imageOfTheDay

    init {
        fetchImageOfTheDay()
        updateAsteroidsFilter(AsteroidService.GetAsteroidsFilter.TODAY)
    }


    val asteroids = switchMap(filterLiveData) {
        repository.getAllAsteroids(startDate, endDate)
    }

    fun updateAsteroidsFilter(filter: AsteroidService.GetAsteroidsFilter) {
        val sdf = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        when (filter) {
            AsteroidService.GetAsteroidsFilter.TODAY -> {
                startDate = sdf.format(Date())
                endDate = sdf.format(Date())
            }
            AsteroidService.GetAsteroidsFilter.WEEK -> {
                startDate = sdf.format(Date())
                endDate = sdf.format(Calendar.getInstance().let {
                    it.add(Calendar.DAY_OF_YEAR, Constants.DEFAULT_END_DATE_DAYS)
                    it.time
                })
            }
            AsteroidService.GetAsteroidsFilter.SAVED -> {
                startDate = sdf.format(Date())
                endDate = sdf.format(Date())
            }
        }
        filterLiveData.value = filter
    }

    private fun fetchImageOfTheDay() {
        viewModelScope.launch {
            try {
                _imageOfTheDay.value = repository.getImageOfTheDay()
            } catch (e: Exception) {
                // don't need to do anything. We are not showing any message.
            }
        }
    }
}