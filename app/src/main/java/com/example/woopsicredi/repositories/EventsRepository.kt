package com.example.woopsicredi.repositories

import com.example.woopsicredi.api.Retrofit
import com.example.woopsicredi.api.body.CheckInBody


class EventsRepository {

    fun getAllEvents() = Retrofit.eventsApi.getEvents()

    fun checkinEvents(checkInBody: CheckInBody) = Retrofit.eventsApi.checkinEvent(checkInBody)

    fun getEventDetail(id: Int) = Retrofit.eventsApi.getEventDetail(id)
}