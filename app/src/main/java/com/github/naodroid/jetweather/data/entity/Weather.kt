package com.github.naodroid.jetweather.data.entity

// for open weather
data class Weather5Day(
    val list: List<Forecast> = emptyList()
)

data class Forecast(
    val dt: Long,
    val main: Detail,
    val weather: List<Weather> //this is array, but containts 1 element.
)

data class Detail(
    val temp: Double,
    val tempMin: Double?,
    val tempMax: Double?,
    val pressure: Double?,
    val seaLevel: Double?,
    val grndLevel: Double?,
    val humidity: Double?,
    val tempKf: Double?
)

data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double,
    val degree: Double
)