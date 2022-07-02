package com.example.woopsicredi.view.activities

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionManager
import com.example.woopsicredi.R
import com.example.woopsicredi.api.body.CheckInBody
import com.example.woopsicredi.databinding.ActivityCheckInBinding
import com.example.woopsicredi.repositories.EventsRepository
import com.example.woopsicredi.utils.Validator
import com.example.woopsicredi.utils.toast
import com.example.woopsicredi.viewmodel.EventsViewModel
import com.example.woopsicredi.viewmodel.EventsViewModelFactory
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform

class CheckInActivity : AppCompatActivity() {

    companion object {
        const val CHECKIN_EXTRA_REPLY =
            "com.example.woopsicredi.view.activities.DetailEventsActivity.CHECKIN_EXTRA_REPLY"
    }

    private lateinit var binding: ActivityCheckInBinding
    private var eventId: Int? = null
    lateinit var viewModel: EventsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel =
            ViewModelProvider(this, EventsViewModelFactory(EventsRepository())).get(
                EventsViewModel::class.java
            )
        onOptionsMenuItemSelected()
        setUpUi()
        intent?.extras?.getBundle(CHECKIN_EXTRA_REPLY)?.getInt(
            CHECKIN_EXTRA_REPLY
        )
            .let {
                eventId = it
            }

    }

    private fun onOptionsMenuItemSelected() = binding.apply {
        topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpUi() = binding.apply {
        confirm.setOnClickListener {
            val validateEmail =
                Validator.validateEmail(this@CheckInActivity, etEmail.text.toString(), etName)
            val validateName =
                Validator.validateName(this@CheckInActivity, etName.text.toString(), etEmail)

            if (validateName && validateEmail) {
                eventId?.let {
                    checkIn(
                        CheckInBody(
                            it,
                            etName.text.toString(),
                            etEmail.text.toString()
                        )
                    )
                }

            }
        }
    }

    private fun checkIn(checkInBody: CheckInBody) {
        showLoading()
        viewModel.checkInEvent(checkInBody)
    }

    override fun onStart() {
        super.onStart()
        viewModel.statusCheckIn.observe(this, Observer {
            if (it) {
                hideLoading()
                toast(getString(R.string.checkin_success))
                onBackPressed()
            } else {
                hideLoading()
                toast(getString(R.string.checkin_error))
                onBackPressed()
            }
        })
    }

    private fun hideLoading() {
        TransitionManager.beginDelayedTransition(
            binding.sheet,
            createMaterialContainerTransform(false)
        )
        binding.confirm.isInvisible = false
        binding.sheet.isInvisible = true
    }

    private fun showLoading() {
        TransitionManager.beginDelayedTransition(
            binding.sheet,
            createMaterialContainerTransform(true)
        )
        binding.confirm.isInvisible = true
        binding.sheet.isInvisible = false
    }

    private fun createMaterialContainerTransform(showBtn: Boolean) =
        MaterialContainerTransform().apply {
            duration = 1000L
            startView = if (showBtn) binding.confirm else binding.sheet
            endView = if (showBtn) binding.sheet else binding.confirm
            addTarget(endView!!)
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(Color.TRANSPARENT)
            setPathMotion(MaterialArcMotion())
        }

}