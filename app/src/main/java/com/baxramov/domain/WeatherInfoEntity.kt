package com.baxramov.domain

data class WeatherInfoEntity(
    val date: String,
    val temperature: Double,
    val wind: Double,
    val humidity: Double,
    val weatherCondition: String,
    val weatherConditionIcon: String
)