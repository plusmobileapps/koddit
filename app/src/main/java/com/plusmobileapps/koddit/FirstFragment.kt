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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plusmobileapps.sharedcode.FeedRepository
import com.plusmobileapps.sharedcode.RedditPostResponse
import com.plusmobileapps.sharedcode.di.commonModule
import com.plusmobileapps.sharedcode.redux.*
import com.plusmobileapps.sharedcode.shareLink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kodein.di.direct
import org.kodein.di.instance
import org.reduxkotlin.StoreSubscription

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var storeSubscription: StoreSubscription
    val adapter = RedditFeedAdapter()
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
            lifecycleScope.launchWhenStarted {
                withContext(Dispatchers.Main) {
                    render(store.state)
                }
            }
        }
        store.dispatch(FeedStarted)
        return view
    }

    override fun onDestroy() {
        storeSubscription()
        super.onDestroy()
    }

    private fun render(appState: AppState) {
        progressBar.showOrGone(appState.isLoading)
        adapter.submitList(appState.posts)
        errorMessage.showOrGone(appState.error != null)
        errorMessage.text = appState.error
    }

}