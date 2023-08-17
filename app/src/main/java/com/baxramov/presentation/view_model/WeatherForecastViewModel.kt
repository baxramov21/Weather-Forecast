package com.baxramov.presentation.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baxramov.data.RepositoryImpl
import com.baxramov.data.repository.RepositoryImpl
import com.baxramov.domain.WeatherInfoEntity
import com.baxramov.domain.use_case.GetWeatherForecastUseCase
import com.baxramov.domain.entity.WeatherInfoEntity

// todo
class WeatherForecastViewModel(application: Application) : ViewModel() {

    private val repository = RepositoryImpl()

    private val getWeatherForecastUseCase = GetWeatherForecastUseCase(repository)

    private val _lvWeatherForecastList = MutableLiveData<List<WeatherInfoEntity>>()
    val lvWeatherForecastList: LiveData<List<WeatherInfoEntity>>
        get() = _lvWeatherForecastList


    suspend fun getWeatherForecast(location: String, period: Int) {
        _lvWeatherForecastList.postValue(
            getWeatherForecastUseCase(location, period.toString(), API_KEY)
        )
    }


    companion object {
        private const val API_KEY = "8700fad4450f4990b18104022231508"
    }
}