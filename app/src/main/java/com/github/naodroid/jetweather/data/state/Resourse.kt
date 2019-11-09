package com.github.naodroid.jetweather.data.state

import android.content.Context
import kotlinx.coroutines.coroutineScope


suspend fun readAsset(context: Context, assetName: String): String? = coroutineScope {
    context.assets.open(assetName).use {
        it.bufferedReader().readText()
    }
}