package com.oui.parisiproject.data.model.searchModel

import com.google.gson.annotations.SerializedName

data class EntityDTO(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("date")
    var date: String? = null,

    @SerializedName("start_time")
    var startTime: String? = null,

    @SerializedName("end_time")
    var endTime: String? = null

)
