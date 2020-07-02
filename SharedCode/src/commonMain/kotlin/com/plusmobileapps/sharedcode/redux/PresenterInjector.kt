package com.plusmobileapps.sharedcode.redux

import com.plusmobileapps.sharedcode.redux.presentermiddleware.AttachView
import com.plusmobileapps.sharedcode.redux.presentermiddleware.DetachView
import com.plusmobileapps.sharedcode.redux.presentermiddleware.View
import com.plusmobileapps.sharedcode.redux.presentermiddleware.rootDispatch


//Here only until a solution is found for rootDispatch & AttachView class not
//visible from swift.
fun attachView(view: View) = rootDispatch(AttachView(view))
fun detachView(view: View) = rootDispatch(DetachView(view))
fun clearView(view: View) = rootDispatch(DetachView(view))
