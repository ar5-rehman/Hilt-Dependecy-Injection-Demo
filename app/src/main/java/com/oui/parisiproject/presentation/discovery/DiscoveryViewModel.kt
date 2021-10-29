package com.oui.parisiproject.presentation.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oui.parisiproject.common.Resource
import com.oui.parisiproject.data.model.discoveryModel.toDomainCategory
import com.oui.parisiproject.data.model.discoveryModel.toDomainSection
import com.oui.parisiproject.data.model.discoveryModel.toDomainWeather
import com.oui.parisiproject.domain.use_case.discoveryUseCase.DiscoveryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(private val discoveryUseCase: DiscoveryUseCase) :
    ViewModel() {

    private val _categoryList = MutableStateFlow<DiscoveryUIState>(DiscoveryUIState())
    val categoryData: StateFlow<DiscoveryUIState> = _categoryList

    private val _weatherList = MutableStateFlow<DiscoveryUIState>(DiscoveryUIState())
    val weatherData: StateFlow<DiscoveryUIState> = _weatherList

    private val _sectionList = MutableStateFlow<DiscoveryUIState>(DiscoveryUIState())
    val sectionData: StateFlow<DiscoveryUIState> = _sectionList


    fun getDiscovery( position: String) {
        discoveryUseCase(position).onEach {
            when (it) {
                is Resource.Loading -> {
                    _categoryList.value = DiscoveryUIState(isLoading = true)
                    _weatherList.value = DiscoveryUIState(isLoading = true)
                    _sectionList.value = DiscoveryUIState(isLoading = true)
                }
                is Resource.Error -> {
                    _categoryList.value = DiscoveryUIState(error = it.message ?: "")
                    _weatherList.value = DiscoveryUIState(error = it.message ?: "")
                    _sectionList.value = DiscoveryUIState(error = it.message ?: "")
                }
                is Resource.Success -> {

                    //WeatherData
                    if (!it.data!!.weatherDTO.isNullOrEmpty()){
                        _weatherList.value = DiscoveryUIState(weatherData = it.data!!.weatherDTO.map { it.toDomainWeather() })
                    }

                    //CategoryData
                    if (!it.data!!.categoryDTOS.isNullOrEmpty()){
                        _categoryList.value = DiscoveryUIState(categoriesData = it.data!!.categoryDTOS.map { it.toDomainCategory() })
                    }

                    //SectionData
                    if (!it.data!!.sectionDTOS.isNullOrEmpty()){
                        _sectionList.value = DiscoveryUIState(sectionData = it.data!!.sectionDTOS.map { it.toDomainSection() })
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

}