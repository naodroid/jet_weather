package com.github.naodroid.jetweather.data.state

import android.content.Context
import androidx.compose.ambient
import androidx.compose.model
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.ContextAmbient
import androidx.ui.core.CoroutineContextAmbient
import com.github.naodroid.jetweather.data.entity.City
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

fun useCityList(): List<City> {
    val context = +ambient(ContextAmbient)
    val state = +state { emptyList<City>() }
    val coroutineCtx = +ambient(CoroutineContextAmbient)
    val scope = CoroutineScope(coroutineCtx)

    +model {
        scope.launch {
            val list = scope.async {
                readCityList(context)
            }
            state.value = list.await()
        }
    }
    return state.value
}


private suspend fun readCityList(context: Context): List<City> = coroutineScope {
    //I want to use kotlin-serializer, but "Backend code generation error" has occurred at compile time.
    //Same error happened when I use "kotshi".
    //Probably, this is the issue of Android-Studio-Preview.
    //So I switched to Gson.
    val jsonText = readAsset(context, "city_list.json")
    val gson = Gson()
    val listType = object : TypeToken<List<City>>() {}.type
    val result = gson.fromJson<List<City>>(jsonText, listType)
    result ?: emptyList<City>()
}
