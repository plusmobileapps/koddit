package com.plusmobileapps.koddit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.plusmobileapps.sharedcode.RedditPostResponse
import com.plusmobileapps.sharedcode.redux.Actions
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
        dispatch(FeedStarted)
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
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            this.adapter = this@HomeFeedFragment.adapter
        }
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

    override fun showShareSheet(url: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        requireContext().startActivity(shareIntent)
        dispatch(Actions.ShareSheetShown)
    }
}