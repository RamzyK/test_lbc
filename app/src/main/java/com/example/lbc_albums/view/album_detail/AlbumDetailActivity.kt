package com.example.lbc_albums.view.album_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.lbc_albums.R
import com.example.lbc_albums.databinding.AlbumDetailActivityBinding
import com.example.lbc_albums.helpers.DevRule

class AlbumDetailActivity : AppCompatActivity() {

    private lateinit var binding: AlbumDetailActivityBinding

    private lateinit var albumCoverUrl: String
    private lateinit var albumTitle: String
    private var albumId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AlbumDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fillViews()
    }


    private fun fillViews() {
        getIntentData()
        val glideUrl = GlideUrl(
            albumCoverUrl,
            LazyHeaders
                .Builder()
                .addHeader("User-Agent", "default-user-agent")
                .build()
        )
        Glide.with(binding.root)
            .load(glideUrl)
            .transform(CircleCrop())
            .error(com.google.android.material.R.drawable.mtrl_ic_error)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .transform(CircleCrop())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.albumDetailCoverIv)

        this.binding.albumTitleTv.text = albumTitle
        this.title = this.getString(
            R.string.album_content_activity_title,
            albumId
        )
    }

    private fun getIntentData() {
        albumCoverUrl = intent.getStringExtra(DevRule.ALBUM_COVER_URL_KEY)!!
        albumTitle = intent.getStringExtra(DevRule.ALBUM_TITLE_KEY)!!
        albumId = intent.getIntExtra(DevRule.ALBUM_ID_KEY, 0)
    }
}