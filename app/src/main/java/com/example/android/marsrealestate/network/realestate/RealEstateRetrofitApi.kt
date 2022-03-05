package com.example.android.marsrealestate.network.realestate

import com.example.android.marsrealestate.network.realestate.model.Property
import retrofit2.Call
import retrofit2.http.GET

interface RealEstateRetrofitApi {

    @GET("realestate")
    fun getProperties(): Call<List<Property>>
}
