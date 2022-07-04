package com.example.woopsicredi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.woopsicredi.api.body.CheckInBody
import com.example.woopsicredi.model.Events
import com.example.woopsicredi.repositories.EventsRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class EventsViewModel(private val repository: EventsRepository) : ViewModel() {

    val eventsList = MutableLiveData<List<Events>>()
    val events = MutableLiveData<Events>()
    val errorMessageGetAllEvents = MutableLiveData<String>()
    val errorMessageGetEventDetail = MutableLiveData<String>()
    val statusCheckIn = MutableLiveData<Boolean>()

    fun getAllEvents() {
        val request = repository.getAllEvents()
        request.enqueue(object : Callback<List<Events>> {
            override fun onResponse(call: Call<List<Events>>, response: Response<List<Events>>) {
                eventsList.postValue(response.body()?.toList())
            }

            override fun onFailure(call: Call<List<Events>>, t: Throwable) {
                errorMessageGetAllEvents.postValue(t.message)
            }
        })
    }

    fun checkInEvent(checkInBody: CheckInBody) {
        val request = repository.checkinEvents(checkInBody)
        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == HttpURLConnection.HTTP_CREATED) {
                    statusCheckIn.postValue(true)
                } else {
                    statusCheckIn.postValue(false)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                statusCheckIn.postValue(false)
            }
        })
    }

    fun getEventDetail(id: Int) {
        val request = repository.getEventDetail(id)
        request.enqueue(object : Callback<Events> {
            override fun onResponse(call: Call<Events>, response: Response<Events>) {
                events.postValue(response.body())
            }

            override fun onFailure(call: Call<Events>, t: Throwable) {
                errorMessageGetEventDetail.postValue(t.message)
            }
        })
    }
}