package com.example.woopsicredi.utils

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

fun AppCompatActivity.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

@SuppressLint("SimpleDateFormat")
fun AppCompatActivity.formartLongDate(date: Date) =
    SimpleDateFormat("E.dd.EU VOU z").format(date.time).toString()

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