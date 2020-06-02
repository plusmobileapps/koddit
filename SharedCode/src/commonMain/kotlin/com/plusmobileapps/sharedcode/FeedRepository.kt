package com.plusmobileapps.sharedcode

import com.plusmobileapps.sharedcode.db.MyDatabase
import com.plusmobileapps.sharedcode.db.data.Post
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

expect val client: HttpClient

expect fun createDb(): MyDatabase

class FeedRepository : KoinComponent {

    private val db: MyDatabase by inject()
    private val client: HttpClient by inject()
    private val dispatcher: CoroutineDispatcher by inject()

    private val job = Job()
    private val scope = CoroutineScope(job + dispatcher)

    fun getDankMemes(onSuccess: (List<Post>) -> Unit, onError: (Any) -> Unit) {
        val cache = db.postQueries.selectAll().executeAsList()
        if (cache.isNotEmpty()) {
            onSuccess(cache)
            return
        }
        scope.launch {
            try {
                val response =
                    client.get<RedditFeedResponse>("https://www.reddit.com/r/dankmemes/.json")
                val posts = response.data.children.map { redditPost ->
                    val post = redditPost.toPost()
                    db.postQueries.insertItem(
                        id = post.id,
                        post_hint = post.post_hint,
                        url = post.url,
                        thumbnail = post.thumbnail,
                        subreddit_name_prefixed = post.subreddit_name_prefixed,
                        permalink = post.permalink,
                        num_comments = post.num_comments,
                        kind = post.kind,
                        is_video = post.is_video,
                        ups = post.ups,
                        downs = post.downs,
                        author_fullname = post.author_fullname,
                        author = post.author,
                        subreddit = post.subreddit,
                        title = post.title
                    )
                    post
                }
                onSuccess(posts)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    private fun RedditPost.toPost(): Post {
        return Post.Impl(
            id = data.id,
            title = data.title,
            subreddit = data.subreddit,
            author = data.author,
            author_fullname = data.author_fullname,
            downs = data.downs,
            ups = data.ups,
            is_video = data.is_video,
            kind = kind,
            num_comments = data.num_comments,
            permalink = data.permalink,
            post_hint = data.post_hint,
            subreddit_name_prefixed = data.subreddit_name_prefixed,
            thumbnail = data.thumbnail,
            url = data.url
        )
    }

}
