package com.oui.parisiproject.data.model.searchModel

import com.google.gson.annotations.SerializedName

data class SearchDTO(
    @SerializedName("activities")
    var activityDTOS: List<ActivityDTO>,

    @SerializedName("events")
    var eventDTOS: List<EventDTO>,

    @SerializedName("guides")
    var guideDTOS: List<GuideDTO>,

    @SerializedName("categories")
    var categoryDTOS: List<CategoryDTO>,

    @SerializedName("sub_categories")
    var subCategoryDTOS: List<SubCategoryDTO>

)
