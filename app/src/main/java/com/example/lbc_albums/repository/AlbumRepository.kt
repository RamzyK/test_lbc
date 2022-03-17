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

/**
 * Class to handle the networking of the app. Here we do http calls
 * and db management
 *
 * @param albumService: Instance of the retrofit interface to get data
 * @param albumMapper: Instance of album mapper class to mapp data from a type to another
 * @param albumDb: Instance of the local room db to save data
 */
@DelicateCoroutinesApi
class AlbumRepository(
    private val albumService: AlbumService,
    private val albumMapper: AlbumsMapper,
    private val albumDb: LocalAlbumsDb
) {
    /**
     * List of Albums model observed by the VM in order to update the view
     */
    val albumsLiveData: MutableLiveData<List<Albums>> = MutableLiveData<List<Albums>>()

    /**
     * Track the status of the http call
     */
    val stillLoading: PublishSubject<Boolean> = PublishSubject.create()


    /**
     * Retofit http call to get data from endpoint
     */
    fun getAllAlbums(shouldCallApi: Boolean) {
        if (shouldCallApi) {
            val call: Call<List<AlbumDto>> = albumService.getAllAlbums()
            call.enqueue(object : Callback<List<AlbumDto>> {
                override fun onResponse(
                    call: Call<List<AlbumDto>>,
                    response: Response<List<AlbumDto>>
                ) {
                    response.body()?.filter {
                        albumMapper.isAlbumJsonDataValid(it)
                    }?.let { albumsDto ->
                        val mappedData = albumMapper.mapDtoToModel(albumsDto)
                        updateDbData(mappedData)

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

    /**
     * Funtion called when db needs to be update e.g when new data are getting in from the api,
     * data needs to be synced locally to always provide fresh data
     *
     * @param mappedData: List of mapped data that will we insert in the local db
     */
    private fun updateDbData(mappedData: List<Albums>) {
        clearAlbumDb()
        insertDataInLocalDb(mappedData)
    }


    /**
     * Function to get data locally saved in db
     */
    private fun fetchLocalDb() {
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


    /**
     * Function called to insert fresh data in local db
     *
     * @param albums: List of mapped data that will we insert in the local db
     */
    private fun insertDataInLocalDb(albums: List<Albums>) {
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

    /**
     * Function called to clear local db in order to insert fresh data
     */
    private fun clearAlbumDb() {
        GlobalScope.launch(Dispatchers.IO) {
            albumDb.albumsDao().flushDb()
        }
    }
}