package com.github.naodroid.jetweather.data.state

import android.content.Context
import androidx.compose.ambient
import androidx.compose.modelFor
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.ContextAmbient
import androidx.ui.core.CoroutineContextAmbient
import com.github.naodroid.jetweather.data.entity.City
import com.github.naodroid.jetweather.data.entity.Weather5Day
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

fun useWeather(city: City): Weather5Day? {
    val state = +state<Weather5Day?> { null }
    val context = +ambient(ContextAmbient)
    val coroutineCtx = +ambient(CoroutineContextAmbient)
    val scope = CoroutineScope(coroutineCtx)

    +modelFor(city.id) {
        scope.launch {
            val result = scope.async {
                accessResource(context)
            }.await()
            state.value = result
        }

    }
    //use local json, for ui-debugging
    //access to 
    return state.value
}

suspend fun accessResource(context: Context): Weather5Day =
    coroutineScope {
        val jsonText = readAsset(context, "5day_sample.json")
        val gson = Gson()
        val dataType = object : TypeToken<Weather5Day>() {}.type
        gson.fromJson<Weather5Day>(
            jsonText, dataType
        )
    }

// I want to use ktor, but background internal error happened at compile.
//suspend fun accessNetwork(client: HttpClient, apiKey: String, city: City): Weather5Day =
//    coroutineScope {
//        val url =
//            "https://api.openweathermap.org/data/2.5/weather?id=${city.id}&appid=${apiKey}&units=metric"
//        client.get<Weather5Day>(url)
//    }

