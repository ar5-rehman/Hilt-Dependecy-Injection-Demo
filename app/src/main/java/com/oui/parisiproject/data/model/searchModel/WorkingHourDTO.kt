package com.oui.parisiproject.data.model.searchModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WorkingHourDTO(

    @SerializedName("id")
    var id: Int?,

    @SerializedName("activity")
    var activity: Int?,

    @SerializedName("day_of_week")
    var dayOfWeek: Int?,

    @SerializedName("closed")
    var closed: Boolean?,

    @SerializedName("open_time1")
    var openTime1: String?,

    @SerializedName("close_time1")
    var closeTime1: String?,

    @SerializedName("open_time2")
    var openTime2: String?,

    @SerializedName("close_time2")
    var closeTime2: String?

)
