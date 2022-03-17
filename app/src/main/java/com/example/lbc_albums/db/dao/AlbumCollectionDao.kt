package com.example.lbc_albums.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lbc_albums.db.entities.AlbumEntity

/**
 * Interface to handle database management
 */
@Dao
interface AlbumCollectionDao {
    @Query("SELECT * FROM album")
    fun getAll(): List<AlbumEntity>

    @Insert
    fun insertAll(albums: ArrayList<AlbumEntity>)

    @Query ("DELETE FROM album")
    fun flushDb()
}