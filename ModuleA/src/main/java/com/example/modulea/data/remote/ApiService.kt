package com.example.modulea.data.remote

import com.example.modulea.domain.model.UniversityItem
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    suspend fun getUniversities(
        @Query("country") country:String
    ): List<UniversityItem>
}