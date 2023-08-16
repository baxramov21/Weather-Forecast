package com.baxramov.domain

interface Repository {

    fun getWeatherForecast(location: String, forecastLengthInDays: Int, apiKey: String): WeatherInfoEntity
}