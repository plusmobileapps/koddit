import com.jfoenix.controls.JFXButton
import com.plusmobileapps.sharedcode.di.Di
import tornadofx.*

class MainView : View("Hello from koddit!") {
    init {
        Di.getFeedRepository().getDankMemes(
            onSuccess = {
                print("dank memes success: $it")
            },
            onError = {
                print("dank memes error: $it")
            })
    }

    override val root = hbox {
        print("")
        label(title) {
            addClass(Styles.heading)
        }
        add(JFXButton("Hello"))

        button("login") {

        }
    }
}