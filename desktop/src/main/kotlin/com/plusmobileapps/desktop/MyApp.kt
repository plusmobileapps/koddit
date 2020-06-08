package com.plusmobileapps.desktop

import tornadofx.*

fun main(args: Array<String>) {
    launch<MyApp>(args)
}

class MyApp : App(MyView::class) {

}