package repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.lbc_albums.db.LocalAlbumsDb
import com.example.lbc_albums.db.dao.AlbumCollectionDao
import com.example.lbc_albums.db.entities.AlbumEntity
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException


class AlbumRepositoryTest {

    private lateinit var albumDao: AlbumCollectionDao
    private lateinit var albumDb: LocalAlbumsDb

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        albumDb = Room.inMemoryDatabaseBuilder(
            context, LocalAlbumsDb::class.java
        ).build()
        albumDao = albumDb.albumsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        albumDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun flushDbTest() {
        // Arrange
        val albumToSave = AlbumEntity(
            1,
            albumId = 1,
            id = 1,
            "reprehenderit est deserunt velit ipsam",
            "https://via.placeholder.com/600/771796",
            "https://via.placeholder.com/150/771796"
        )
        albumDao.insertAll(arrayListOf(albumToSave))
        val albumsInDbCountBefore = albumDao.getAll().size

        // Act
        albumDao.flushDb()
        val albumsInDbCountAfter = albumDao.getAll().size

        // Assert
        assertThat(albumsInDbCountBefore).isEqualTo(1)
        assertThat(albumsInDbCountAfter).isEqualTo(0)
    }

    @Test
    @Throws(Exception::class)
    fun insertOneAlbumInDbTest() {
        // Arrange
        val albumToSave = AlbumEntity(
            1,
            albumId = 1,
            id = 1,
            "reprehenderit est deserunt velit ipsam",
            "https://via.placeholder.com/600/771796",
            "https://via.placeholder.com/150/771796"
        )
        val albumsCountBefore = albumDao.getAll().size

        // Act
        albumDao.insertAll(arrayListOf(albumToSave))
        val albumsCoutAfter = albumDao.getAll().size

        // Assert
        assertThat(albumsCoutAfter - albumsCountBefore).isEqualTo(1)
        albumDao.flushDb()
    }

    @Test
    @Throws(Exception::class)
    fun insertManyAlbumsInDbTest() {
        // Arrange
        val albumsToSave = arrayListOf(
            AlbumEntity(
                1,
                albumId = 1,
                id = 1,
                "reprehenderit est deserunt velit ipsam",
                "https://via.placeholder.com/600/771796",
                "https://via.placeholder.com/150/771796"
            ),
            AlbumEntity(
                2,
                albumId = 2,
                id = 2,
                "reprehenderit est deserunt velit ipsam",
                "https://via.placeholder.com/600/771796",
                "https://via.placeholder.com/150/771796"
            ),
            AlbumEntity(
                3,
                albumId = 3,
                id = 3,
                "reprehenderit est deserunt velit ipsam",
                "https://via.placeholder.com/600/771796",
                "https://via.placeholder.com/150/771796"
            ),
        )
        val sizeOftheListToInsert = albumsToSave.size
        val albumsCountBefore = albumDao.getAll().size

        // Act
        albumDao.insertAll(albumsToSave)
        val albumsCoutAfter = albumDao.getAll().size

        // Assert
        assertThat(albumsCoutAfter - albumsCountBefore).isEqualTo(sizeOftheListToInsert)
        albumDao.flushDb()
    }

    @Test
    @Throws(Exception::class)
    fun getAllAlbumsInDbTest() {
        // Arrange
        insertAlbumsInDb(generateFakeAlbumsEntities())

        // Act
        val savedAlbums = albumDao.getAll()
        val savedAlbumsCount = savedAlbums.size

        // Assert
        assertThat(savedAlbumsCount).isEqualTo(getInsertedAlbumsInDb().size)
    }


    private fun insertAlbumsInDb(albums: ArrayList<AlbumEntity>) {
        albumDao.insertAll(albums)
    }

    private fun getInsertedAlbumsInDb(): List<AlbumEntity> {
        return albumDao.getAll()
    }

    private fun generateFakeAlbumsEntities(): ArrayList<AlbumEntity> {
        return arrayListOf(
            AlbumEntity(
                1,
                albumId = 1,
                id = 1,
                "reprehenderit est deserunt velit ipsam",
                "https://via.placeholder.com/600/771796",
                "https://via.placeholder.com/150/771796"
            ),
            AlbumEntity(
                2,
                albumId = 2,
                id = 2,
                title = "accusamus beatae ad facilis cum similique qui sunt",
                url = "https://via.placeholder.com/600/92c952",
                thumbnailUrl = "https://via.placeholder.com/150/92c952"
            ),
            AlbumEntity(
                3,
                albumId = 3,
                id = 3,
                title = "accusamus beatae ad facilis cum similique qui sunt",
                url = "https://via.placeholder.com/600/92c952",
                thumbnailUrl = "https://via.placeholder.com/150/92c952"
            )
        )
    }
}