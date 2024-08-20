package com.practicum.appplaylistmaker.di

import com.example.practicum.playlist.ui.settings.view_model.SettingsViewModel
import com.practicum.appplaylistmaker.MainViewModel
import com.practicum.appplaylistmaker.ui.audioplayer.view_model.AudioplayerViewModel
import com.practicum.appplaylistmaker.ui.media.view_model.FavouriteTracksViewModel
import com.practicum.appplaylistmaker.ui.media.view_model.PlaylistsViewModel
import com.practicum.appplaylistmaker.ui.new_playlist.view_model.NewPlaylistViewModel
import com.practicum.appplaylistmaker.ui.playlist.view_model.OpenedPlaylistViewModel
import com.practicum.appplaylistmaker.ui.search.view_model.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        AudioplayerViewModel(get(), get(), get())
    }

    viewModel {
        SearchViewModel(get())
    }

    viewModel{
        SettingsViewModel(get(),get())
    }

    viewModel{
        MainViewModel(get())
    }

    viewModel{
        FavouriteTracksViewModel(get())
    }
    viewModel{
        NewPlaylistViewModel(get())
    }
    viewModel{
        PlaylistsViewModel(get())
    }
    viewModel{
        OpenedPlaylistViewModel(get())
    }
}