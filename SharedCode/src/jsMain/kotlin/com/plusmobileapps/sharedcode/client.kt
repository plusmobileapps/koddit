package com.plusmobileapps.sharedcode

import com.plusmobileapps.sharedcode.db.MyDatabase
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json

actual val client: HttpClient = HttpClient(Js) {
    install(JsonFeature) {
        serializer = KotlinxSerializer(Json.nonstrict)
    }
}

actual fun createDb(): MyDatabase {
    TODO("Not yet implemented")
}