package com.practicum.appplaylistmaker

import android.util.Log
import androidx.lifecycle.ViewModel
import com.practicum.appplaylistmaker.domain.settings.SettingsInteractor

class MainViewModel(private val settingsInteractor: SettingsInteractor): ViewModel() {
    fun switchTheme() {
        val systemTheme=settingsInteractor.getTheme()
        Log.d("ThemeInViewModel", systemTheme.toString())
        settingsInteractor.setTheme(systemTheme)
        Log.d("ThemeInViewModel", settingsInteractor.getTheme().toString())

    }

}