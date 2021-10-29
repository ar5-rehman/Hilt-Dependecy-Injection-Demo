package com.oui.parisiproject.data.model.discoveryModel

import com.oui.parisiproject.domain.model.discoveryModels.Weather

data class WeatherDTO(
    val date: String?,
    val hour: String?,
    val icon: String?,
    val temperature: Int?
)

fun WeatherDTO.toDomainWeather() : Weather {
    return Weather(
        date = this.date?:"",
        hour = this.hour?:"",
        icon = this.icon?:"",
        temperature = this.temperature?:0
    )
}