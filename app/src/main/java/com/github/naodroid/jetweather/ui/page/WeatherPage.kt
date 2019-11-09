package com.github.naodroid.jetweather.ui.page

import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.unaryPlus
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.HorizontalScroller
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.imageFromResource
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.layout.CrossAxisAlignment
import androidx.ui.layout.FlexColumn
import androidx.ui.layout.LayoutSize
import androidx.ui.layout.MainAxisAlignment
import androidx.ui.layout.Padding
import androidx.ui.layout.Row
import androidx.ui.layout.Spacing
import androidx.ui.material.TopAppBar
import androidx.ui.material.surface.Card
import androidx.ui.material.themeColor
import com.github.naodroid.jetweather.R
import com.github.naodroid.jetweather.data.entity.City
import com.github.naodroid.jetweather.data.entity.Forecast
import com.github.naodroid.jetweather.data.entity.Weather5Day
import com.github.naodroid.jetweather.data.state.useWeather
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WeatherPage(city: City) {
    val weather5Day = useWeather(city)

    FlexColumn(
        crossAxisSize = LayoutSize.Expand,
        mainAxisSize = LayoutSize.Expand
    ) {
        inflexible {
            TopAppBar(
                title = { Text(text = city.name) },
                color = +themeColor { primary }
            )
        }
        flexible(flex = 1.0f) {
            if (weather5Day == null) {
                Text(text = "Loading")
            } else {
                WeatherList(weather5Day)
            }
        }
    }
}


//--------------------------------
@Composable
private fun WeatherList(weather5Day: Weather5Day) {
    val grouped = weather5Day.list.groupedByDay()

    VerticalScroller {
        Column(
            crossAxisSize = LayoutSize.Expand,
            mainAxisSize = LayoutSize.Expand
        ) {
            grouped.map {
                ForecastRow(forecasts = it)
            }
        }
    }
}

@Composable
private fun ForecastRow(forecasts: List<Forecast>) {
    val dayText = dayFormatter.format(forecasts[0].date)
    Padding(
        padding = 16.dp
    ) {
        Card(
            elevation = 3.dp,
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(
                Spacing(8.dp),
                crossAxisSize = LayoutSize.Expand,
                mainAxisSize = LayoutSize.Wrap
            ) {
                Text(text = dayText)
                HorizontalScroller {
                    Row {
                        forecasts.map {
                            OneWeatherItem(it)
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun OneWeatherItem(forecast: Forecast) {
    val ctx = +ambient(ContextAmbient)
    val res = ctx.resources
    val time = timeFormatter.format(forecast.date)
    val image = imageFromResource(res, forecast.iconRes)

    Container(
        width = 96.dp,
        height = 80.dp
    ) {
        Column(
            mainAxisAlignment = MainAxisAlignment.Center,
            crossAxisAlignment = CrossAxisAlignment.Center,
            crossAxisSize = LayoutSize.Expand
        ) {
            // I want to use the image from url,
            // but i can't find the way to show image from network.
            Container(
                width = 44.dp,
                height = 44.dp
            ) {
                DrawImage(image = image)
            }
            Text(time)
        }
    }
}

//--------------------------------
private val dayFormatter = SimpleDateFormat("MM/dd", Locale.JAPANESE)
private val timeFormatter = SimpleDateFormat("HH:mm", Locale.JAPANESE)

private fun List<Forecast>.groupedByDay(): Collection<List<Forecast>> {
    return this.groupBy {
        dayFormatter.format(it.date)
    }.values
}

private val Forecast.date: Date
    get() = Date(this.dt * 1000)

private val Forecast.iconRes: Int
    get() {
        val name = this.weather[0].icon
        return when (name) {
            "01d" -> R.drawable.ic01d
            "01n" -> R.drawable.ic01n
            "02d" -> R.drawable.ic02d
            "02n" -> R.drawable.ic02n
            "03d" -> R.drawable.ic03d
            "03n" -> R.drawable.ic03n
            "04d" -> R.drawable.ic04d
            "04n" -> R.drawable.ic04n
            "09d" -> R.drawable.ic09d
            "09n" -> R.drawable.ic09n
            "10d" -> R.drawable.ic10d
            "10n" -> R.drawable.ic10n
            "11d" -> R.drawable.ic11d
            "11n" -> R.drawable.ic11n
            "13d" -> R.drawable.ic13d
            "13n" -> R.drawable.ic13n
            "50d" -> R.drawable.ic50d
            "50n" -> R.drawable.ic50n
            else -> R.drawable.ic01d
        }
    }
