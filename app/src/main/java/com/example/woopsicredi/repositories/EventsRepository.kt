package com.example.woopsicredi.repositories

import com.example.woopsicredi.rest.Retrofit


class EventsRepository {

    fun getAllEvents() = Retrofit.eventsApi.getEvents()

    fun checkinEvents() = Retrofit.eventsApi.checkinEvent()

    fun getEventDetail(id: Int) = Retrofit.eventsApi.getEventDetail(id)
}