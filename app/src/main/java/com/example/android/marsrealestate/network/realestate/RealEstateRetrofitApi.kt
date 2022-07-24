package com.example.android.marsrealestate.network.realestate

import com.example.android.marsrealestate.network.realestate.model.MarsProperty
import retrofit2.http.GET

interface RealEstateRetrofitApi {

    @GET("realestate")
    suspend fun getProperties(): List<MarsProperty>
}
