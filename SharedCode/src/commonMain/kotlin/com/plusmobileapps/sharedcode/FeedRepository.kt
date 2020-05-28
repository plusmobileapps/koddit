package com.plusmobileapps.sharedcode

import com.plusmobileapps.sharedcode.db.MyDatabase
import com.plusmobileapps.sharedcode.db.data.Posts
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

expect val client: HttpClient

expect fun createDb(): MyDatabase

class FeedRepository {

    private val job = Job()
    private val scope = CoroutineScope(job + ApplicationDispatcher)
    private val db: MyDatabase = createDb()

    fun getDankMemes(onSuccess: (List<Posts>) -> Unit, onError: (Any) -> Unit) {
        val cache = db.postsQueries.selectAll().executeAsList()
        if (cache.isNotEmpty()) {
            onSuccess(cache)
            return
        }
        scope.launch {
            try {
                val response =
                    client.get<RedditFeedResponse>("https://www.reddit.com/r/dankmemes/.json")
                val posts = response.data.children.map { post ->
                    val id = post.data.url
                    val title = post.data.title
                    db.postsQueries.insertItem(id, title)
                    Posts.Impl(id, title)
                }
                onSuccess(posts)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

}
