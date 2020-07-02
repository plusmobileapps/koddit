package com.plusmobileapps.sharedcode.redux

import com.plusmobileapps.sharedcode.FeedRepository
import com.plusmobileapps.sharedcode.FeedResult
import com.plusmobileapps.sharedcode.di.Di
import com.plusmobileapps.sharedcode.di.commonModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.direct
import org.kodein.di.instance
import org.reduxkotlin.Thunk
import kotlin.coroutines.CoroutineContext

class HomeFeedThunk(networkContext: CoroutineContext) {
    private val networkScope = CoroutineScope(networkContext)

    fun fetchDankMemes(): Thunk<AppState> = { dispatch, _, _ ->
        networkScope.launch {
            dispatch(Actions.LoadingFeed)
            val feedResult = commonModule.direct.instance<FeedRepository>().getDankMemes()
            when(feedResult) {
                is FeedResult.Success -> dispatch(Actions.FeedLoaded(feedResult.posts))
                is FeedResult.Error -> dispatch(Actions.FeedError(feedResult.error))
            }
        }
    }
}