package com.app.retrofitexample.data.preferences

import android.content.Context
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val prefManager by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

    var authToken: String?
        get() = prefManager.getString(AUTH_TOKEN, null)
        set(value) = prefManager.edit().putString(AUTH_TOKEN, value).apply()


    companion object {
        private const val AUTH_TOKEN = "auth_token"
    }
}
