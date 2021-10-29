package com.oui.parisiproject.domain.model.searchModel

import com.oui.parisiproject.data.model.searchModel.PictureDTO

data class Event(

    val id: Int,
    val name: String,
    val displayCatSubCatName: String,
    val picture: List<PictureDTO>,
    val imgSrc: String,
    val distance: Float,
    val lat: Double? = null,
    val lng: Double? = null

)
