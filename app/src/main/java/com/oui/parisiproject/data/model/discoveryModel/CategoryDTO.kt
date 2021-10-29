package com.oui.parisiproject.data.model.discoveryModel

import com.oui.parisiproject.domain.model.discoveryModels.Category

data class CategoryDTO(
    val icon: String?,
    val icon_active: String?,
    val id: Int,
    val name: String?,
    val pin: String?,
    val pin_active: String?
)

fun CategoryDTO.toDomainCategory(): Category {
    return Category(icon = this.icon?:"",
        icon_active = icon_active?:"",
        id = this.id,
        name = this.name?:"",
        pin = this.pin?:"",
        pin_active = this.pin_active?:"")
}