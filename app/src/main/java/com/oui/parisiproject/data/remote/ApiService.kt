package com.oui.parisiproject.data.remote

import com.oui.parisiproject.data.model.discoveryModel.DiscoveryDTO
import com.oui.parisiproject.data.model.searchModel.SearchDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("marketing/home/")
    suspend fun getDiscoveryData(
        @Query("position") position: String,
    ): DiscoveryDTO

    @GET("activities/search/")
    suspend fun getSearchResult(
        @Query("term") searchKeyWord: String
    ): SearchDTO

    /*

    @GET("activities/")
    suspend fun getActivities(
        @Query("api_key") api_key: String,
        @Header("Request-Time") time: String,
    ): Response<GetActivitiesPojo>

    @GET("activities/")
    suspend fun getActivitiesNextPage(
        @Query(value = "cursor", encoded = true) cursor: String?,
        @Query("api_key") api_key: String?,
        @Header("Request-Time") time: String?,
    ): Response<GetActivitiesPojo>*/
}