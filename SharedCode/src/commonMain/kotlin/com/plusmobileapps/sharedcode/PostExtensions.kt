package com.plusmobileapps.sharedcode

import com.plusmobileapps.sharedcode.db.data.Post

val Post.formattedAuthor: String
    get() = "u/$author"

val Post.shareLink: String
    get() = "https://reddit.com$permalink"

val Post.formattedKarma: String
    get() = ups?.toString() ?: downs?.toString() ?: "0"