package com.example.android.marsrealestate.network.realestate

import com.example.android.marsrealestate.network.Network
import com.example.android.marsrealestate.network.realestate.model.Property
import retrofit2.Call

internal object RealEstateService {

    private val api : RealEstateRetrofitApi by lazy {
        Network.retrofit.create(RealEstateRetrofitApi::class.java)
    }

    fun getProperties(): Call<List<Property>> = api.getProperties()
}