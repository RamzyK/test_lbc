package com.example.lbc_albums.repository

import androidx.lifecycle.MutableLiveData
import com.example.lbc_albums.repository.dao.Albums
import com.example.lbc_albums.repository.dto.AlbumDto
import com.example.lbc_albums.repository.mappers.AlbumsMapper
import io.reactivex.rxjava3.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumRepository(
    private val albumService: AlbumService,
    private val albumMapper: AlbumsMapper
){
    val albumsLiveData: MutableLiveData<List<Albums>> = MutableLiveData()

    val stillLoading = PublishSubject.create<Boolean>()

    fun getAllAlbums() {
        val call: Call<List<AlbumDto>> = albumService.getAllAlbums()
        call.enqueue(object : Callback<List<AlbumDto>> {
            override fun onResponse(
                call: Call<List<AlbumDto>>,
                response: Response<List<AlbumDto>>
            ) {
                stillLoading.onNext(false)
                response.body()?.let { albumsDto ->
                    albumsLiveData.value = albumMapper.map(albumsDto)
                }
            }

            override fun onFailure(call: Call<List<AlbumDto>>, t: Throwable) {
                // Handle errors
                stillLoading.onNext(false)
            }
        })
    }
}