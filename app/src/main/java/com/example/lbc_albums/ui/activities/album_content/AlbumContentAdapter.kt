package com.example.lbc_albums.ui.activities.album_content


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.lbc_albums.R
import com.example.lbc_albums.databinding.AlbumContentListCellBinding
import com.example.lbc_albums.helpers.BaseAdapter
import com.example.lbc_albums.helpers.RecyclerViewClickListener
import com.example.lbc_albums.helpers.setFadeAnimation
import com.example.lbc_albums.model.AlbumContent


/**
 * Adapter to create and manage the list of albums list
 */
class AlbumContentAdapter(private val albumClickListener: RecyclerViewClickListener) :
    BaseAdapter<AlbumContent, AlbumContentAdapter.AlbumContentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumContentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: AlbumContentListCellBinding = AlbumContentListCellBinding.inflate(layoutInflater, parent, false)
        return AlbumContentViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AlbumContentViewHolder, position: Int) {
        setFadeAnimation(holder.itemView)
        val currentItem = currentList[position]
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            albumClickListener.onClickListener(position)
        }
    }



    class AlbumContentViewHolder(private val binding: AlbumContentListCellBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AlbumContent) {
            binding.albumContentTitleTv.text = item.albumInformation.title

            val glideUrl = GlideUrl(
                item.albumInformation.thumbnailUrl,
                LazyHeaders
                    .Builder()
                    .addHeader("User-Agent", "default-user-agent")
                    .build()
            )
            Glide.with(binding.root)
                .load(glideUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) //3
                .transform(CircleCrop()) //4
                .into(binding.albumContentThumbnailIv)

        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}

