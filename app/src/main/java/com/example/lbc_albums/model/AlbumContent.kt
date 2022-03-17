package com.example.lbc_albums.model

/**
 * Album model
 *
 * @param id: Unique id of a group of albums
 * @param albumContent: List of albums that have the same albumId
 */
data class Albums(
    val id: Int,
    val albumContent: MutableList<AlbumContent>
)

/**
 * Album content model
 *
 * @param id: Unique id for each album
 * @param albumInformation: Album information
 */
data class AlbumContent(
    val id: Int,
    val albumInformation: AlbumInfo
)

/**
 * Album information model
 */
data class AlbumInfo(
    val title: String,
    val url: String,
    val thumbnailUrl: String
)
