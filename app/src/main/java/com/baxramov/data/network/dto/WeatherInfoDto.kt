package com.baxramov.data.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherInfoDto (
    @SerializedName("avgtemp_c")
    @Expose
    var avgTempC: Double = 0.0,

    @SerializedName("maxwind_kph")
    @Expose
    var maxWindKph: Double = 0.0,

    @SerializedName("avghumidity")
    @Expose
    var avgHumidity: Double = 0.0,

    @SerializedName("condition")
    @Expose
    var condition: WeatherConditionDto? = null
)