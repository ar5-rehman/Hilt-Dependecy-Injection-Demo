package com.oui.parisiproject.data.model.searchModel

import com.google.gson.annotations.SerializedName
import com.oui.parisiproject.domain.model.searchModel.Guide

data class GuideDTO(

    @SerializedName("id")
    var id: Int?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("subtitle")
    var subtitle: String?,

    @SerializedName("description")
    var description: String?,

    @SerializedName("pictures")
    var pictures: List<String>?,

    @SerializedName("picture")
    var picture: String?,

    @SerializedName("duration")
    var duration: String?,

    @SerializedName("activities_count")
    var activitiesCount: Int?,

    @SerializedName("tag")
    var tag: String?,

    @SerializedName("in_bbox")
    var inBbox: String?,

    @SerializedName("line")
    var line: List<String>?,

    @SerializedName("last_update")
    var lastUpdate: String?,

    @SerializedName("tour_guide")
    var tourGuide: List<String>?,

    @SerializedName("default_sort")
    var defaultSort: String?

)

fun GuideDTO.toDomainGuide(): Guide{
    return Guide(
        id = this.id?:0,
        name = this.name?:"",
        picture = this.picture?:""
    )
}
