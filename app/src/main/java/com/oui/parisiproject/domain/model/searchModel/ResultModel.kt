package com.oui.parisiproject.domain.model.searchModel

data class ResultModel(

    var imgsList: List<String>?,
    var activePin: String,
    var id: Int,
    var imgSrc: String,
    var title: String,
    var subTitle: String,
    var hour: String,
    var distance: Float,
    var name: String? = null

)