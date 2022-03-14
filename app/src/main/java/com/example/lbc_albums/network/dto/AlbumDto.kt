package com.example.lbc_albums.network.dto

import com.google.gson.annotations.SerializedName


class AlbumDto(
    @field:SerializedName("albumId")
    var albumId: Int,
    @field:SerializedName("id")
    var id: Int,
    @field:SerializedName("title")
    var title: String,
    @field:SerializedName("url")
    var url: String,
    @field:SerializedName("thumbnailUrl")
    var thumbnailUrl: String
)