package com.plusmobileapps.koddit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plusmobileapps.sharedcode.RedditPostResponse
import com.plusmobileapps.sharedcode.redux.FeedStarted
import com.plusmobileapps.sharedcode.ui.HomeFeedView

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFeedFragment : BaseFragment<HomeFeedView>(),
    HomeFeedView {

    private val adapter = RedditFeedAdapter()
    private lateinit var progressBar: ProgressBar
    private lateinit var errorMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            this.adapter = this@HomeFeedFragment.adapter
        }
        dispatch(FeedStarted)
        return view
    }

    override fun showLoading(show: Boolean) = progressBar.showOrGone(show)

    override fun showError(error: String) {
        errorMessage.visibility = View.VISIBLE
        errorMessage.text = error
    }

    override fun hideError() {
        errorMessage.visibility = View.GONE
    }

    override fun showPosts(posts: List<RedditPostResponse>) {
        adapter.submitList(posts)
    }

}