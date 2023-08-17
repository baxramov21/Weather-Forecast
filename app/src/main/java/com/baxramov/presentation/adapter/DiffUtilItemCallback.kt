package com.baxramov.presentation.adapter

import com.baxramov.domain.entity.WeatherInfoEntity

class DiffUtilItemCallback : androidx.recyclerview.widget.DiffUtil.ItemCallback<WeatherInfoEntity>() {

    override fun areItemsTheSame(oldItem: WeatherInfoEntity, newItem: WeatherInfoEntity): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(
        oldItem: WeatherInfoEntity,
        newItem: WeatherInfoEntity
    ): Boolean {
        return oldItem == newItem
    }

}