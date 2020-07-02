package com.plusmobileapps.koddit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.plusmobileapps.sharedcode.RedditPostResponse
import com.plusmobileapps.sharedcode.formattedAuthor
import com.plusmobileapps.sharedcode.formattedKarma
import com.plusmobileapps.sharedcode.redux.*

data class RedditFeedItem(
    val id: String,
    val subreddit: String,
    val subredditImageUrl: String,
    val username: String,
    val timePosted: String,
    val title: String,
    val description: String,
    val karmaCount: String
)

class RedditFeedItemDiffer : DiffUtil.ItemCallback<RedditPostResponse>() {

    override fun areItemsTheSame(oldItem: RedditPostResponse, newItem: RedditPostResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RedditPostResponse, newItem: RedditPostResponse): Boolean {
        return oldItem == newItem
    }
}

class RedditFeedAdapter : ListAdapter<RedditPostResponse, RedditFeedViewHolder>(RedditFeedItemDiffer()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditFeedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reddit_feed_list_item, parent, false)
        return RedditFeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: RedditFeedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RedditFeedViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {

    val imageLoader = itemView.context.gifImageLoader

    private val subredditImage: ImageView = itemView.findViewById(R.id.feed_item_subreddit_image)
    private val subreddit: TextView = itemView.findViewById(R.id.feed_item_subreddit_name)
    private val moreOptions: ImageButton = itemView.findViewById(R.id.feed_item_more_options)
    private val username: TextView = itemView.findViewById(R.id.feed_item_user_name)
    private val postTitle: TextView = itemView.findViewById(R.id.feed_item_title)
    private val description: ImageView = itemView.findViewById(R.id.feed_item_description)
    private val downVote: ImageButton = itemView.findViewById(R.id.feed_item_down_vote)
    private val upVote: ImageButton = itemView.findViewById(R.id.feed_item_up_button)
    private val karmaCount: TextView = itemView.findViewById(R.id.feed_item_karma_count)
    private val commentButton: Button = itemView.findViewById(R.id.feed_item_comment_button)
    private val shareButton: ImageButton = itemView.findViewById(R.id.feed_item_share_button)

    fun bind(data: RedditPostResponse) {
//        subredditImage.load(data.subredditImageUrl, imageLoader = imageLoader) todo need to fetch subreddit image url
        subreddit.text = data.subreddit
        username.text = data.formattedAuthor
        postTitle.text = data.title
        description.load(data.url, imageLoader = imageLoader)
        description.transitionName = "$adapterPosition-${data.id}"
        karmaCount.text = data.formattedKarma
        moreOptions.setOnClickListener {
            dispatch(MoreOptionsAction(data.id))
        }
        downVote.setOnClickListener { dispatch(DownVoteAction(data.id)) }
        upVote.setOnClickListener { dispatch(UpVoteAction(data.id)) }
        commentButton.setOnClickListener { dispatch(OpenCommentAction(data.id)) }
        shareButton.setOnClickListener {
            dispatch(SharePostAction(data.id))
            //TODO
            //        val sendIntent: Intent = Intent().apply {
//            action = Intent.ACTION_SEND
//            putExtra(Intent.EXTRA_TEXT, post.shareLink)
//            type = "text/plain"
//        }
//
//        val shareIntent = Intent.createChooser(sendIntent, null)
//        requireContext().startActivity(shareIntent)
        }
        itemView.setOnClickListener { dispatch(PostDetailAction(data.id)) }
    }

}