package com.example.lbc_albums.repository

import androidx.lifecycle.MutableLiveData
import com.example.lbc_albums.db.LocalAlbumsDb
import com.example.lbc_albums.db.entities.AlbumEntity
import com.example.lbc_albums.network.AlbumService
import com.example.lbc_albums.model.Albums
import com.example.lbc_albums.network.dto.AlbumDto
import com.example.lbc_albums.network.mappers.AlbumsMapper
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@DelicateCoroutinesApi
class AlbumRepository(
    private val albumService: AlbumService,
    private val albumMapper: AlbumsMapper,
    private val albumDb: LocalAlbumsDb
) {
    val albumsLiveData: MutableLiveData<List<Albums>> = MutableLiveData<List<Albums>>()
    val stillLoading: PublishSubject<Boolean> = PublishSubject.create()


    fun getAllAlbums(shouldCallApi: Boolean) {
        if (shouldCallApi) {
            val call: Call<List<AlbumDto>> = albumService.getAllAlbums()
            call.enqueue(object : Callback<List<AlbumDto>> {
                override fun onResponse(
                    call: Call<List<AlbumDto>>,
                    response: Response<List<AlbumDto>>
                ) {
                    response.body()?.let { albumsDto ->
                        val mappedData = albumMapper.mapDtoToModel(albumsDto)
                        if (albumsLiveData.value?.size != mappedData.size) {
                            shouldUpdateDbData(mappedData)
                        }
                        albumsLiveData.value = mappedData
                        stillLoading.onNext(false)
                    }
                }

                override fun onFailure(call: Call<List<AlbumDto>>, t: Throwable) {
                    // Handle errors
                    fetchLocalDb()
                    stillLoading.onNext(false)
                }
            })
        } else {
            fetchLocalDb()
        }
    }

    private fun shouldUpdateDbData(mappedData: List<Albums>) {
        clearAlbumDb()
        insertDataInLocalDb(mappedData)
    }


    private fun fetchLocalDb(){
        GlobalScope.launch(Dispatchers.IO) {
            val savedAlbums = albumDb.albumsDao().getAll()
            if (savedAlbums.isNotEmpty()) {
                val mappedData = albumMapper.mapDaoToModel(savedAlbums)
                albumsLiveData.postValue(mappedData)
            } else {
                albumsLiveData.postValue(emptyList())
            }
            stillLoading.onNext(false)
        }
    }


    private fun insertDataInLocalDb(albums: List<Albums>){
        val albumsEntities: ArrayList<AlbumEntity> = arrayListOf()
        albums.map {
            Pair(it.id, it.albumContent)
        }.map { (albumId, albumContent) ->
            albumContent.forEach {
                albumsEntities.add(
                    AlbumEntity(
                        uid = null,
                        albumId = albumId,
                        id = it.id,
                        title = it.albumInformation.title,
                        url = it.albumInformation.url,
                        thumbnailUrl = it.albumInformation.thumbnailUrl
                    )
                )
            }
        }
        GlobalScope.launch(Dispatchers.IO) {
            albumDb.albumsDao()
                .insertAll(
                    albumsEntities
                )
        }
    }

    private fun clearAlbumDb(){
        GlobalScope.launch(Dispatchers.IO) {
            albumDb.albumsDao().flushDb()
        }
    }
}