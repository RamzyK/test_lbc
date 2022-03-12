package com.example.lbc_albums.initializer

import com.example.lbc_albums.di.modules.coreModules
import com.example.lbc_albums.di.remoteModules
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException


fun initModules() {
    try {
        startKoin {
            coreModules
            remoteModules
        }
    } catch (alreadyStart: KoinAppAlreadyStartedException) {
        loadKoinModules(
            mutableListOf(remoteModules, coreModules)
        )
    }

}
