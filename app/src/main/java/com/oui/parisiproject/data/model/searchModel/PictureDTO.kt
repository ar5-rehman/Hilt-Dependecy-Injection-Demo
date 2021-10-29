package com.oui.parisiproject.data.model.searchModel

import com.google.gson.annotations.SerializedName

data class PictureDTO(

    @SerializedName("picture")
    var picture: String? = null,

    @SerializedName("is_featured")
    var isFeatured: Boolean? = null,

    @SerializedName("caption")
    var caption: Any? = null,

    @SerializedName("priority")
    var priority: Int? = null

)
