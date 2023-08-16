package com.baxramov.domain

class GetWeatherForecastUseCase(private val repository: Repository) {
    operator fun invoke(location: String, forecastLengthInDays: Int, apiKey: String) =
        repository.getWeatherForecast(location, forecastLengthInDays, apiKey)
}