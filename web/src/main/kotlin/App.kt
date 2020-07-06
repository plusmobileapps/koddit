import com.plusmobileapps.sharedcode.redux.IncrementCount
import com.plusmobileapps.sharedcode.redux.reduxStore
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*


val App = functionalComponent<RProps> { _ ->
    val appDependencies = useContext(AppDependenciesContext)
    val repository = appDependencies.getRedditFeedApi()

    val (posts, setPosts) = useState(reduxStore.state)
    //Do you need to unsubscribe in js?
    val count = reduxStore.subscribe {
        setPosts(reduxStore.state)
    }

//    useEffectWithCleanup(dependencies = listOf()) {
//        val mainScope = MainScope()
//
//        mainScope.launch {
//            val memes = repository.getDankMemes().data.children
//            setPosts(memes)
//        }
//        return@useEffectWithCleanup { mainScope.cancel() }
//    }

            h1 {
                +"Koddit"
            }

            div {
                p {
                    attrs {
                        onClickFunction = {
                        }
                    }
                    +posts.count.toString()
                }

                button {
                    attrs {
                        onClickFunction = {
                            reduxStore.dispatch(IncrementCount())
                        }
                        +"Increment Count"
                    }
                }
//                posts.forEach { meme ->
//                    p {
//                        +meme.data.title
//                    }
//                    img {
//                        attrs {
//                            src = meme.data.url
//                        }
//                    }
//                }
            }
}

