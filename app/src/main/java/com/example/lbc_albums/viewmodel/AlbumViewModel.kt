package com.example.lbc_albums.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lbc_albums.repository.AlbumRepository

import com.example.lbc_albums.repository.dao.Albums
import io.reactivex.rxjava3.subjects.PublishSubject

class AlbumViewModel(private val albumRepository: AlbumRepository): ViewModel() {

    val albumsLiveData: MutableLiveData<List<Albums>> = albumRepository.albumsLiveData

    val stillLoading: PublishSubject<Boolean> = albumRepository.stillLoading

    init {
        getAllAlbums()
    }

    fun getAllAlbums() {
        albumRepository.getAllAlbums()
    }
}