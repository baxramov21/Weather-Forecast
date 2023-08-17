package com.baxramov.domain.entity

data class WeatherInfoEntity(
    val date: String,
    val temperature: String,
    val wind: String,
    val humidity: String,
    val weatherCondition: String,
    val weatherConditionIconUrl: String
)