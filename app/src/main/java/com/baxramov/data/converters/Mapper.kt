package com.baxramov.data.converters

import com.baxramov.data.WeatherDataContainer
import com.baxramov.data.network.dto.DayForecastDto
import com.baxramov.domain.WeatherInfoEntity

class Mapper {

//    fun mapWeatherForecastsListToWeatherInfoEntity(weatherForecastList: ForecastsListDto) {
//        val weatherInfoEntityList = mutableListOf<WeatherInfoEntity>()
//        weatherForecastList.forecastsList?.map {
//            val weatherInfo = it.weatherInfo
//            with(weatherInfo!!) {
//                weatherInfoEntityList.add(
//                    WeatherInfoEntity(
//                        it.date!!,
//                        avgTempC,
//                        maxWindKph,
//                        avgHumidity,
//                        condition.text
//
//                    )
//                )
//            }
//        }
//    }

    fun mapForecastListToDataContainerList(forecastsList: List<DayForecastDto>): List<WeatherDataContainer> {
        val dataContainersList = mutableListOf<WeatherDataContainer>()
        forecastsList.map {
            dataContainersList.add(WeatherDataContainer(it.date!!, it.weatherInfo!!))
        }
        return dataContainersList
    }

    fun mapDataContainerListToEntityList(dataContainersList: List<WeatherDataContainer>): List<WeatherInfoEntity> {
        val entitiesList = mutableListOf<WeatherInfoEntity>()
        dataContainersList.map {
            with(it.weatherInfo) {
                entitiesList.add(
                    WeatherInfoEntity(
                        it.date,
                        this.avgTempC,
                        maxWindKph,
                        avgHumidity,
                        condition?.text!!,
                        condition?.icon!!
                    )
                )
            }
        }
        return entitiesList
    }
}