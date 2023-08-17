package com.baxramov.data

import android.content.Context
import com.baxramov.data.converters.Mapper
import com.baxramov.data.network.ApiFactory
import com.baxramov.domain.Repository
import com.baxramov.domain.WeatherInfoEntity
import retrofit2.HttpException
import java.io.IOException

class RepositoryImpl(private val context: Context) : Repository {

    private val apiService = ApiFactory.apiService
    private val mapper = Mapper()

    override fun getWeatherForecast(
        location: String,
        forecastLengthInDays: String,
        apiKey: String
    ): List<WeatherInfoEntity> {

        try {
            // todo вынеси вызов usecase во вьюмодель и обрабатывай там
            val weatherGeneralInfo =
                apiService.getWeatherGeneralInfo(
                    location,
                    forecastLengthInDays,
                    apiKey
                )

            val weatherForecastsDtoList =
                weatherGeneralInfo.forecast?.forecastsList ?: return emptyList()

            val weatherDataContainerList =
                mapper.mapForecastListToDataContainerList(weatherForecastsDtoList)

            return mapper.mapDataContainerListToEntityList(weatherDataContainerList)
        } catch (exception: IOException) {
            //todo: добавь обработку ошибок. покажи пользователю тост хотя бы и залогируй ошибку
            //а можно не тост, а картинку
            return emptyList()
        } catch (exception: HttpException) {
            return emptyList()
        }
    }
}