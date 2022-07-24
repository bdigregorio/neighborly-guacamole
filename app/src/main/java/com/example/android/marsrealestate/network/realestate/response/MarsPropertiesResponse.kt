package com.example.android.marsrealestate.network.realestate.response

import com.example.android.marsrealestate.network.realestate.model.MarsProperty

sealed class MarsPropertiesResponse {
    object Loading : MarsPropertiesResponse()
    class Success(val marsProperties: List<MarsProperty>) : MarsPropertiesResponse()
    class Error(val exception: Exception) : MarsPropertiesResponse()
}