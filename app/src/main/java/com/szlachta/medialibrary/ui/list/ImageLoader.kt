package com.szlachta.medialibrary.ui.list

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(url: String, into: ImageView)
}