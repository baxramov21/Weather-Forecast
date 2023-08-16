package com.baxramov.data.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GeneralInfoContainerDto(
    @SerializedName("location")
    @Expose
    var location: LocationDto? = null,

    @SerializedName("forecast")
    @Expose
    var forecast: ForecastsListDto? = null
)