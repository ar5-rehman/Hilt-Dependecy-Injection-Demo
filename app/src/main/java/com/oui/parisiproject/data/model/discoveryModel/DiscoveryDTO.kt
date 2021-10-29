package com.oui.parisiproject.data.model.discoveryModel

import com.google.gson.annotations.SerializedName

data class DiscoveryDTO(
    @SerializedName("categories") val categoryDTOS: List<CategoryDTO>,
    @SerializedName("sections") val sectionDTOS: List<SectionDTO>,
    @SerializedName("weather") val weatherDTO: List<WeatherDTO>,
)