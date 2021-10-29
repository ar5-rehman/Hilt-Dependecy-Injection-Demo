package com.oui.parisiproject.data.model.searchModel

import com.google.gson.annotations.SerializedName
import com.oui.parisiproject.domain.model.searchModel.Category

data class CategoryDTO(

    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String? ,

    @SerializedName("icon")
    var icon: String? ,

    @SerializedName("icon_active")
    var iconActive: String? ,

    @SerializedName("pin")
    var pin: String? ,

    @SerializedName("pin_active")
    var pinActive: String?

)

fun CategoryDTO.toDomainCategory(): Category {
    return Category(id = this.id, name = this.name?:"")
}
