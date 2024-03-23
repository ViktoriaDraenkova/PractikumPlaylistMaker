package com.practicum.appplaylistmaker.data.settings.impl

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.activity.ComponentActivity
import com.practicum.appplaylistmaker.PRACTICUM_EXAMPLE_PREFERENCES
import com.practicum.appplaylistmaker.data.settings.SettingsRepository

class SettingsRepositoryImpl(
    var context: Context
) : SettingsRepository {
    companion object {
        const val THEME_SWITCHER_KEY = "key_for_theme_switcher"
    }

    private fun initLala(): SharedPreferences {
        Log.d("", (context).toString())
        return context.getSharedPreferences(
            PRACTICUM_EXAMPLE_PREFERENCES,
            ComponentActivity.MODE_PRIVATE
        )
    }

    private var sharedPrefs: SharedPreferences = initLala()

    override fun getTheme(): Boolean {
        return sharedPrefs.getBoolean(THEME_SWITCHER_KEY, false)
    }

    override fun setTheme(isDarkTheme: Boolean) {
        sharedPrefs.edit()
            .putBoolean(THEME_SWITCHER_KEY, isDarkTheme)
            .apply()
    }

}