package com.example.mindsdbkb.api

import com.example.mindsdbkb.model.AgentResponse
import com.example.mindsdbkb.model.BackendResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiCall {

    @GET("query")
    @Headers("Content-Type: application/json")
    suspend fun apiCall(
        @Query("q") request: String,
        @Query("min_relevance") minRelevance: Float,
        @QueryMap(encoded = true) metadata: Map<String, String>
    ): BackendResponse

    @GET("agent")
    @Headers("Content-Type: application/json")
    suspend fun agentCall(
        @Query("query") request: String
    ) : AgentResponse
}
