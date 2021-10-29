package com.oui.parisiproject.data.model.discoveryModel

import com.google.gson.annotations.SerializedName
import com.oui.parisiproject.domain.model.discoveryModels.Section

data class SectionDTO(
    @SerializedName("items") val itemDTOS: List<ItemDTO>?,
    @SerializedName("section_title") val section_title: String?,
    @SerializedName("view_all_request") val view_all_request: String?
)

fun SectionDTO.toDomainSection(): Section {
    return Section(
        itemDTOS = this.itemDTOS?: ArrayList(),
        section_title = this.section_title?:""
    )
}