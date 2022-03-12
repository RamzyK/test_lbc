package com.example.lbc_albums.repository.mappers

import com.example.lbc_albums.repository.dao.Album
import com.example.lbc_albums.repository.dao.AlbumInfo
import com.example.lbc_albums.repository.dao.Albums
import com.example.lbc_albums.repository.dto.AlbumDto

class AlbumsMapper : Mapper<List<AlbumDto>, List<Albums>> {

    /**
     * Function to map the JSON data to a much more usable format
     *
     * @param input: List of all the DTO get from the api call from the repository
     * @return List of Albums objects which compile albums with the same albumId
     */
    override fun map(input: List<AlbumDto>): List<Albums> = with(input) {
        val albums: MutableList<Albums> = mutableListOf()
        var albumContentList: MutableList<Album> = mutableListOf()
        var albumId = this.first().albumId
        for (albumDto in this) {
            if(albumDto.albumId != albumId){
                val newAlbum = Albums(albumId, albumContentList)
                albums.add( newAlbum)
                albumId = albumDto.albumId
                albumContentList = mutableListOf()
            }
            if(albumDto.albumId == albumId){
                val albumInfo = AlbumInfo(
                    albumDto.title,
                    albumDto.url,
                    albumDto.thumbnailUrl,
                )
                albumContentList.add(
                    Album(
                        albumDto.id,
                        albumInfo
                    )
                )
                if(albumDto.id == this.last().id) {
                    val newAlbum = Albums(albumId, albumContentList)
                    albums.add(newAlbum)
                }
            }
        }
        albums
    }
}

/**
 * Interface to help the mapping between DTOs and app objects
 */
interface Mapper<in I, out O> {
    fun map(input: I): O
}