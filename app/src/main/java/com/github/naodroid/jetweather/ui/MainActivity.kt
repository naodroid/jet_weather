package com.github.naodroid.jetweather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.animation.Crossfade
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Surface
import androidx.ui.material.themeColor
import com.github.naodroid.jetweather.data.state.GlobalState
import com.github.naodroid.jetweather.data.state.Page
import com.github.naodroid.jetweather.ui.page.CityListPage
import com.github.naodroid.jetweather.ui.page.WeatherPage


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colors = MyTheme.materialColor
            ) {
                AppContent()
            }
        }
    }
}


@Composable
private fun AppContent() {
    Crossfade(GlobalState.currentPage) { page ->
        Surface(color = +themeColor { background }) {
            when (page) {
                is Page.CityList -> CityListPage()
                is Page.Weather -> WeatherPage(city = page.city)
            }
        }
    }
}
