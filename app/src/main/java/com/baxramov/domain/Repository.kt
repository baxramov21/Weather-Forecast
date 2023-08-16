package com.baxramov.domain

interface Repository {

    fun getWeatherForecast(location: String, forecastLengthInDays: String, apiKey: String): List<WeatherInfoEntity>
}