package com.practicum.appplaylistmaker.di

import com.practicum.appplaylistmaker.data.search.TrackHistoryRepository
import com.practicum.appplaylistmaker.data.search.TrackRepository
import com.practicum.appplaylistmaker.data.search.impl.TrackHistoryRepositoryImpl
import com.practicum.appplaylistmaker.data.search.impl.TrackRepositoryImpl
import com.practicum.appplaylistmaker.data.settings.SettingsRepository
import com.practicum.appplaylistmaker.data.settings.impl.SettingsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module{

    single<TrackHistoryRepository> {
        TrackHistoryRepositoryImpl(get())
    }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }

    single<TrackRepository> {
        TrackRepositoryImpl(get())
    }


}