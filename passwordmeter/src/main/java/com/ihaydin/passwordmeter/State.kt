package com.ihaydin.passwordmeter

import androidx.annotation.ColorInt


data class State(
    val text: String,
    @ColorInt val color: Int,
    val tintSize: Int
)