package com.example.woopsicredi.api

import com.example.woopsicredi.api.body.CheckInBody
import com.example.woopsicredi.model.Events
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiEndpoints {

    interface EventsService {
        @GET("api/events")
        fun getEvents(): Call<List<Events>>

        @GET("api/events/{id}")
        fun getEventDetail(@Path("id") id: Int): Call<Events>

        @POST("api/checkin")
        fun checkinEvent(@Body checkInBody: CheckInBody): Call<ResponseBody>
    }

}