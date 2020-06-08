import tornadofx.*

class MainView : View("Hello from koddit!") {
    override val root = hbox {
        label(title) {
            addClass(Styles.heading)
        }
    }
}