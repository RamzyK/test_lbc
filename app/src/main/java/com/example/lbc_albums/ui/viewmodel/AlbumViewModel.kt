package com.example.lbc_albums.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lbc_albums.repository.AlbumRepository
import com.example.lbc_albums.model.Albums
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class AlbumViewModel(private val albumRepository: AlbumRepository): ViewModel() {

    /**
     * Observable to notify the activity that new data is available and let it handle it
     */
    val albumsLiveData: MutableLiveData<List<Albums>> = albumRepository.albumsLiveData

    /**
     * Keeps track of whether the API call has reponded or not yet to notify the view to stop its loader animation
     */
    val stillLoading: PublishSubject<Boolean> = albumRepository.stillLoading

    /**
     * Keep track of the user's connectivity state, updated when user switches off his connectivity
     * By default at the first app usage, we consider that the user is connected
     */
    var isUserOnline = true


    init {
        getAllAlbums()
    }

    @DelicateCoroutinesApi
    fun getAllAlbums() {
        albumRepository.getAllAlbums(isUserOnline)

    }

}