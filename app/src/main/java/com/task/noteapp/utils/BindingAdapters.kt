package com.task.noteapp.utils

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("visibleGone")
fun visibleGone(view: View, isVisible: Boolean?) {
    isVisible?.let {
        if (it)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }
}

@BindingAdapter("visibleInvisible")
fun visibleInvisible(view: View, isVisible: Boolean?) {
    isVisible?.let {
        if (it)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.INVISIBLE
    }
}

@BindingAdapter("setFormattedDate")
fun setFormattedDate(textView: MaterialTextView, date: Date?) {
    date?.let {
        val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        textView.text = formatter.format(date)
    }
}