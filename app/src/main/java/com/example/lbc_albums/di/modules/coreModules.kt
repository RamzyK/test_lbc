package com.example.lbc_albums.di.modules

import com.example.lbc_albums.di.createWebService
import com.example.lbc_albums.repository.AlbumRepository
import com.example.lbc_albums.repository.AlbumService
import com.example.lbc_albums.repository.mappers.AlbumsMapper
import com.example.lbc_albums.viewmodel.AlbumViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin DI module.
 * Set up the viewmodels of the app.
 */
val coreModules = module {

    single { createWebService<AlbumService>(get()) }

    // instantiate viewModels which can be retrieved in appCompatActivities, Fragments and Services
    viewModel { AlbumViewModel(get()) }

    single { AlbumsMapper()}
    single { AlbumRepository(get(), get()) }
}