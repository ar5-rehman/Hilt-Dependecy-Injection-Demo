package com.oui.parisiproject.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oui.parisiproject.common.Resource
import com.oui.parisiproject.data.model.discoveryModel.toDomainCategory
import com.oui.parisiproject.data.model.discoveryModel.toDomainSection
import com.oui.parisiproject.data.model.discoveryModel.toDomainWeather
import com.oui.parisiproject.data.model.searchModel.*
import com.oui.parisiproject.domain.use_case.discoveryUseCase.DiscoveryUseCase
import com.oui.parisiproject.domain.use_case.discoveryUseCase.SearchUseCase
import com.oui.parisiproject.presentation.discovery.DiscoveryUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel  @Inject constructor(private val searchUseCase: SearchUseCase) :
    ViewModel() {

    private val _categoryList = MutableStateFlow<SearchUIState>(SearchUIState())
    val categoryData: StateFlow<SearchUIState> = _categoryList

    private val _subCategoryList = MutableStateFlow<SearchUIState>(SearchUIState())
    val subCategoryData: StateFlow<SearchUIState> = _subCategoryList

    private val _activitiesList = MutableStateFlow<SearchUIState>(SearchUIState())
    val activitiesData: StateFlow<SearchUIState> = _activitiesList

    private val _eventsList = MutableStateFlow<SearchUIState>(SearchUIState())
    val eventsData : StateFlow<SearchUIState> = _eventsList

    private val _guideList = MutableStateFlow<SearchUIState>(SearchUIState())
    val guidesData: StateFlow<SearchUIState> = _guideList


    fun getSearch(searchKeyWord: String) {
        searchUseCase(searchKeyWord).onEach {
            when (it) {
                is Resource.Loading -> {
                    _categoryList.value = SearchUIState(isLoading = true)
                    _subCategoryList.value = SearchUIState(isLoading = true)
                    _activitiesList.value = SearchUIState(isLoading = true)
                    _eventsList.value = SearchUIState(isLoading = true)
                    _guideList.value = SearchUIState(isLoading = true)
                }
                is Resource.Error -> {
                    _categoryList.value = SearchUIState(error = it.message ?: "")
                    _subCategoryList.value = SearchUIState(error = it.message ?: "")
                    _activitiesList.value = SearchUIState(error = it.message ?: "")
                    _eventsList.value = SearchUIState(error = it.message ?: "")
                    _guideList.value = SearchUIState(error = it.message ?: "")
                }
                is Resource.Success -> {

                    //Categories
                    if (!it.data!!.categoryDTOS.isNullOrEmpty()){
                        _categoryList.value = SearchUIState(categoriesData = it.data!!.categoryDTOS.map { it.toDomainCategory() })
                    }

                    //SubcategoryData
                    if (!it.data!!.subCategoryDTOS.isNullOrEmpty()){
                        _subCategoryList.value = SearchUIState(subCategoriesData = it.data!!.subCategoryDTOS.map { it.toDomainSubCategory() })
                    }

                    //ActivitiesData
                    if (!it.data!!.activityDTOS.isNullOrEmpty()){
                        _activitiesList.value = SearchUIState(activitiesData = it.data!!.activityDTOS.map { it.toDomainActivity() })
                    }

                    //EventsData
                    if (!it.data!!.eventDTOS.isNullOrEmpty()){
                        _eventsList.value = SearchUIState(eventsData = it.data!!.eventDTOS.map { it.toDomainEvent() })
                    }

                    //GuidesData
                    if (!it.data!!.guideDTOS.isNullOrEmpty()){
                        _guideList.value = SearchUIState(guidesData = it.data!!.guideDTOS.map { it.toDomainGuide() })
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    }