package com.plusmobileapps.sharedcode.ui

import com.plusmobileapps.sharedcode.redux.AppState
import com.plusmobileapps.sharedcode.redux.presentermiddleware.*

typealias BaseView = ViewWithProvider<AppState>

//a Presenter typed to our app's State type for convenience
fun <V: BaseView> presenter(actions: PresenterBuilder<AppState, V>): Presenter<View, AppState> {
    @Suppress("UNCHECKED_CAST")
    return createGenericPresenter(actions) as Presenter<View, AppState>
}