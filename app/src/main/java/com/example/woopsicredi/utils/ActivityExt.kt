package com.example.woopsicredi.utils

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

fun AppCompatActivity.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

@SuppressLint("SimpleDateFormat")
fun AppCompatActivity.formartLongDate(date: Date) = SimpleDateFormat("E.dd.EU VOU z").format(date.time).toString()