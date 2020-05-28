package com.plusmobileapps.sharedcode

import com.squareup.sqldelight.android.AndroidSqliteDriver
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json


actual fun platformName(): String {
    return "Android"
}

actual val client: HttpClient = HttpClient(Android) {
    install(JsonFeature) {
        serializer = KotlinxSerializer(Json.nonstrict)
    }
}

//val androidDriver = AndroidSqliteDriver()
