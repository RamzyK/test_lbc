package com.example.lbc_albums.ui.activities.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lbc_albums.databinding.AlbumListCellBinding
import com.example.lbc_albums.helpers.BaseAdapter
import com.example.lbc_albums.helpers.RecyclerViewClickListener
import com.example.lbc_albums.helpers.setFadeAnimation
import com.example.lbc_albums.model.Albums


/**
 * Adapter to create and manage the list of albums list
 */
class AlbumListAdapter(private val albumClickListener: RecyclerViewClickListener) :
    BaseAdapter<Albums, AlbumListAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: AlbumListCellBinding = AlbumListCellBinding.inflate(layoutInflater, parent, false)
        return AlbumViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        setFadeAnimation(holder.itemView)
        holder.bind(position + 1)
        holder.itemView.setOnClickListener {
            albumClickListener.onClickListener(position)
        }
    }

    class AlbumViewHolder(private val binding: AlbumListCellBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.albumPositionTv.text = "$position"
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}
