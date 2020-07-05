import com.plusmobileapps.sharedcode.FeedRepository
import com.plusmobileapps.sharedcode.di.Di
import com.plusmobileapps.sharedcode.di.commonModule
import org.kodein.di.direct
import org.kodein.di.instance
import react.child
import react.createContext
import react.dom.render
import kotlin.browser.document


val AppDependenciesContext = createContext<Di>()


fun main() {
    render(document.getElementById("root")) {
        AppDependenciesContext.Provider(Di) {
            child(App)
        }
    }
}