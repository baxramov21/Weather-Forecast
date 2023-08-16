package com.baxramov.data.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DayForecastDto(
    @SerializedName("date")
    @Expose
    var date: String? = null,

    @SerializedName("day")
    @Expose
    var weatherInfo: WeatherInfoDto? = null
)