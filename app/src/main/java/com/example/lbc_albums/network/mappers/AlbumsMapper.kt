package com.example.lbc_albums.network.mappers

import com.example.lbc_albums.db.entities.AlbumEntity
import com.example.lbc_albums.model.Album
import com.example.lbc_albums.model.AlbumInfo
import com.example.lbc_albums.model.Albums
import com.example.lbc_albums.network.dto.AlbumDto

class AlbumsMapper : Mapper<List<AlbumDto>, List<Albums>> {

    /**
     * Function to map the JSON data to a list of Albums model
     *
     * @param input: List of all the DTO get from the api call from the repository
     * @return List of Albums objects which compile albums with the same albumId
     */
    override fun mapDtoToModel(input: List<AlbumDto>): List<Albums> = with(input) {
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

    /**
     * Map data from db to a list of Albums model
     *
     * @param input: List of Album entities saved in db
     * @return List of Albums model objects
     */
    override fun mapDaoToModel(input: List<AlbumEntity>): List<Albums> {
        val inputMappedToDtos = input.map {
            AlbumDto(
               it.albumId,
               it.id,
               it.title,
               it.url,
               it.thumbnailUrl
            )
        }
        return mapDtoToModel(inputMappedToDtos)
    }
}

/**
 * Interface to help the mapping objects
 */
interface Mapper<in I, out O> {
    fun mapDtoToModel(input: I): O
    fun mapDaoToModel(input: List<AlbumEntity>): O
}