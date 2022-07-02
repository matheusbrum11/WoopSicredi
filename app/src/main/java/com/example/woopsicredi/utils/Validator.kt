package com.example.woopsicredi.utils

import android.content.Context
import com.example.woopsicredi.R
import com.google.android.material.textfield.TextInputEditText

object Validator {

    fun validateEmail(context: Context, email: String, view: TextInputEditText): Boolean {
        if (email.isEmpty() || email.isBlank() || !email.contains("@")) {
            view.error = context.getString(R.string.title_fill_the_email)
            return false
        }
        return true
    }

    fun validateName(context: Context, name: String, view: TextInputEditText): Boolean {
        if (name.isEmpty() || name.isBlank()) {
            view.error = context.getString(R.string.title_fill_the_name)
            return false
        }
        return true
    }
}