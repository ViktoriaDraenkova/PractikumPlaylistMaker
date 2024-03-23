package com.example.practicum.playlist.ui.settings.view_model

import android.app.Application
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
    private val application: App
) : ViewModel() {

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    val application = checkNotNull(extras[APPLICATION_KEY]) as App

                    return SettingsViewModel(
                        Creator.getSettingsInteractor(application),
                        Creator.getSharingInteractor(application),
                        application
                    ) as T
                }
            }
    }

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
        application.switchTheme(isDarkTheme)
        settingsInteractor.setTheme(isDarkTheme)
    }

    fun getTheme(): Boolean {
        return settingsInteractor.getTheme()
    }

    fun onUserAgreementClicked() {
        sharingInteractor.openTerms()
    }
}