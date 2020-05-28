package com.plusmobileapps.koddit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plusmobileapps.sharedcode.FeedRepository
import com.plusmobileapps.sharedcode.createApplicationScreenMessage
import com.plusmobileapps.sharedcode.db.data.Post

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), RedditFeedItemListener {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.reddit_home_feed)
        val adapter = RedditFeedAdapter(this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            this.adapter = adapter
        }
        FeedRepository().getDankMemes(
            onSuccess = { posts ->
                Log.d("FirstFragment", posts.toString())
                adapter.submitList(posts.map(Post::toRedditFeedItem))
            },
            onError = {
                Log.e("FirstFragment", it.toString())
            }
        )
    }

    override fun onMoreOptionsClicked(id: String) {

    }

    override fun onPostClicked(id: String, imageview: ImageView) {
    }

    override fun onUpVoteClicked(id: String) {
    }

    override fun onDownVoteClicked(id: String) {
    }

    override fun onCommentClicked(id: String) {
    }

    override fun onShareButtonClicked(id: String) {
    }
}

private fun Post.toRedditFeedItem(): RedditFeedItem {
    return RedditFeedItem(
        id = id,
        title = title,
        subreddit = subreddit_name_prefixed,
        username = author_fullname,
        timePosted = "12:00", //todo
        subredditImageUrl = "",
        karmaCount = ups.toString(),
        description = url
    )
}
