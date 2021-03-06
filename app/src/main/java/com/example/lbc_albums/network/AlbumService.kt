package com.example.lbc_albums.network

import com.example.lbc_albums.network.dto.AlbumDto
import retrofit2.Call
import retrofit2.http.GET

/**
 * Interface to get data from the api
 */
interface AlbumService {

    @GET("img/shared/technical-test.json")
    fun getAllAlbums(): Call<List<AlbumDto>>
}