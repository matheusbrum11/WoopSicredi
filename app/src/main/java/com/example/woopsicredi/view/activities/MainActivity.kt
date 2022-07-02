package com.example.woopsicredi.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.woopsicredi.databinding.ActivityMainBinding
import com.example.woopsicredi.model.Events
import com.example.woopsicredi.repositories.EventsRepository
import com.example.woopsicredi.utils.startActivity
import com.example.woopsicredi.utils.toast
import com.example.woopsicredi.view.activities.DetailEventsActivity.Companion.EXTRA_REPLY
import com.example.woopsicredi.view.adapters.EventAdapter
import com.example.woopsicredi.viewmodel.EventsViewModel
import com.example.woopsicredi.viewmodel.EventsViewModelFactory

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: EventsViewModel
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel =
            ViewModelProvider(this, EventsViewModelFactory(EventsRepository())).get(
                EventsViewModel::class.java
            )

        initRecyclerView()
        binding.shimmerViewContainer.startShimmer()
        viewModel.eventsList.observe(this, Observer {
            binding.listEvents.visibility = View.VISIBLE
            eventAdapter.setEventsList(it)
            binding.shimmerViewContainer.stopShimmer()
        })

        viewModel.errorMessage.observe(this, Observer {
            binding.listEvents.visibility = View.VISIBLE
            binding.shimmerViewContainer.stopShimmer()
            toast(it)
        })

    }

    private fun initRecyclerView() {
        eventAdapter = EventAdapter { event ->
            openDetails(event)
        }

        binding.listEvents.apply {
            adapter = eventAdapter
        }
    }

    private fun openDetails(event: Events) {
        var bundle = Bundle()
        bundle.putSerializable(EXTRA_REPLY, event)
        startActivity(DetailEventsActivity::class.java, EXTRA_REPLY, bundle)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllEvents()
    }

    override fun onStop() {
        super.onStop()
        binding.shimmerViewContainer.stopShimmer()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllEvents()
    }
}