package com.practicum.appplaylistmaker.creator

import android.app.Application
import android.content.Context
import android.util.Log
import com.practicum.appplaylistmaker.data.audioplayer.Audioplayer
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
import com.practicum.appplaylistmaker.domain.sharing.SharingInteractor
import com.practicum.appplaylistmaker.domain.sharing.impl.SharingInteractorImpl

object Creator {


}