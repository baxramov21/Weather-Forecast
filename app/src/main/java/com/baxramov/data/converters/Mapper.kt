package com.baxramov.data.converters

import com.baxramov.data.WeatherDataContainer
import com.baxramov.data.network.dto.DayForecastDto
import com.baxramov.domain.entity.WeatherInfoEntity

class Mapper {

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
                        fixTemperature(this.avgTempC),
                        maxWindKph.toString(),
                        fixHumidity(avgHumidity),
                        condition?.text!!,
                        fixIconUrl(condition?.icon!!)
                    )
                )
            }
        }
        return entitiesList
    }

    private fun fixIconUrl(iconUrl: String): String = "https:$iconUrl"

    private fun fixTemperature(temp: Double): String = "$temp°"

    private fun fixHumidity(humidity: Double): String = "$humidity%"
}