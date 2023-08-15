package com.baxramov.domain

interface Repository {

    fun getLocation(): String

    fun getDate(): Int

    fun getWeatherForecast(dayNumber: Int): WeatherInfoEntity
}