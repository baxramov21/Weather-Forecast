package com.baxramov.domain

data class WeatherInfoEntity(
    val temperature: Float,
    val wind: Float,
    val humidity: Int,
    val weatherCondition: String,
    val weatherConditionIcon: String
)