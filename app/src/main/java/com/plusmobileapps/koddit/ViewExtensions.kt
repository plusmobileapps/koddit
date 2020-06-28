package com.plusmobileapps.koddit

import android.view.View

fun View.showOrGone(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}