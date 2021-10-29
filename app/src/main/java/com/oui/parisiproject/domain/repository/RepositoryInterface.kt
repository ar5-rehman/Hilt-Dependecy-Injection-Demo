package com.oui.parisiproject.domain.repository

import com.oui.parisiproject.data.model.discoveryModel.DiscoveryDTO
import com.oui.parisiproject.data.model.searchModel.SearchDTO

interface RepositoryInterface {
    suspend fun discovery(position: String): DiscoveryDTO

    suspend fun search(searchKeyWord: String): SearchDTO
}