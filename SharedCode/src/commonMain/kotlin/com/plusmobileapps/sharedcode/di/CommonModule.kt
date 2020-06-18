package com.plusmobileapps.sharedcode.di

import com.plusmobileapps.sharedcode.*
import com.plusmobileapps.sharedcode.ApplicationDispatcher
import com.plusmobileapps.sharedcode.db.MyDatabase
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineDispatcher
import org.kodein.di.*

val commonModule = DI.lazy {
    bind<HttpClient>() with singleton { client }
    bind<CoroutineDispatcher>() with singleton { ApplicationDispatcher }
    bind<MyDatabase>() with singleton { createDb() }
    bind<RedditFeedApi>() with singleton { RedditFeedApi(instance()) }
    bind<FeedRepository>() with singleton { FeedRepository(instance(), instance(), instance()) }
}

object Di {
    fun getRedditFeedApi(): RedditFeedApi {
        return commonModule.direct.instance()
    }

    fun getFeedRepository(): FeedRepository {
        return commonModule.direct.instance<FeedRepository>()
    }
}