package com.github.naodroid.jetweather.data.state

import android.content.Context
import androidx.compose.ambient
import androidx.compose.modelFor
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.ContextAmbient
import androidx.ui.core.CoroutineContextAmbient
import com.github.naodroid.jetweather.R
import com.github.naodroid.jetweather.data.entity.City
import com.github.naodroid.jetweather.data.entity.Weather5Day
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.net.URL

fun useWeather(city: City): Weather5Day? {
    val state = +state<Weather5Day?> { null }
    val context = +ambient(ContextAmbient)
    val coroutineCtx = +ambient(CoroutineContextAmbient)
    val scope = CoroutineScope(coroutineCtx)

    +modelFor(city.id) {
        scope.launch {
            val apiKey = context.getString(R.string.api_key)
            val result = accessNetwork(apiKey, city).await()
            state.value = result

            //if you don't want to use network, please use this line.
            //then this app will get data from local-resource.
            //state.value = accessResource(context)
        }
    }
    return state.value
}

//use local json, for ui-debugging
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
fun accessNetwork(
    apiKey: String,
    city: City
): Deferred<Weather5Day?> {
    val deferred = CompletableDeferred<Weather5Day?>()
    val urlStr =
        "https://api.openweathermap.org/data/2.5/forecast?id=${city.id}&appid=${apiKey}&units=metric"

    GlobalScope.launch {
        try {
            val url = URL(urlStr)
            url.openStream().use {
                val jsonText = it.bufferedReader().readText()
                val gson = Gson()
                val dataType = object : TypeToken<Weather5Day>() {}.type
                val result = gson.fromJson<Weather5Day>(
                    jsonText, dataType
                )
                deferred.complete(result)
            }
        } catch (e: Exception) {
            //TODO: Error Process
            deferred.complete(null)
        }
    }
    return deferred
}


