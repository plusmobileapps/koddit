package com.plusmobileapps.sharedcode

import com.plusmobileapps.sharedcode.db.MyDatabase
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
    return DbHelper("path").getDatabase()
}

class DbHelper(filePath: String) {
    private val driver: JdbcSqliteDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY + filePath)
    private var database: MyDatabase? = null
    fun getDatabase(): MyDatabase {
        if (database == null) {
            init()
        }
        return database!!
    }

    private fun init() {
        val currentVer = version
        if (currentVer == 0) {
            MyDatabase.Schema.create(driver)
            version = 1
            print("init: created tables, setVersion to 1")
        } else {
//            val schemaVer: Int = MyDatabase.Companion.Schema.getVersion()
//            if (schemaVer > currentVer) {
//                Database.Companion.getSchema().migrate(driver, currentVer, schemaVer)
//                version = schemaVer
//                Timber.d("init: migrated from %d to %d", currentVer, schemaVer)
//            } else {
//                Timber.d("init")
//            }
        }
        database = MyDatabase.invoke(driver)
    }

    private var version: Int
        get() {
            val sqlCursor = driver.executeQuery(null, "PRAGMA user_version;", 0, null)
            return sqlCursor.getLong(0)?.toInt() ?: 0
        }
        private set(version) {
            driver.execute(null, String.format("PRAGMA user_version = %d;", version), 0, null)
        }

}