package com.practicum.appplaylistmaker.di

import android.content.Context
import com.practicum.appplaylistmaker.data.audioplayer.impl.AudioplayerImpl
import com.practicum.appplaylistmaker.data.search.TrackHistoryRepository
import com.practicum.appplaylistmaker.data.search.TrackRepository
import com.practicum.appplaylistmaker.data.search.impl.TrackHistoryRepositoryImpl
import com.practicum.appplaylistmaker.data.search.impl.TrackRepositoryImpl
import com.practicum.appplaylistmaker.data.settings.SettingsRepository
import com.practicum.appplaylistmaker.data.settings.impl.SettingsRepositoryImpl
import com.practicum.appplaylistmaker.data.sharing.ExternalNavigation
import com.practicum.appplaylistmaker.data.sharing.impl.ExternalNavigationImpl
import com.practicum.appplaylistmaker.domain.audioplayer.AudioplayerInteractor
import com.practicum.appplaylistmaker.domain.audioplayer.impl.AudioplayerInteractorImpl
import com.practicum.appplaylistmaker.domain.search.SearchInteractor
import com.practicum.appplaylistmaker.domain.search.impl.SearchInteractorImpl
import com.practicum.appplaylistmaker.domain.settings.SettingsInteractor
import com.practicum.appplaylistmaker.domain.settings.impl.SettingsInteractorImpl
import com.practicum.appplaylistmaker.domain.sharing.impl.SharingInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    single<SettingsInteractor> {
        SettingsInteractorImpl(get())
    }

    single {
        SharingInteractorImpl(get())
    }

    single {
        SearchInteractorImpl(get(), get())
    }

    single {
        AudioplayerInteractorImpl(get())
    }

}