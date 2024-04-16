package com.practicum.appplaylistmaker.domain.settings

interface SettingsInteractor {
    fun getTheme(): Boolean
    fun setTheme(isDarkTheme: Boolean)
}