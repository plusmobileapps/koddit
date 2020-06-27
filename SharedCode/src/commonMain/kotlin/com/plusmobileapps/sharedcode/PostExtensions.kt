package com.plusmobileapps.sharedcode

import com.plusmobileapps.sharedcode.db.data.Post

val RedditPostResponse.formattedAuthor: String
    get() = "u/$author"

val RedditPostResponse.shareLink: String
    get() = "https://reddit.com$permalink"

val RedditPostResponse.formattedKarma: String
    get() = ups.toString() ?: downs.toString() ?: "0"