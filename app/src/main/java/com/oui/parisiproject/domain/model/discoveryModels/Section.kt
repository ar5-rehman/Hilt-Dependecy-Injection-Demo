package com.oui.parisiproject.domain.model.discoveryModels

import com.oui.parisiproject.data.model.discoveryModel.ItemDTO

data class Section(
    val itemDTOS: List<ItemDTO>,
    val section_title: String
)