package com.plusmobileapps.sharedcode

import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RedditFeedApi(private val httpClient: HttpClient) {

    suspend fun getDankMemes(): RedditFeedResponse =
        httpClient.get("https://www.reddit.com/r/dankmemes/.json")


}