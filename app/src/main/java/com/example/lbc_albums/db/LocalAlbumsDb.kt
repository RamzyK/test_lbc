package com.example.lbc_albums.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lbc_albums.db.dao.AlbumCollectionDao
import com.example.lbc_albums.db.entities.AlbumEntity

@Database(entities = [AlbumEntity::class], version = 1)
abstract class LocalAlbumsDb : RoomDatabase() {
    abstract fun albumsDao(): AlbumCollectionDao

    companion object {
        private var INSTANCE: LocalAlbumsDb? = null
        fun getInstance(context: Context): LocalAlbumsDb {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    LocalAlbumsDb::class.java,
                    "album"
                ).build()
            }
            return INSTANCE!!
        }

    }
}