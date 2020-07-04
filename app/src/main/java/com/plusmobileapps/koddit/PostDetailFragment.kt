package com.plusmobileapps.koddit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import coil.api.load
import com.plusmobileapps.sharedcode.RedditPostResponse
import com.plusmobileapps.sharedcode.ui.PostDetailView

class PostDetailFragment : BaseFragment<PostDetailView>(), PostDetailView {

    private lateinit var title: TextView
    private lateinit var image: ImageView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.post_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view.findViewById<TextView>(R.id.post_detail_title)
        image = view.findViewById<ImageView>(R.id.post_detail_image)
    }

    override fun showPost(post: RedditPostResponse) {
        title.text = post.title
        image.load(post.url, imageLoader = requireContext().gifImageLoader)
    }
}
