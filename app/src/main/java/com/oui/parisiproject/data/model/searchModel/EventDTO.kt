package com.oui.parisiproject.data.model.searchModel

import com.google.gson.annotations.SerializedName
import com.oui.parisiproject.domain.model.searchModel.Event

data class EventDTO(

    @SerializedName("id")
    var id: Int?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("category")
    var category: Any?,

    @SerializedName("description")
    var description: String?,

    @SerializedName("website")
    var website: Any? ,

    @SerializedName("entities")
    var entityDTOS: List<EntityDTO>? ,

    @SerializedName("pictures")
    var pictureDTOS: List<PictureDTO>? ,

    @SerializedName("display_cat_sub_cat")
    var displayCatSubCatDTO: DisplayCatSubCatDTO?,

    @SerializedName("tour_guide")
    var tourGuide: List<Any>?,

    @SerializedName("lat")
    var lat: Double? ,

    @SerializedName("lng")
    var lng: Double? ,

    @SerializedName("address")
    var address: String? ,

    @SerializedName("zip")
    var zip: String? ,

    @SerializedName("city")
    var city: String? ,

    @SerializedName("phone")
    var phone: String?

)

fun EventDTO.toDomainEvent(): Event{
    return Event(
        id = this.id?:0,
        name = this.name?:"",
        displayCatSubCatName = "",
        picture = this.pictureDTOS?:ArrayList(),
        imgSrc = "",
        distance = 0F,
        lat = this.lat?:0.0,
        lng = this.lng?: 0.0
    )
}
