package com.plusmobileapps.sharedcode

import com.plusmobileapps.sharedcode.db.MyDatabase
import com.plusmobileapps.sharedcode.db.data.Post
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

expect val client: HttpClient

expect fun createDb(): MyDatabase

sealed class FeedResult {
    data class Success(val posts: List<RedditPostResponse>) : FeedResult()
    data class Error(val error: String) : FeedResult()
}

class FeedRepository(
    private val db: MyDatabase,
    private val api: RedditFeedApi,
    dispatcher: CoroutineDispatcher
) {

    private val job = Job()
    private val scope = CoroutineScope(job + dispatcher)

    suspend fun getDankMemes(): FeedResult {
        val cache = db.postQueries.selectAll().executeAsList()
        if (cache.isNotEmpty()) {
            return FeedResult.Success(cache.map { it.toRedditPost() })
        }
        try {
            val response = api.getDankMemes()
            response.data.children.forEach { redditPost ->
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
            }
            return FeedResult.Success(response.data.children.map { it.data })
        } catch (e: Exception) {
            return FeedResult.Error(e.toString())
        }
    }

    suspend fun upVote(postId: String): Boolean {
        //TODO
        return true
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

    private fun Post.toRedditPost(): RedditPostResponse {
        return RedditPostResponse(
            id = id,
            ups = ups ?: 0,
            downs = downs ?: 0,
            title = title,
            author = author,
            author_fullname = author_fullname,
            is_video = is_video ?: false,
            num_comments = num_comments ?: 0,
            permalink = permalink,
            post_hint = post_hint,
            subreddit = subreddit,
            subreddit_name_prefixed = subreddit_name_prefixed,
            thumbnail = thumbnail,
            url = url
        )
    }

}
