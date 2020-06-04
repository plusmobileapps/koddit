package com.plusmobileapps.sharedcode.di

import com.plusmobileapps.sharedcode.ApplicationDispatcher
import com.plusmobileapps.sharedcode.FeedRepository
import com.plusmobileapps.sharedcode.client
import com.plusmobileapps.sharedcode.createDb
import com.plusmobileapps.sharedcode.db.MyDatabase
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineDispatcher
import org.kodein.di.*

val commonModule = DI.lazy {
    bind<HttpClient>() with singleton { client }
    bind<CoroutineDispatcher>() with singleton { ApplicationDispatcher }
    bind<MyDatabase>() with singleton { createDb() }
    bind<FeedRepository>() with singleton { FeedRepository(instance(), instance(), instance()) }
}

object Di {
    fun getFeedRepository(): FeedRepository {
        return commonModule.direct.instance<FeedRepository>()
    }
}