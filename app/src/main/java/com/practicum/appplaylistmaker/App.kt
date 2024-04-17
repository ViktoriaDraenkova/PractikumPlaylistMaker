package com.practicum.appplaylistmaker

import android.app.Application

import androidx.appcompat.app.AppCompatDelegate
import com.practicum.appplaylistmaker.di.appModule
import com.practicum.appplaylistmaker.di.dataModule
import com.practicum.appplaylistmaker.di.interactorModule
import com.practicum.appplaylistmaker.di.repositoryModule
import com.practicum.appplaylistmaker.domain.settings.SettingsInteractor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.android.ext.android.inject

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            modules(listOf( dataModule, repositoryModule, interactorModule, appModule ))

        }
    }


}
 