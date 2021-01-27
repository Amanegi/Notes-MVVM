package com.amannegi.notes.utils

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import com.amannegi.notes.R

fun ImageView.loadImageViaCoil(uri: String) {
    // imageview extension function to load image from coil
    this.load(uri) {
        crossfade(true)
        crossfade(500)
        placeholder(R.drawable.ic_person)
        transformations(CircleCropTransformation())
    }

}
