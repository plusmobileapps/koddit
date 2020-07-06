package com.plusmobileapps.koddit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.plusmobileapps.sharedcode.db.data.Post
import com.plusmobileapps.sharedcode.redux.reduxStore
import com.plusmobileapps.sharedcode.shareLink
import org.reduxkotlin.StoreSubscription

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), RedditFeedItemListener {
    lateinit var storeSubscription: StoreSubscription
    private lateinit var counterText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        counterText = view.findViewById(R.id.counter)
        storeSubscription = reduxStore.subscribe {
            counterText.text = reduxStore.state.count.toString()
        }
//        val recyclerView = view.findViewById<RecyclerView>(R.id.reddit_home_feed)
//        val adapter = RedditFeedAdapter(this)
//        recyclerView.apply {
//            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
//            this.adapter = adapter
//        }
//        commonModule.direct.instance<FeedRepository>().getDankMemes(
//            onSuccess = { posts ->
//                Log.d("FirstFragment", posts.toString())
//                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
//                    adapter.submitList(posts as List<Post.Impl>)
//                }
//            },
//            onError = {
//                Log.e("FirstFragment", it.toString())
//            }
//        )
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