package com.baxramov.domain.use_case

import com.baxramov.domain.repository.Repository

class GetWeatherForecastUseCase(private val repository: Repository) {
    suspend operator fun invoke(location: String, forecastLengthInDays: String, apiKey: String) =
        repository.getWeatherForecast(location, forecastLengthInDays, apiKey)
}