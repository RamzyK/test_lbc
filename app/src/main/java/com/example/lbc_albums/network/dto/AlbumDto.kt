package com.example.lbc_albums.network.dto

import com.google.gson.annotations.SerializedName

/**
 * Object that is mapped from json response get from HTTP call
 *
 * @param albumId: Unique id of each group of albums
 * @param id: Unique id of each album
 * @param title: title of the album
 * @param url: Url to get album big image
 * @param thumbnailUrl: Url to get album thumbnail image
 */
class AlbumDto(
    @field:SerializedName("albumId")
    var albumId: Int?,
    @field:SerializedName("id")
    var id: Int?,
    @field:SerializedName("title")
    var title: String?,
    @field:SerializedName("url")
    var url: String?,
    @field:SerializedName("thumbnailUrl")
    var thumbnailUrl: String?
)