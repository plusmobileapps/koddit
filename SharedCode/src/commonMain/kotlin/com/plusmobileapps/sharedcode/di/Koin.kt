package com.plusmobileapps.sharedcode.di

import com.plusmobileapps.sharedcode.ApplicationDispatcher
import com.plusmobileapps.sharedcode.FeedRepository
import com.plusmobileapps.sharedcode.client
import com.plusmobileapps.sharedcode.createDb
import com.plusmobileapps.sharedcode.db.MyDatabase
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule)
}

// called by iOS etc
fun initKoin() = initKoin{
    modules(commonModule)
}

val commonModule = module {
    single<MyDatabase> { createDb() }
    single<CoroutineDispatcher> { ApplicationDispatcher }
    single<HttpClient> { client }
    single { FeedRepository() }
}