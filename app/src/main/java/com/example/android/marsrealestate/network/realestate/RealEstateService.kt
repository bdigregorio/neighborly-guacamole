package com.example.android.marsrealestate.network.realestate

import com.example.android.marsrealestate.network.Network
import com.example.android.marsrealestate.network.realestate.model.MarsProperty

internal object RealEstateService {

    private val api : RealEstateRetrofitApi by lazy {
        Network.retrofit.create(RealEstateRetrofitApi::class.java)
    }

    suspend fun getProperties(propertyType: PropertyTypeApiFilter): List<MarsProperty> = api.getProperties(propertyType.value)

}

enum class PropertyTypeApiFilter(val value: String) {
    RENT("rent"),
    BUY("buy"),
    ALL("all")
}
