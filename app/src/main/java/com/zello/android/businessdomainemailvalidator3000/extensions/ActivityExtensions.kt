package com.zello.android.businessdomainemailvalidator3000.extensions

import android.app.Activity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import com.zello.android.businessdomainemailvalidator3000.R

fun Activity.disableUserInteraction() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Activity.enableUserInteraction() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun Activity.showIOIndicator() {
    runOnUiThread {
        disableUserInteraction()
        findViewById<ProgressBar>(R.id.loading)?.visibility = View.VISIBLE
    }
}

fun Activity.hideIOIndicator() {
    runOnUiThread {
        enableUserInteraction()
        findViewById<ProgressBar>(R.id.loading)?.visibility = View.GONE
    }
}

fun Activity.hideKeyboard(view: View) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
