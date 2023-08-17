package com.baxramov.domain.repository

import com.baxramov.domain.entity.WeatherInfoEntity

interface Repository {

    suspend fun getWeatherForecast(
        location: String,
        forecastLengthInDays: String,
        apiKey: String
    ): List<WeatherInfoEntity>
}