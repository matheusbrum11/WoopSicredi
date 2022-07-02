package com.example.woopsicredi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.woopsicredi.model.Events
import com.example.woopsicredi.repositories.EventsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsViewModel(private val repository: EventsRepository) : ViewModel() {
    val eventsList = MutableLiveData<List<Events>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllEvents() {
        val response = repository.getAllEvents()
        response.enqueue(object : Callback<List<Events>> {
            override fun onResponse(call: Call<List<Events>>, response: Response<List<Events>>) {
                eventsList.postValue(response.body()?.toList())
            }

            override fun onFailure(call: Call<List<Events>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}