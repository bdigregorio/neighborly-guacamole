package com.example.android.marsrealestate.network.realestate

import com.example.android.marsrealestate.network.realestate.model.MarsProperty
import retrofit2.http.GET
import retrofit2.http.Query

interface RealEstateRetrofitApi {

    @GET("realestate")
    suspend fun getProperties(@Query("filter") propertyType: String): List<MarsProperty>
}
