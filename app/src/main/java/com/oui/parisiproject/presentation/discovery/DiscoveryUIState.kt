package com.oui.parisiproject.presentation.discovery

import com.oui.parisiproject.data.model.discoveryModel.DiscoveryDTO
import com.oui.parisiproject.domain.model.discoveryModels.Category
import com.oui.parisiproject.domain.model.discoveryModels.Section
import com.oui.parisiproject.domain.model.discoveryModels.Weather

data class DiscoveryUIState(
    val isLoading: Boolean = false,
    val discoveryData: DiscoveryDTO? = null,
    val weatherData: List<Weather>? = null,
    val categoriesData: List<Category>? = null,
    val sectionData: List<Section>? = null,
    val error: String = "")