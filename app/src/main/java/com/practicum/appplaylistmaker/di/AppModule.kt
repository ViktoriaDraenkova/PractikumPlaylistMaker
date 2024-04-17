package com.practicum.appplaylistmaker.di

import com.example.practicum.playlist.ui.settings.view_model.SettingsViewModel
import com.practicum.appplaylistmaker.MainViewModel
import com.practicum.appplaylistmaker.ui.audioplayer.view_model.AudioplayerViewModel
import com.practicum.appplaylistmaker.ui.search.view_model.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        AudioplayerViewModel(get())
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

}