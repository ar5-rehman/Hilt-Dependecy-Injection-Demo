package com.oui.parisiproject.data.repository

import com.oui.parisiproject.data.model.discoveryModel.DiscoveryDTO
import com.oui.parisiproject.data.model.searchModel.SearchDTO
import com.oui.parisiproject.data.remote.ApiService
import com.oui.parisiproject.domain.repository.RepositoryInterface

class RepositoryImpl(private var apiService: ApiService): RepositoryInterface {
    override suspend fun discovery(
        position: String,
    ): DiscoveryDTO {
        return apiService.getDiscoveryData(position)
    }

    override suspend fun search(
        searchKeyWord: String
    ): SearchDTO {
        return apiService.getSearchResult(searchKeyWord)
    }
}