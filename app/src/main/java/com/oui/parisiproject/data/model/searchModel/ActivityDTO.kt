package com.oui.parisiproject.data.model.searchModel

import com.google.gson.annotations.SerializedName
import com.oui.parisiproject.domain.model.searchModel.Activity

data class ActivityDTO(

    @SerializedName("id")
    var id: Int?,

    @SerializedName("lat")
    var lat: Double?,

    @SerializedName("lng")
    var lng: Double?,

    @SerializedName("categories")
    var activityCategoryDTOS: List<ActivityCategoryDTO>?,

    @SerializedName("location_name")
    var locationName: String?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("pictures")
    var pictures: List<String>?,

    @SerializedName("icon")
    var icon: String?,

    @SerializedName("icon_active")
    var iconActive: String?,

    @SerializedName("pin")
    var pin: String?,

    @SerializedName("pin_active")
    var pinActive: String?,

    @SerializedName("working_hours")
    var workingHourDTOS: List<WorkingHourDTO>?,

    @SerializedName("display_cat_sub_cat")
    var displayCatSubCatDTO: DisplayCatSubCatDTO?,

    @SerializedName("date_text")
    var dateText: String?,

    @SerializedName("hours_text")
    var hoursText: String?

)

fun ActivityDTO.toDomainActivity(): Activity{
    return Activity(
        id = this.id?:0,
        locationName = this.locationName?:"",
        name = this.name?:"",
        pictures = this.pictures?: ArrayList(),
        pinActive = this.pinActive?:"",
        imgSrc = "",
        displayCatSubCatName = this.displayCatSubCatDTO?.name?:"",
        distance = 0.0F,
        workingHours = this.workingHourDTOS?:ArrayList(),
        lat = this.lat?:0.0,
        lng = this.lng?:0.0
    )
}
