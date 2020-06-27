package com.plusmobileapps.koddit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plusmobileapps.sharedcode.FeedRepository
import com.plusmobileapps.sharedcode.RedditPostResponse
import com.plusmobileapps.sharedcode.di.commonModule
import com.plusmobileapps.sharedcode.redux.AppState
import com.plusmobileapps.sharedcode.redux.FeedStarted
import com.plusmobileapps.sharedcode.redux.UpVoteAction
import com.plusmobileapps.sharedcode.redux.store
import com.plusmobileapps.sharedcode.shareLink
import org.kodein.di.direct
import org.kodein.di.instance
import org.reduxkotlin.StoreSubscription

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), RedditFeedItemListener {

    private lateinit var storeSubscription: StoreSubscription
    val adapter = RedditFeedAdapter(this)
    private lateinit var progressBar: ProgressBar
    private lateinit var errorMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val foo = commonModule.direct.instance<FeedRepository>().getDankMemes()
        foo(store.dispatch, store.getState, null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        errorMessage = view.findViewById(R.id.error_text)
        val recyclerView = view.findViewById<RecyclerView>(R.id.reddit_home_feed)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            this.adapter = this@FirstFragment.adapter
        }
        // Inflate the layout for this fragment
        storeSubscription = store.subscribe {
            render(store.state)
        }
        store.dispatch(FeedStarted)
        return view
    }

    override fun onDestroy() {
        storeSubscription()
        super.onDestroy()
    }

    private fun render(appState: AppState) {
        when {
            appState.isLoading -> {
                progressBar.visibility = View.VISIBLE
                errorMessage.visibility = View.GONE
            }
            appState.error != null -> {
                errorMessage.visibility = View.VISIBLE
                errorMessage.text = appState.error
                progressBar.visibility = View.GONE
            }
            appState.posts.isNotEmpty() -> {
                errorMessage.visibility = View.GONE
                progressBar.visibility = View.GONE
                adapter.submitList(appState.posts)
            }

        }
    }

    override fun onMoreOptionsClicked(post: RedditPostResponse) {
    }

    override fun onPostClicked(post: RedditPostResponse, imageview: ImageView) {
    }

    override fun onUpVoteClicked(post: RedditPostResponse) {
        store.dispatch(UpVoteAction(post.id))
    }

    override fun onDownVoteClicked(post: RedditPostResponse) {
    }

    override fun onCommentClicked(post: RedditPostResponse) {
    }

    override fun onShareButtonClicked(post: RedditPostResponse) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, post.shareLink)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        requireContext().startActivity(shareIntent)
    }

}