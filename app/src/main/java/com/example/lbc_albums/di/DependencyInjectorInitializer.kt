package com.example.lbc_albums.di

import android.content.Context
import androidx.startup.Initializer
import com.example.lbc_albums.di.modules.coreModules
import com.example.lbc_albums.di.modules.remoteModules
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

/**
 * DI initializer called to instantiate modules
 */
@DelicateCoroutinesApi
class DependencyInjectorInitializer: Initializer<Unit> {
    override fun create(context: Context) {
        startKoin {
            androidContext(context)
            loadKoinModules(mutableListOf(remoteModules, coreModules))
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}