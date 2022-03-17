package com.example.lbc_albums.helpers

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Represents constants that are defined by developers to make the project run well.
 */
internal object DevRule {
    /**
     * Base url used by retrofit instance to get data
     */
    const val API_BASE_URL = "https://static.leboncoin.fr/"

    /**
     * Constants used by configuration of okhttp instance to handle timeouts
     */
    const val CONNECTION_TIMEOUT_VALUE = 2000L
    const val WRITE_TIMEOUT_VALUE = 3000L
    const val READ_TIMEOUT_VALUE = 3000L

    /**
     * Constant to change home grid view number of colones
     */
    const val ALBUM_GRID_COLONES = 3

    /**
     * Constant string to pass data in the intent between AlbumListActivity & AlbumContentActivity
     */
    const val ALBUM_CONTENT_POSITION_KEY = "ALBUM_CONTENT_POSITION_KEY"

    /**
     * Constant string to pass data in the intent between AlbumContentActivity & AlbumDetailActivity
     */
    const val ALBUM_COVER_URL_KEY = "ALBUM_COVER_URL_KEY"

    /**
     * Constant string to pass data in the intent between AlbumContentActivity & AlbumDetailActivity
     */
    const val ALBUM_TITLE_KEY = "ALBUM_TITLE_KEY"

    /**
     * Constant string to pass data in the intent between AlbumContentActivity & AlbumDetailActivity
     */
    const val ALBUM_ID_KEY = "ALBUM_ID_KEY"
}

/**
 * To optimize changes on adapter.
 * Change only what need to change
 */
abstract class BaseAdapter<T: Any, VH : RecyclerView.ViewHolder>: ListAdapter<T, VH>(BaseItemCallback<T>())


/**
 * Generic diff util item callback used in both AlbumListActivity and AlbumContentActivity recyclerviews
 */
@SuppressLint("DiffUtilEquals")
class BaseItemCallback<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.toString() == newItem.toString()

    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
}

/**
 * Interface to handle click used between a recycler view and its adapter
 */
interface RecyclerViewClickListener {
    fun onClickListener(position: Int)
}