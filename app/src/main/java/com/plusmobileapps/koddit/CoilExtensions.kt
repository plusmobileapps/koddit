package com.plusmobileapps.koddit

import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

val Context.gifImageLoader: ImageLoader
    get() = ImageLoader.Builder(this)
        .componentRegistry {
            val decoder = if (Build.VERSION.SDK_INT >= 28) ImageDecoderDecoder() else GifDecoder()
            add(decoder)
        }
        .build()