package com.baxramov.data

import com.baxramov.data.network.dto.WeatherInfoDto

data class WeatherDataContainer(
    val date: String,
    val weatherInfo: WeatherInfoDto
)
