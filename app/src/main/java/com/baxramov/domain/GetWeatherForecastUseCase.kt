package com.baxramov.domain

class GetWeatherForecastUseCase(private val repository: Repository) {
    operator fun invoke(dayNumber: Int) = repository.getWeatherForecast(dayNumber)
}