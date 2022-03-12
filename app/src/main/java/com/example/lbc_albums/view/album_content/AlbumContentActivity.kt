package com.example.lbc_albums.view.album_content

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lbc_albums.R
import com.example.lbc_albums.databinding.AlbumContentActivityBinding
import com.example.lbc_albums.helpers.DevRule.ALBUM_CONTENT_POSITION_KEY
import com.example.lbc_albums.helpers.DevRule.ALBUM_COVER_URL_KEY
import com.example.lbc_albums.helpers.DevRule.ALBUM_ID_KEY
import com.example.lbc_albums.helpers.DevRule.ALBUM_TITLE_KEY
import com.example.lbc_albums.helpers.RecyclerViewClickListener
import com.example.lbc_albums.repository.dao.Albums
import com.example.lbc_albums.view.album_detail.AlbumDetailActivity
import com.example.lbc_albums.viewmodel.AlbumViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumContentActivity : AppCompatActivity(), RecyclerViewClickListener {
    private val  albumViewModel: AlbumViewModel by viewModel()
    /**
     * Generated file that binds the XML views to the activity
     */
    private lateinit var binding: AlbumContentActivityBinding

    private lateinit var albumContentAdapter: AlbumContentAdapter
    private lateinit var albumContentList : Albums

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AlbumContentActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
        fillViews()
    }

    private fun setUpViews() {
        val layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        this.albumContentAdapter = AlbumContentAdapter(this)
        this.binding.albumContentRv.adapter = albumContentAdapter
        this.binding.albumContentRv.layoutManager = layoutManager
    }

    /**
     * Get position the
     */
    private fun getDataFromIntent(): Int{
        val albumContentId = intent.getIntExtra(ALBUM_CONTENT_POSITION_KEY, 0)
        this.title = this.getString(
            R.string.album_content_activity_title,
            albumContentId + 1
        )
        return albumContentId
    }

    private fun fillViews() {
        getAlbumContentList()
        albumContentAdapter.submitList(albumContentList.albumContent)
    }

    override fun onClickListener(position: Int) {
        val albumContent = albumContentList.albumContent.get(position)
        Intent(
            this,
            AlbumDetailActivity::class.java
        ).also {
            it.putExtra(
                ALBUM_COVER_URL_KEY,
                albumContent.albumInformation.url
            )
            it.putExtra(
                ALBUM_TITLE_KEY,
                albumContent.albumInformation.title
            )
            it.putExtra(
                ALBUM_ID_KEY,
                albumContent.id
            )
            startActivity(it)
        }
    }

    private fun getAlbumContentList() {
        val albumContentPosition = getDataFromIntent()
        val albumsList = albumViewModel.albumsLiveData.value!!
        albumContentList = albumsList[albumContentPosition]
    }
}
