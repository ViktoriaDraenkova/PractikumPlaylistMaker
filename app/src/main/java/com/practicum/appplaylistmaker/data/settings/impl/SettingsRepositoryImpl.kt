package com.practicum.appplaylistmaker.data.settings.impl

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.activity.ComponentActivity
import com.practicum.appplaylistmaker.PRACTICUM_EXAMPLE_PREFERENCES
import com.practicum.appplaylistmaker.data.settings.SettingsRepository

class SettingsRepositoryImpl(
    val sharedPrefs: SharedPreferences
) : SettingsRepository {
    companion object {
       private const val THEME_SWITCHER_KEY = "key_for_theme_switcher"
    }

    override fun getTheme(): Boolean {
        return sharedPrefs.getBoolean(THEME_SWITCHER_KEY, false)
    }

    override fun setTheme(isDarkTheme: Boolean) {
        sharedPrefs.edit()
            .putBoolean(THEME_SWITCHER_KEY, isDarkTheme)
            .apply()
    }

}