package com.plusmobileapps.sharedcode

import com.plusmobileapps.sharedcode.db.MyDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json

actual val client: HttpClient = HttpClient(Apache) {
    install(JsonFeature.Feature) {
        serializer = KotlinxSerializer(Json.nonstrict)
    }
}

actual fun createDb(): MyDatabase {
    val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    MyDatabase.Schema.create(driver)
    return MyDatabase.invoke(driver)
}