package com.example.lbc_albums.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lbc_albums.repository.AlbumRepository
import com.example.lbc_albums.model.Albums
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class AlbumViewModel(private val albumRepository: AlbumRepository): ViewModel() {

    val albumsLiveData: MutableLiveData<List<Albums>> = albumRepository.albumsLiveData

    val stillLoading: PublishSubject<Boolean> = albumRepository.stillLoading


    init {
        getAllAlbums()
    }
    @DelicateCoroutinesApi
    fun getAllAlbums() {
        albumRepository.getAllAlbums()

    }

}