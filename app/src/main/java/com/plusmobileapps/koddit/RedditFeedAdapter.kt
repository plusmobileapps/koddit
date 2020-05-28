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

interface RedditFeedItemListener {
    fun onMoreOptionsClicked(id: String)
    fun onPostClicked(id: String, imageview: ImageView)
    fun onUpVoteClicked(id: String)
    fun onDownVoteClicked(id: String)
    fun onCommentClicked(id: String)
    fun onShareButtonClicked(id: String)
}

class RedditFeedItemDiffer : DiffUtil.ItemCallback<RedditFeedItem>() {
    override fun areItemsTheSame(oldItem: RedditFeedItem, newItem: RedditFeedItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RedditFeedItem, newItem: RedditFeedItem): Boolean {
        return oldItem == newItem
    }
}

class RedditFeedAdapter(private val listener: RedditFeedItemListener) :
    ListAdapter<RedditFeedItem, RedditFeedViewHolder>(RedditFeedItemDiffer()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditFeedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reddit_feed_list_item, parent, false)
        return RedditFeedViewHolder(listener, view)
    }

    override fun onBindViewHolder(holder: RedditFeedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RedditFeedViewHolder(private val listener: RedditFeedItemListener, view: View) :
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

    fun bind(data: RedditFeedItem) {
        subredditImage.load(data.subredditImageUrl, imageLoader = imageLoader)
        subreddit.text = data.subreddit
        username.text = data.username
        postTitle.text = data.title
        description.load(data.description, imageLoader = imageLoader)
        description.transitionName = "$adapterPosition-${data.id}"
        karmaCount.text = data.karmaCount
        moreOptions.setOnClickListener { listener.onMoreOptionsClicked(data.id) }
        downVote.setOnClickListener { listener.onDownVoteClicked(data.id) }
        upVote.setOnClickListener { listener.onUpVoteClicked(data.id) }
        commentButton.setOnClickListener { listener.onCommentClicked(data.id) }
        shareButton.setOnClickListener { listener.onShareButtonClicked(data.id) }
        itemView.setOnClickListener { listener.onPostClicked(data.id, description) }
        moreOptions.setOnClickListener {
            listener.onMoreOptionsClicked(data.id)
        }
    }

}