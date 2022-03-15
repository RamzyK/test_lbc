package com.example.lbc_albums.network.mappers

import com.example.lbc_albums.db.entities.AlbumEntity
import com.example.lbc_albums.network.dto.AlbumDto
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat


class AlbumsMapperTest {

    private lateinit var inputDto: List<AlbumDto>
    private lateinit var inputDao: List<AlbumEntity>
    private lateinit var albumMapper: AlbumsMapper

    @Before
    fun setUp() {
        inputDto = generateFakeJsonResponseCorrectlyFormed()
        inputDao = generateFakeDbAlbumEntities()
        albumMapper = AlbumsMapper()
    }

    @Test
    fun `list of album dto should be transformed into a list of albums model object of the same size`() {
        val inputListSize = inputDto.size
        val dtoMappedToModel = albumMapper.mapDtoToModel(inputDto)

        assertThat(inputListSize).isEqualTo(dtoMappedToModel.size)
    }

    @Test
    fun `list of album entities should be transformed into a list of albums model object of the same size`() {
        val inputListSize = inputDao.size
        val daoMappedToModel = albumMapper.mapDaoToModel(inputDao)

        assertThat(inputListSize).isEqualTo(daoMappedToModel.size)
    }

    @Test
    fun `list of album dto should be transformed into a list of albums model object`() {
        val inputListSize = inputDto.size
        val dtoMappedToModel = albumMapper.mapDtoToModel(inputDto)

        assertThat(inputListSize).isEqualTo(dtoMappedToModel.size)
    }


    @Test
    fun `album object with null album id should not be mapped`() {
        val albumWithNullId = generateFakeAlbumDto(
            null,
            1,
            "accusamus beatae ad facilis cum similique qui sunt",
            "https://via.placeholder.com/600/92c952",
            "https://via.placeholder.com/150/92c952"
        )

        val isMapped = albumMapper.isAlbumJsonDataValid(albumWithNullId)
        assertThat(isMapped).isFalse()
    }

    @Test
    fun `album object with null albumId should not be mapped`() {
        val albumWithNullAlbumId = generateFakeAlbumDto(
            1,
            null,
            "accusamus beatae ad facilis cum similique qui sunt",
            "https://via.placeholder.com/600/92c952",
            "https://via.placeholder.com/150/92c952"
        )

        val isMapped = albumMapper.isAlbumJsonDataValid(albumWithNullAlbumId)
        assertThat(isMapped).isFalse()
    }

    @Test
    fun `album object with null or blank album title should not be mapped`() {
        val albumWithNullAlbumTitle = generateFakeAlbumDto(
            1,
            1,
            null,
            "https://via.placeholder.com/600/92c952",
            "https://via.placeholder.com/150/92c952"
        )

        val albumWithBlankAlbumTitle = generateFakeAlbumDto(
            1,
            1,
            "",
            "https://via.placeholder.com/600/92c952",
            "https://via.placeholder.com/150/92c952"
        )


        var isMapped = albumMapper.isAlbumJsonDataValid(albumWithNullAlbumTitle)
        assertThat(isMapped).isFalse()

        isMapped = albumMapper.isAlbumJsonDataValid(albumWithBlankAlbumTitle)
        assertThat(isMapped).isFalse()
    }

    @Test
    fun `album object with null or blank album url should not be mapped`() {
        val albumWithNullAlbumUrl = generateFakeAlbumDto(
            1,
            1,
            "accusamus beatae ad facilis cum similique qui sunt",
            null,
            "https://via.placeholder.com/150/92c952"
        )

        val albumWithBlankAlbumUrl = generateFakeAlbumDto(
            1,
            1,
            "accusamus beatae ad facilis cum similique qui sunt",
            "",
            "https://via.placeholder.com/150/92c952"
        )


        var isMapped = albumMapper.isAlbumJsonDataValid(albumWithNullAlbumUrl)
        assertThat(isMapped).isFalse()

        isMapped = albumMapper.isAlbumJsonDataValid(albumWithBlankAlbumUrl)
        assertThat(isMapped).isFalse()
    }

    @Test
    fun `album object with null album thumbnail url should not be mapped`() {
        val albumWithNullAlbumUrlThumbnail = generateFakeAlbumDto(
            1,
            1,
            "accusamus beatae ad facilis cum similique qui sunt",
            "https://via.placeholder.com/600/92c952",
            null
        )

        val albumWithBlankAlbumUrlThumbnail = generateFakeAlbumDto(
            1,
            1,
            "accusamus beatae ad facilis cum similique qui sunt",
            "https://via.placeholder.com/600/92c952",
            ""
        )


        var isMapped = albumMapper.isAlbumJsonDataValid(albumWithNullAlbumUrlThumbnail)
        assertThat(isMapped).isFalse()

        isMapped = albumMapper.isAlbumJsonDataValid(albumWithBlankAlbumUrlThumbnail)
        assertThat(isMapped).isFalse()
    }

    private fun generateFakeJsonResponseCorrectlyFormed(): List<AlbumDto> {
        return listOf(
            generateFakeAlbumDto(
                1,
                1,
                "accusamus beatae ad facilis cum similique qui sunt",
                "https://via.placeholder.com/600/92c952",
                "https://via.placeholder.com/150/92c952"
            ),
            generateFakeAlbumDto(
                2,
                2,
                "reprehenderit est deserunt velit ipsam",
                "https://via.placeholder.com/600/771796",
                "https://via.placeholder.com/150/771796"
            )
        )
    }

    private fun generateFakeDbAlbumEntities(): List<AlbumEntity> {
        return listOf(
            generateFakeAlbumDao(
                1,
                1,
                "accusamus beatae ad facilis cum similique qui sunt",
                "https://via.placeholder.com/600/92c952",
                "https://via.placeholder.com/150/92c952"
            ),
            generateFakeAlbumDao(
                2,
                2,
                "reprehenderit est deserunt velit ipsam",
                "https://via.placeholder.com/600/771796",
                "https://via.placeholder.com/150/771796"
            )
        )
    }

    private fun generateFakeAlbumDto(albumId: Int?,
                                     id: Int?,
                                     title: String?,
                                     url: String?,
                                     thumbnailUrl: String?
    ): AlbumDto {
        return AlbumDto(
            albumId = albumId,
            id = id,
            title = title,
            url = url,
            thumbnailUrl = thumbnailUrl
        )
    }

    private fun generateFakeAlbumDao(albumId: Int,
                                     id: Int,
                                     title: String,
                                     url: String,
                                     thumbnailUrl: String
    ): AlbumEntity {
        return AlbumEntity(
            1,
            albumId = albumId,
            id = id,
            title = title,
            url = url,
            thumbnailUrl = thumbnailUrl
        )
    }
}