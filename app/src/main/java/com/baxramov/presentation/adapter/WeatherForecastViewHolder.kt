package com.baxramov.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.baxramov.databinding.WeatherForecastItemBinding

// todo: перемести в место использования с inner classes
class WeatherForecastViewHolder(val weatherInfoItemBinding: WeatherForecastItemBinding) :
    RecyclerView.ViewHolder(weatherInfoItemBinding.root)