package com.practicum.appplaylistmaker.domain.settings.impl

import com.practicum.appplaylistmaker.data.settings.SettingsRepository
import com.practicum.appplaylistmaker.domain.settings.SettingsInteractor

class SettingsInteractorImpl(private val settingsRepository: SettingsRepository) :
    SettingsInteractor {

    override fun getTheme(): Boolean {
        return settingsRepository.getTheme()
    }

    override fun setTheme(isChecked: Boolean) {
        settingsRepository.setTheme(isChecked)
    }
}