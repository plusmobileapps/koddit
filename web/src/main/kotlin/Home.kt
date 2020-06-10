import kotlin.browser.document
import react.dom.*


fun main() {
    render(document.getElementById("root")) {
        h1 {
            +"Hello, React+Kotlin/JS!"
        }
    }
}