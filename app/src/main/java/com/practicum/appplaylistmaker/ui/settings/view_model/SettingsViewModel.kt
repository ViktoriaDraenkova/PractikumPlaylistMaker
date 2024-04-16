package com.example.practicum.playlist.ui.settings.view_model

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.practicum.appplaylistmaker.App
import com.practicum.appplaylistmaker.creator.Creator
import com.practicum.appplaylistmaker.domain.settings.SettingsInteractor
import com.practicum.appplaylistmaker.domain.sharing.SharingInteractor

class SettingsViewModel(
    private val settingsInteractor: SettingsInteractor,
    private val sharingInteractor: SharingInteractor,
) : ViewModel() {


    fun onShareAppClicked() {
        sharingInteractor.shareApp()
    }

    fun onSupportClicked() {
        sharingInteractor.openSupport()
    }

    fun initTheme() {
        setTheme(settingsInteractor.getTheme())
    }

    fun setTheme(isDarkTheme: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (settingsInteractor.getTheme()) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        settingsInteractor.setTheme(isDarkTheme)
    }

    fun getTheme(): Boolean {
        return settingsInteractor.getTheme()
    }

    fun onUserAgreementClicked() {
        sharingInteractor.openTerms()
    }
}