package com.example.woopsicredi.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.avatarfirst.avatargenlib.AvatarConstants
import com.avatarfirst.avatargenlib.AvatarGenerator
import com.example.woopsicredi.R
import com.example.woopsicredi.databinding.ActivityDetailEventsBinding
import com.example.woopsicredi.model.Events
import com.example.woopsicredi.repositories.EventsRepository
import com.example.woopsicredi.utils.*
import com.example.woopsicredi.view.activities.CheckInActivity.Companion.CHECKIN_EXTRA_REPLY
import com.example.woopsicredi.viewmodel.EventsViewModel
import com.example.woopsicredi.viewmodel.EventsViewModelFactory
import com.squareup.picasso.Picasso
import java.util.*


class DetailEventsActivity : AppCompatActivity() {

    companion object {
        const val DETAIL_EXTRA_REPLY =
            "com.example.woopsicredi.view.activities.DetailEventsActivity.DETAIL_EXTRA_REPLY"
    }

    private lateinit var binding: ActivityDetailEventsBinding
    private var event: Events? = null
    private var eventId: Int? = null
    lateinit var viewModel: EventsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel =
            ViewModelProvider(this, EventsViewModelFactory(EventsRepository())).get(
                EventsViewModel::class.java
            )
        val uri = intent.data
        if (uri != null)
            if (uri.pathSegments?.size!! > 0) {
                eventId = uri.pathSegments[0].toInt()
            } else {
                errorLoadDetail()
            }

        if (eventId != null) {
            viewModel.getEventDetail(eventId!!)
        } else {
            intent?.extras?.getBundle(DETAIL_EXTRA_REPLY)?.getSerializable(DETAIL_EXTRA_REPLY)
                .let {
                    event = it as Events?
                }
        }
        onOptionsMenuItemSelected();
        setUpUi()
    }

    private fun setUpUi() = binding.apply {

        event?.let {
            topAppBar.title = it.title
            title.text = it.title
            date.text = formartLongDate(Date(it.date!!))
            time.text = formartOnlyTimeDate(Date(it.date))
            if (it.latitude != null && it.longitude != null) {
                address.text = getAddress(it.latitude, it.longitude)
            } else {
                address.text = getAddress()
            }
            description.text = it.description
            Picasso.get()
                .load(it.image)
                .placeholder(
                    AvatarGenerator.avatarImage(
                        this@DetailEventsActivity, 200, AvatarConstants.CIRCLE,
                        it.title.toString()
                    )
                )
                .into(binding.image)
        }
        confirm.setOnClickListener {
            val bundle = Bundle()
            event?.id?.let { it1 ->
                bundle.putInt(
                    CHECKIN_EXTRA_REPLY,
                    it1
                )
            }
            startActivityTransitionAnimation(
                CheckInActivity::class.java,
                "transition_fab",
                confirm,
                CHECKIN_EXTRA_REPLY,
                bundle
            )
        }
    }


    private fun onOptionsMenuItemSelected() = binding.apply {
        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_share -> {
                    shareEvent()
                }
                androidx.appcompat.R.id.home -> {
                    onBackPressed()
                }
            }
            true
        }
        topAppBar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.events.observe(this, androidx.lifecycle.Observer {
            event = it
            if (it != null) {
                setUpUi()
            } else {
                errorLoadDetail()
            }
        })

        viewModel.errorMessageGetAllEvents.observe(this, androidx.lifecycle.Observer {
            toast(it)
            finish()
        })
    }

    private fun errorLoadDetail() {
        binding.layoutEmpty.visibility = View.VISIBLE
        binding.topAppBar.title = " "
        binding.topAppBar.menu.clear()
    }

    private fun shareEvent() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                "${getString(R.string.message_come_join_the_event)}https://www.woopsicrediexample.com/${event?.id}"
            )
            startActivity(Intent.createChooser(shareIntent, getString(R.string.send_link)))
        } catch (e: Exception) {
        }
    }
}