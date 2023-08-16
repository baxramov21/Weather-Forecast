package com.baxramov.data.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherConditionDto(
    @SerializedName("text")
    @Expose
    var text: String? = null,

    @SerializedName("icon")
    @Expose
    var icon: String? = null
)