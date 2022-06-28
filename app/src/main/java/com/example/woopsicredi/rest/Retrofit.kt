package com.example.woopsicredi.rest

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import com.example.woopsicredi.rest.ApiEndpoints.*

object Retrofit {

    private const val BASE_URL = "https://5f5a8f24d44d640016169133.mockapi.io/"

    private fun getRetrofit(baseURL: String): Retrofit {
        val gsonBuilder = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()
    }

    val eventsApi: EventsService = getRetrofit(BASE_URL).create(EventsService::class.java)
}