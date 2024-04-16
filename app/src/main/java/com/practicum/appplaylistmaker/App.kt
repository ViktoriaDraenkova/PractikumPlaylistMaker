package com.practicum.appplaylistmaker

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.appplaylistmaker.creator.Creator
import com.practicum.appplaylistmaker.domain.settings.SettingsInteractor

class App : Application() {

    private var darkTheme = false
    private lateinit var settingsInteractor: SettingsInteractor

    override fun onCreate() {
        super.onCreate()
        settingsInteractor = Creator.getSettingsInteractor(this)
        Log.d(
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", getSharedPreferences(
                PRACTICUM_EXAMPLE_PREFERENCES,
                ComponentActivity.MODE_PRIVATE
            ).toString()
        )
        switchTheme(settingsInteractor.getTheme())
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}
 