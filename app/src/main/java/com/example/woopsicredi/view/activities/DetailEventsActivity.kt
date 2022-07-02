package com.example.woopsicredi.view.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.woopsicredi.R
import com.example.woopsicredi.databinding.ActivityDetailEventsBinding
import com.example.woopsicredi.model.Events
import com.example.woopsicredi.utils.toast
import com.example.woopsicredi.viewmodel.EventsViewModel

class DetailEventsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_REPLY =
            "com.example.woopsicredi.view.activities.DetailEventsActivity.EXTRA_REPLY"
    }

    private lateinit var binding: ActivityDetailEventsBinding
    lateinit var viewModel: EventsViewModel
    private var event: Events? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onOptionsMenuItemSelected();
        intent?.extras?.getBundle(EXTRA_REPLY)?.getSerializable(EXTRA_REPLY)
            .let {
                event = it as Events?
            }
        event?.let {
            binding.topAppBar.title = it.title
        }
    }


    private fun onOptionsMenuItemSelected() {
        binding.topAppBar.setOnMenuItemClickListener {
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
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                shareEvent();
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareEvent() {
        toast("Hello")
    }
}