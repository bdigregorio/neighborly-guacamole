package com.example.android.marsrealestate.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object Network {
    private const val baseUrl = "https://mars.udacity.com/"

    val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(baseUrl)
        .build()
}