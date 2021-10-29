package com.oui.parisiproject.data.model.searchModel

import com.google.gson.annotations.SerializedName

data class DisplayCatSubCatDTO(

    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("icon")
    var icon: String? = null,

    @SerializedName("icon_active")
    var iconActive: String? = null,

    @SerializedName("pin")
    var pin: String? = null,

    @SerializedName("pin_active")
    var pinActive: String? = null

)
