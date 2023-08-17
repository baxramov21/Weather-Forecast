package com.baxramov.domain.repository

import com.baxramov.domain.entity.WeatherInfoEntity
import com.baxramov.domain.entity.Result


interface Repository {

    suspend fun getWeatherForecast(
        location: String,
        forecastLengthInDays: String,
        apiKey: String
    ): Result<List<WeatherInfoEntity>>
}