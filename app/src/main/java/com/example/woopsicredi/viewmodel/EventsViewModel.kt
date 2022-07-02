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
    val errorMessage = MutableLiveData<String>()
    val statusCheckIn = MutableLiveData<Boolean>()

    fun getAllEvents() {
        val request = repository.getAllEvents()
        request.enqueue(object : Callback<List<Events>> {
            override fun onResponse(call: Call<List<Events>>, response: Response<List<Events>>) {
                eventsList.postValue(response.body()?.toList())
            }

            override fun onFailure(call: Call<List<Events>>, t: Throwable) {
                errorMessage.postValue(t.message)
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
}