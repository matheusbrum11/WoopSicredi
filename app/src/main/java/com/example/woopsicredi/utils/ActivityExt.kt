package com.example.woopsicredi.utils

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.woopsicredi.R
import java.text.SimpleDateFormat
import java.util.*


fun AppCompatActivity.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

@SuppressLint("SimpleDateFormat")
fun AppCompatActivity.formartLongDate(date: Date) =
    SimpleDateFormat("E dd LLLL").format(date.time).toString()

fun AppCompatActivity.getAddress(latitude: Double = 0.0, logitude: Double = 0.0): String {
    if (latitude != 0.0 && logitude != 0.0) {
        val geocoder = Geocoder(this)
        val address = geocoder.getFromLocation(latitude, logitude, 1)
        return address?.get(0)?.getAddressLine(0) ?: getString(R.string.no_address)
    }
    return getString(R.string.no_address)
}

@SuppressLint("SimpleDateFormat")
fun AppCompatActivity.formartOnlyTimeDate(date: Date) =
    SimpleDateFormat("HH:mm").format(date.time).toString()


fun AppCompatActivity.startActivityTransitionAnimation(
    clazz: Class<*>,
    transitionName: String = "",
    itemView: View,
    name: String = "",
    args: Bundle = Bundle()
) {
    val intent = Intent(this, clazz).apply {
        if (!(name.isNotEmpty() && args.isEmpty)) {
            putExtra(name, args)
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val options = ActivityOptions
            .makeSceneTransitionAnimation(this, itemView, transitionName)
        startActivity(intent, options.toBundle())
    } else {
        startActivity(intent)
    }

}

fun AppCompatActivity.startActivity(clazz: Class<*>, name: String = "", args: Bundle = Bundle()) {
    val intent = Intent(this, clazz).apply {
        if (!(name.isNotEmpty() && args.isEmpty)) {
            putExtra(name, args)
        }
    }
    startActivity(intent)
}

fun AppCompatActivity.setToolbar(toolbar: androidx.appcompat.widget.Toolbar) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setHomeButtonEnabled(true)
}