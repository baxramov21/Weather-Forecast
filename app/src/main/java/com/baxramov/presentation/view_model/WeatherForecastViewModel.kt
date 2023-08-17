package com.baxramov.presentation.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baxramov.data.repository.RepositoryImpl
import com.baxramov.domain.use_case.GetWeatherForecastUseCase
import com.baxramov.domain.entity.WeatherInfoEntity
import com.baxramov.domain.entity.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class WeatherForecastViewModel(application: Application) : ViewModel() {

    private val repository = RepositoryImpl()

    private val getWeatherForecastUseCase = GetWeatherForecastUseCase(repository)

    private val _lvWeatherForecastList = MutableLiveData<List<WeatherInfoEntity>>()
    val lvWeatherForecastList: LiveData<List<WeatherInfoEntity>>
        get() = _lvWeatherForecastList

    private val _lvError = MutableLiveData<String>()
    val lvError: LiveData<String>
        get() = _lvError

    private var cityName: String = ""

    fun getCityName() = cityName

    fun setCityName(cityName: String) {
        this.cityName = cityName
    }

    suspend fun getWeatherForecast(location: String, period: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getWeatherForecastUseCase(location, period, API_KEY)) {
                is Result.Success -> {
                    _lvWeatherForecastList.postValue(result.data as List<WeatherInfoEntity>)
                }
                is Result.Error -> {
                    _lvError.postValue(result.message)
                }
            }
        }
    }


    companion object {
        private const val API_KEY = "8700fad4450f4990b18104022231508"
    }
}