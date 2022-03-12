package com.example.lbc_albums.repository.dao

data class Albums(
    val id: Int,
    val albumContent: MutableList<Album>
)

data class Album(
    val id: Int,
    val albumInformation: AlbumInfo
)

data class AlbumInfo(
    val title: String,
    val url: String,
    val thumbnailUrl: String
)
