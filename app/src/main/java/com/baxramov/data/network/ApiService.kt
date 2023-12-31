package com.baxramov.data.network

import com.baxramov.data.network.dto.GeneralInfoContainerDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(WEATHER_FORECAST_URL)
    suspend fun getWeatherGeneralInfo(
        @Query(LOCATION_QUERY_PARAM) location: String,
        @Query(PERIOD_QUERY_PARAM) period: String,
        @Query(API_KEY_QUERY_PARAM) query: String,
    ): GeneralInfoContainerDto

    companion object {

        const val API_KEY_QUERY_PARAM = "key"
        const val LOCATION_QUERY_PARAM = "q"
        const val PERIOD_QUERY_PARAM = "days"
        const val WEATHER_FORECAST_URL = "v1/forecast.json?"
    }
}