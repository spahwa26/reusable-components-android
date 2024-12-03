package com.app.retrofitexample.utils

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.View
import com.app.retrofitexample.utils.LocalisedException
import com.app.retrofitexample.utils.NoInternetException
import com.app.retrofitexample.utils.SomethingWentWrongException
import com.google.android.material.snackbar.Snackbar
import java.io.IOException

//TODO: remove the pre-defined extensions if not needed

fun Long.postDelayed(handlerData: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({
        handlerData.invoke()
    }, this)
}

fun Activity.getActivity() = this

val Exception?.localizedException: LocalisedException
    get() {
        return when (this) {
            is LocalisedException -> this
            is IOException -> NoInternetException(message)
            else -> SomethingWentWrongException(this?.localizedMessage)
        }
    }

fun View.showSnackBar(msg: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, msg, length).show()
}