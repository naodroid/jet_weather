package com.github.naodroid.jetweather.data.state

import androidx.compose.Model
import com.github.naodroid.jetweather.data.entity.City


sealed class Page {
    object CityList : Page()
    data class Weather(val city: City) : Page()
}

@Model
object GlobalState {
    var currentPage: Page = Page.CityList
}