package com.example.practicum.playlist.ui.settings.view_model

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import com.practicum.appplaylistmaker.domain.settings.SettingsInteractor
import com.practicum.appplaylistmaker.domain.sharing.SharingInteractor

class SettingsViewModel(
    private val settingsInteractor: SettingsInteractor,
    private val sharingInteractor: SharingInteractor
) : ViewModel() {


    fun onShareAppClicked() {
        sharingInteractor.shareApp()
    }

    fun onSupportClicked() {
        sharingInteractor.openSupport()
    }

    fun setTheme(isChecked: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        settingsInteractor.setTheme(isChecked)
    }

    fun getTheme(): Boolean {
        return settingsInteractor.getTheme()
    }

    fun onUserAgreementClicked() {
        sharingInteractor.openTerms()
    }
}