package com.github.naodroid.jetweather.data.entity


/// open weather city data
/// sample json
/// [{"country": "UA", "id": 707860, "coord": {"lat": 44.549999, "lon": 34.283333}, "name": "Hurzuf"}]

data class City(
    val name: String
)

data class Coord(
    val lat: Double,
    val lon: Double
)
