package com.example.lbc_albums.repository

import com.example.lbc_albums.repository.dto.AlbumDto
import retrofit2.Call
import retrofit2.http.GET

interface AlbumService {

    @GET("img/shared/technical-test.json")
    fun getAllAlbums(): Call<List<AlbumDto>>
}