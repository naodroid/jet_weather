package com.github.naodroid.jetweather.ui

import androidx.ui.graphics.Color
import androidx.ui.material.MaterialColors


object MyTheme {
    // from https://material.io/resources/color/#!/?view.left=0&view.right=0&primary.color=546E7A
    // Be careful to use same value as colors.xml
    // I considered that use 'Context.getColor', 
    // but it's a boring to convert xml-color-value to Compose-Color.
    val materialColor = MaterialColors(
        primary = Color(0xFF546e7a),
        primaryVariant = Color(0xFF29434e),
        secondary = Color(0xFF819ca9),
        secondaryVariant = Color(0xFF29434e)
    )
}