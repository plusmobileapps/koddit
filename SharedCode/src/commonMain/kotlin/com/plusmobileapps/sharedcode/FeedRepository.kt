package com.plusmobileapps.sharedcode

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

expect val client: HttpClient

class FeedRepository {

    private val job = Job()
    private val scope = CoroutineScope(job + ApplicationDispatcher)

    fun getDankMemes(onSuccess: (RedditFeedResponse) -> Unit, onError: (Any) -> Unit) {
        scope.launch {
            try {
                val response = client.get<RedditFeedResponse>("https://www.reddit.com/r/dankmemes/.json")
                onSuccess(response)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

}
