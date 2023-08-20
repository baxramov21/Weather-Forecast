package com.baxramov.data.repository

import android.util.Log
import com.baxramov.data.converters.Mapper
import com.baxramov.data.network.ApiFactory
import com.baxramov.domain.entity.WeatherInfoEntity
import com.baxramov.domain.repository.Repository
import com.baxramov.domain.entity.Result
import retrofit2.HttpException
import java.io.IOException

class RepositoryImpl : Repository {

    private val apiService = ApiFactory.apiService
    private val mapper = Mapper()

    override suspend fun getWeatherForecast(
        location: String,
        forecastLengthInDays: String,
        apiKey: String
    ): Result<List<WeatherInfoEntity>> {
        return try {
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

            val entityList = mapper.mapDataContainerListToEntityList(weatherDataContainerList)

            Result.Success(entityList)

        } catch (exception: IOException) {
            Log.e(TAG, "IO Exception: ${exception.message}")
            Result.InternetError(INTERNET_ERROR)

        } catch (exception: HttpException) {
            Log.e(TAG, "Http exception: ${exception.message}")
            Result.IncorrectCityNameError(INCORRECT_CITY_NAME)
        }
    }

    companion object {
        private const val TAG = "RepositoryImpl"
        const val INTERNET_ERROR = "Check internet connection"
        const val INCORRECT_CITY_NAME = "Incorrect city name. Check and try again"
    }
}