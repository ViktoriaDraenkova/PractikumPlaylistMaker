package com.practicum.appplaylistmaker.di

import com.practicum.appplaylistmaker.data.search.impl.TrackHistoryRepositoryImpl
import com.practicum.appplaylistmaker.data.search.impl.TrackRepositoryImpl
import com.practicum.appplaylistmaker.data.settings.impl.SettingsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module{

    single {
        TrackHistoryRepositoryImpl(get())
    }
    single { SettingsRepositoryImpl(get()) }

    single {
        TrackRepositoryImpl(get())
    }


}