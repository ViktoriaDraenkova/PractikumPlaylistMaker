package com.practicum.appplaylistmaker.di

import android.media.MediaPlayer
import androidx.activity.ComponentActivity
import com.practicum.appplaylistmaker.PRACTICUM_EXAMPLE_PREFERENCES
import com.practicum.appplaylistmaker.api.ITunesApi
import com.practicum.appplaylistmaker.data.audioplayer.Audioplayer
import com.practicum.appplaylistmaker.data.audioplayer.impl.AudioplayerImpl
import com.practicum.appplaylistmaker.data.sharing.ExternalNavigation
import com.practicum.appplaylistmaker.data.sharing.impl.ExternalNavigationImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val dataModule = module {

    single {
        MediaPlayer()
    }

    single<Audioplayer>{
        AudioplayerImpl(get())
    }

    single {
        androidContext().getSharedPreferences(
            PRACTICUM_EXAMPLE_PREFERENCES,
            ComponentActivity.MODE_PRIVATE
        )
    }

    single {
        Retrofit.Builder().baseUrl("https://itunes.apple.com").addConverterFactory(
            GsonConverterFactory.create()
        )
            .build()
            .create(ITunesApi::class.java)
    }

    single<ExternalNavigation> {
        ExternalNavigationImpl(get())
    }


}