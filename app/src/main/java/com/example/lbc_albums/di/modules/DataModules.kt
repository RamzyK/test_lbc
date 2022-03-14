package com.example.lbc_albums.di.modules

import com.example.lbc_albums.db.LocalAlbumsDb
import com.example.lbc_albums.repository.AlbumRepository
import com.example.lbc_albums.network.AlbumService
import com.example.lbc_albums.network.mappers.AlbumsMapper
import com.example.lbc_albums.ui.viewmodel.AlbumViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin DI module.
 * Set up the view models of the app.
 */
@DelicateCoroutinesApi
val coreModules = module {
    // Instantiate ws
    single { createWebService<AlbumService>(get()) }
    // instantiate viewModels which can be retrieved in appCompatActivities, Fragments and Services
    viewModel { AlbumViewModel(get()) }
    // Instantiate Db
    single { LocalAlbumsDb.getInstance(get()) }
    // Instantiate data mapper
    single { AlbumsMapper() }
    // Instantiate Album repository
    single { AlbumRepository(get(), get(), get()) }
}