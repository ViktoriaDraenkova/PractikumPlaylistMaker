package com.practicum.appplaylistmaker.creator

import android.app.Application
import android.content.Context
import android.util.Log
import com.practicum.appplaylistmaker.data.settings.SettingsRepository
import com.practicum.appplaylistmaker.data.settings.impl.SettingsRepositoryImpl
import com.practicum.appplaylistmaker.data.sharing.ExternalNavigation
import com.practicum.appplaylistmaker.data.sharing.impl.ExternalNavigationImpl
import com.practicum.appplaylistmaker.domain.settings.SettingsInteractor
import com.practicum.appplaylistmaker.domain.settings.impl.SettingsInteractorImpl
import com.practicum.appplaylistmaker.domain.sharing.SharingInteractor
import com.practicum.appplaylistmaker.domain.sharing.impl.SharingInteractorImpl

object Creator {

    fun getSettingsRepository(context: Context): SettingsRepository {
        return SettingsRepositoryImpl(context)
    }

    fun getSettingsInteractor(application: Application): SettingsInteractor {
        Log.d("", "getting settings interactor")
        return SettingsInteractorImpl(getSettingsRepository(application))
    }

    fun getExternalNavigation(context: Context): ExternalNavigation {
        Log.d("BBBBBBBBBBBBBB", "Getting external navigation")
        return ExternalNavigationImpl(context)
    }

    fun getSharingInteractor(context: Context): SharingInteractor {
        return SharingInteractorImpl(getExternalNavigation(context))
    }
}