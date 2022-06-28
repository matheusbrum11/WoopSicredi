package com.example.woopsicredi.rest

import com.example.woopsicredi.model.Events
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoints {

    interface EventsService {
        @GET("api/events")
        fun getEvents(): Call<List<Events>>

        @GET("api/events/{id}")
        fun getEventDetail(@Path ("id")id : Int): Call<Events>

        @POST("api/checkin")
        fun checkinEvent()
    }

}