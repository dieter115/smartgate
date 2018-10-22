package com.flashapps.smartgate.extensions

import android.R
import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Created by dietervaesen on 23/01/18.
 */
//snackbar shortening
fun snackbar(parentView: View, message: String) = Snackbar.make(parentView, message, Snackbar.LENGTH_LONG)

fun Activity.snackbar(message: String) = snackbar(findViewById(R.id.content), message).show()
fun Fragment.snackbar(message: String) = snackbar(activity!!.findViewById(R.id.content), message).show()


//keyboard management
fun Activity.openKeyBoard(edittext: EditText) {
    edittext.requestFocus()
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Fragment.openKeyBoard(edittext: EditText) {
    edittext.requestFocus()
    val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}


fun Activity.hideKeyboard() {

    // Check if no view has focus:
    val view = currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Fragment.hideKeyboard() {

    // Check if no view has focus:
    val view = activity!!.currentFocus
    if (view != null) {
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.isLollipopOrMore(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}

