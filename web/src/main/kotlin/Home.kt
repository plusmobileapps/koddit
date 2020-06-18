
import com.plusmobileapps.sharedcode.di.Di
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import react.dom.h1
import react.dom.render
import kotlin.browser.document


fun main() {
    val api = Di.getRedditFeedApi()
    GlobalScope.launch {
        console.log(api.getDankMemes())
    }
    render(document.getElementById("root")) {
        h1 {
            +"Hello, React+Kotlin/JS!"
        }
    }
}