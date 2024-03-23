package com.practicum.appplaylistmaker.data.settings

interface SettingsRepository {
    fun getTheme(): Boolean
    fun setTheme(isDarkTheme: Boolean)
}