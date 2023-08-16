package com.baxramov.data

import com.baxramov.data.network.ApiFactory
import com.baxramov.domain.Repository
import com.baxramov.domain.WeatherInfoEntity

class RepositoryImpl : Repository {

    private val apiService = ApiFactory.apiService

    override fun getLocation(): String {
        TODO("Not yet implemented")
    }

    override fun getDate(): Int {
        TODO("Not yet implemented")
    }

    override fun getWeatherForecast(dayNumber: Int): WeatherInfoEntity {
        TODO("Not yet implemented")
    }
}