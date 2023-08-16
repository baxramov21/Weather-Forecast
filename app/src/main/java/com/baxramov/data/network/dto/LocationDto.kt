package com.baxramov.data.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("name")
    @Expose
    var location: String? = null,

    @SerializedName("region")
    @Expose
    var region: String? = null,

    @SerializedName("country")
    @Expose
    var country: String? = null,

    @SerializedName("localtime")
    @Expose
    var localtime: String? = null
)