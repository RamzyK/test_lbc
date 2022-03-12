package com.example.lbc_albums.di

import android.content.Context
import androidx.startup.Initializer
import com.example.lbc_albums.di.modules.coreModules

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class DependencyInjectorInitializer: Initializer<Unit> {
    override fun create(context: Context) {
        startKoin {
            androidContext(context)
            loadKoinModules(mutableListOf(remoteModules, coreModules))
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}