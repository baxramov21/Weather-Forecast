package com.baxramov.presentation.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

// todo используй inner class, чтобы не плодить файлы
class MyViewModelFactory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(WeatherForecastViewModel::class.java)) {
            return WeatherForecastViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown class $modelClass")
    }
}