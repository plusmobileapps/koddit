
import com.plusmobileapps.sharedcode.di.Di
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import react.dom.*
import kotlin.browser.document


fun main() {
    val api = Di.getRedditFeedApi()
    GlobalScope.launch {
        val memes = api.getDankMemes()
        console.log(memes)
        render(document.getElementById("root")) {
            h1 {
                +"Koddit"
            }
            testCards(memes.data.children)
//            div {
//                memes.data.children.forEach { meme ->
//
//                    p {
//                        +meme.data.title
//                    }
//                    img {
//                        attrs {
//                            src = meme.data.url
//                        }
//                    }
//                }
//            }
        }
    }

}