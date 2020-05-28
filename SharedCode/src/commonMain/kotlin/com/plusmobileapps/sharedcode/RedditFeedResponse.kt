package com.plusmobileapps.sharedcode

import kotlinx.serialization.Serializable

/**
 * represents the response of hitting a subreddit feed
 *
 * @sample https://www.reddit.com/r/dankmemes/.json
 */
@Serializable
data class RedditFeedResponse(
    val kind: String,
    val data: FeedData
)

@Serializable
data class FeedData(
    val modhash: String,
    val dist: Int,
    val children: List<RedditPost>
)

@Serializable
data class RedditPost(
    val kind: String,
    val data: RedditPostResponse
)

@Serializable
data class RedditPostResponse(
    val subreddit: String,
    val author_fullname: String,
    val title: String,
    val subreddit_name_prefixed: String,
    val downs: Int,
    val ups: Int,
    val id: String,
    val thumbnail: String,
    val post_hint: String,
    val url: String,
    val num_comments: Int,
    val author: String,
    val permalink: String,
    val is_video: Boolean
)
