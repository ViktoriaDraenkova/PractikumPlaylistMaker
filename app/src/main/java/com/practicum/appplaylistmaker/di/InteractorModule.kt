package com.practicum.appplaylistmaker.di


import com.practicum.appplaylistmaker.domain.audioplayer.AudioplayerInteractor
import com.practicum.appplaylistmaker.domain.audioplayer.impl.AudioplayerInteractorImpl
import com.practicum.appplaylistmaker.domain.favourites.FavouritesInteractor
import com.practicum.appplaylistmaker.domain.favourites.impl.FavouritesInteractorImpl
import com.practicum.appplaylistmaker.domain.playlist.PlaylistInteractor
import com.practicum.appplaylistmaker.domain.playlist.impl.PlaylistInteractorImpl
import com.practicum.appplaylistmaker.domain.search.SearchInteractor
import com.practicum.appplaylistmaker.domain.search.impl.SearchInteractorImpl
import com.practicum.appplaylistmaker.domain.settings.SettingsInteractor
import com.practicum.appplaylistmaker.domain.settings.impl.SettingsInteractorImpl
import com.practicum.appplaylistmaker.domain.sharing.SharingInteractor
import com.practicum.appplaylistmaker.domain.sharing.impl.SharingInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    single<SettingsInteractor> {
        SettingsInteractorImpl(get())
    }

    single<SharingInteractor> {
        SharingInteractorImpl(get())
    }

    single<SearchInteractor> {
        SearchInteractorImpl(get(), get())
    }

    single<AudioplayerInteractor> {
        AudioplayerInteractorImpl(get())
    }
    single<FavouritesInteractor>{
        FavouritesInteractorImpl(get())
    }
    single<PlaylistInteractor>{
        PlaylistInteractorImpl(get())
    }

}