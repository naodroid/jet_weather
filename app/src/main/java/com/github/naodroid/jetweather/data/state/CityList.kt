package com.github.naodroid.jetweather.data.state

import android.content.Context
import androidx.compose.ambient
import androidx.compose.model
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.ContextAmbient
import com.github.naodroid.jetweather.data.entity.City
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun useCityList(): List<City> {
    val context = +ambient(ContextAmbient)
    val state = +state { emptyList<City>() }

    +model {
        //how to cancel this job?
        GlobalScope.launch {
            val list = readCityList(context)
            GlobalScope.launch(Dispatchers.Main) {
                state.value = list
            }
        }
    }
    return state.value
}


private fun readCityList(context: Context): List<City> {
    //I want to use kotlin-serializer, but "Backend code generation error" has occurred at compile time.
    //Same error happened when I use "kotshi".
    //Probably, this is the issue of Android-Studio-Preview.
    //So I switched to Gson.

    return context.resources.assets.open("city_list.json").use {
        val jsonText = it.bufferedReader().readText()
        val gson = Gson()
        val listType = object : TypeToken<List<City>>() {}.type
        gson.fromJson<List<City>>(jsonText, listType)
    } ?: emptyList<City>()
}
