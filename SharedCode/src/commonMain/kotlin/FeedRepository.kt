import com.plusmobileapps.sharedclient.RedditFeedResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

expect val client: HttpClient

class FeedRepository {

    private val job = Job()
    private val scope = CoroutineScope(job + ApplicationDispatcher)

    fun getDankMemes() {
        scope.launch {
            val response = client.get<RedditFeedResponse>("https://www.reddit.com/r/dankmemes/.json")
            print(response)
        }
    }

}
