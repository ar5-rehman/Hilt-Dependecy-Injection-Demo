package com.oui.parisiproject.data.model.searchModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.oui.parisiproject.domain.model.searchModel.SubCategory

data class SubCategoryDTO(

    @SerializedName("id")
    var id: Int?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("category")
    var category: Int?,

    @SerializedName("icon")
    var icon: String?,

    @SerializedName("icon_active")
    var iconActive: String?,

    @SerializedName("pin")
    var pin: String?,

    @SerializedName("pin_active")
    var pinActive: String?

)

fun SubCategoryDTO.toDomainSubCategory(): SubCategory{
    return SubCategory(id = this.id?:0, name = this.name?:"")
}
