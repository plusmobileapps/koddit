import com.plusmobileapps.sharedcode.RedditPost
import com.plusmobileapps.sharedcode.RedditPostResponse
import com.plusmobileapps.sharedcode.di.Di
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import react.*
import react.dom.*
import kotlin.browser.document


val App = functionalComponent<RProps> { _ ->
    val appDependencies = useContext(AppDependenciesContext)
    val repository = appDependencies.getRedditFeedApi()

    val (posts, setPosts) = useState(emptyList<RedditPost>())

    useEffectWithCleanup(dependencies = listOf()) {
        val mainScope = MainScope()

        mainScope.launch {
            val memes = repository.getDankMemes().data.children
            setPosts(memes)
        }
        return@useEffectWithCleanup { mainScope.cancel() }
    }

            h1 {
                +"Koddit"
            }

            div {
                posts.forEach { meme ->
                    p {
                        +meme.data.title
                    }
                    img {
                        attrs {
                            src = meme.data.url
                        }
                    }
                }
            }
}

