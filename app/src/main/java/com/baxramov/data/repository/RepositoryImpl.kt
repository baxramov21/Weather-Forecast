package com.baxramov.data.repository

import android.util.Log
import com.baxramov.data.converters.Mapper
import com.baxramov.data.network.ApiFactory
import com.baxramov.domain.repository.Repository
import com.baxramov.domain.entity.WeatherInfoEntity
import retrofit2.HttpException
import java.io.IOException

class RepositoryImpl() : Repository {

    private val apiService = ApiFactory.apiService
    private val mapper = Mapper()

    override suspend fun getWeatherForecast(
        location: String,
        forecastLengthInDays: String,
        apiKey: String
    ): List<WeatherInfoEntity> {
        try {
            val weatherGeneralInfo =
                apiService.getWeatherGeneralInfo(
                    location,
                    forecastLengthInDays,
                    apiKey
                )

            val weatherForecastsDtoList = weatherGeneralInfo.forecast?.forecastsList
                ?: throw NullPointerException("ForecastsList is null")

            val weatherDataContainerList =
                mapper.mapForecastListToDataContainerList(weatherForecastsDtoList)

            return mapper.mapDataContainerListToEntityList(weatherDataContainerList)

        } catch (exception: IOException) {
            Log.e(TAG, "IO Exception: ${exception.message}")
            return emptyList()

        } catch (exception: HttpException) {
            Log.e(TAG, "Http exception: ${exception.message}")
            return emptyList()
        }
    }

    companion object {
        private const val TAG = "RepositoryImpl"
    }
}