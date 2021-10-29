package com.oui.parisiproject.domain.model.searchModel

import com.oui.parisiproject.data.model.searchModel.WorkingHourDTO

data class Activity(

    val id: Int,
    val locationName: String,
    val name: String,
    val pictures: List<String>,
    val pinActive: String,
    val imgSrc: String,
    val displayCatSubCatName: String,
    val distance: Float?,
    val workingHours: List<WorkingHourDTO>,
    val lat: Double? = null,
    val lng: Double? = null

)
