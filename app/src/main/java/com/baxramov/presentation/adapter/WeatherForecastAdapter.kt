package com.baxramov.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.baxramov.R
import com.baxramov.databinding.WeatherForecastItemBinding
import com.baxramov.domain.entity.WeatherInfoEntity
import com.bumptech.glide.Glide

class WeatherForecastAdapter(private val context: Context) :
    ListAdapter<WeatherInfoEntity, WeatherForecastViewHolder>(DiffUtilItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): WeatherForecastViewHolder {
        val inflatedView = WeatherForecastItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WeatherForecastViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        val weatherForecast: WeatherInfoEntity = getItem(position)
        with(holder.weatherInfoItemBinding) {
            with(weatherForecast) {

                Glide.with(context)
                    .load(weatherConditionIconUrl)
                    .into(imageViewWeatherStateIcon)

                val windStringTemplate = getStringTemplate(context, R.string.wind, wind)
                val humidityStringTemplate = getStringTemplate(context, R.string.humidity, humidity)

                tvTemperature.text = temperature
                tvWind.text = windStringTemplate
                tvHumidity.text = humidityStringTemplate
                tvWeatherState.text = weatherCondition
                tvDate.text = date
            }
        }
    }

    private fun getStringTemplate(context: Context, templateId: Int, value: String): String =
        String.format(context.resources.getString(templateId), value)

}