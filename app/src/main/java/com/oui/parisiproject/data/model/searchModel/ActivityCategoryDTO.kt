package com.oui.parisiproject.data.model.searchModel

import com.google.gson.annotations.SerializedName

data class ActivityCategoryDTO(

    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null

)
