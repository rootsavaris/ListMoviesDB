package com.example.moviedb.utils

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.moviedb.R

fun getDivider(context: Context) : DividerItemDecoration{
    return DividerItemDecoration(context, DividerItemDecoration.VERTICAL).also {
        it.setDrawable(ContextCompat.getDrawable(context, R.drawable.shape_divider)!!)
    }
}

