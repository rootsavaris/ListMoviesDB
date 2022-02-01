package com.example.moviedb.presentation.list

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.moviedb.utils.BASE_IMAGE_URL

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    view.load(BASE_IMAGE_URL + imageUrl) {
        crossfade(durationMillis = 2000)
        transformations(
            RoundedCornersTransformation(0f)
        )
    }
}