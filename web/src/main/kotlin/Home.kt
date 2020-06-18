
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.request.get
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.browser.document
import react.dom.*


fun main() {
    val client = HttpClient(Js) {

    }
    GlobalScope.launch {
        val feed = client.get<String>("https://www.reddit.com/r/dankmemes/.json")
        console.log(feed)
    }
    console.log("Hello world")
    render(document.getElementById("root")) {
        h1 {
            +"Hello from koddit, React+Kotlin/JS!"
        }
    }
}