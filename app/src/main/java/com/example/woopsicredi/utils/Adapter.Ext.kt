package com.example.woopsicredi.utils

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun formartLongDate(date: Date) = SimpleDateFormat("E dd LLLL").format(date.time).toString()