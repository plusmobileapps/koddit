package com.plusmobileapps.koddit

import android.content.Intent
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
import com.plusmobileapps.sharedcode.formattedAuthor
import com.plusmobileapps.sharedcode.shareLink

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
                adapter.submitList(posts as List<Post.Impl>)
            },
            onError = {
                Log.e("FirstFragment", it.toString())
            }
        )
    }

    override fun onMoreOptionsClicked(post: Post) {
    }

    override fun onPostClicked(post: Post, imageview: ImageView) {
    }

    override fun onUpVoteClicked(post: Post) {
    }

    override fun onDownVoteClicked(post: Post) {
    }

    override fun onCommentClicked(post: Post) {
    }

    override fun onShareButtonClicked(post: Post) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, post.shareLink)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        requireContext().startActivity(shareIntent)
    }

}