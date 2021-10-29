package com.oui.parisiproject.presentation.search

import com.oui.parisiproject.domain.model.searchModel.*

data class SearchUIState(
    val isLoading: Boolean = false,
    val categoriesData: List<Category>? = null,
    val subCategoriesData: List<SubCategory>? = null,
    val activitiesData: List<Activity>? = null,
    val eventsData: List<Event>? = null,
    val guidesData: List<Guide>? = null,
    val error: String = ""
)