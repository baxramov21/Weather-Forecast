package com.baxramov.data.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ForecastsListDto (
    @SerializedName("forecastday")
    @Expose
    var forecastsList: List<ForecastDay>? = null
)