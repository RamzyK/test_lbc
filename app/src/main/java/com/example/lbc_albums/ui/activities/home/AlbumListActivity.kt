package com.example.lbc_albums.ui.activities.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lbc_albums.databinding.AlbumListActivityBinding
import com.example.lbc_albums.helpers.DevRule.ALBUM_CONTENT_POSITION_KEY
import com.example.lbc_albums.helpers.DevRule.ALBUM_GRID_COLONES
import com.example.lbc_albums.helpers.RecyclerViewClickListener
import com.example.lbc_albums.ui.activities.album_content.AlbumContentActivity
import com.example.lbc_albums.ui.viewmodel.AlbumViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@DelicateCoroutinesApi
class AlbumListActivity : AppCompatActivity(), RecyclerViewClickListener {
    /**
     * ViewModel that handles the logic of the app
     */
    private val  albumViewModel: AlbumViewModel by viewModel()

    /**
     * Generated file that binds the XML views to the activity
     */
    private lateinit var binding: AlbumListActivityBinding

    /**
     * Adapter linked to the album's recycler view
     */
    private lateinit var  albumAdapter: AlbumListAdapter
    private val disposeBag = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AlbumListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.setUpViews()
        this.observeAlbumData()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.dispose()
    }

    /**
     * Function to set up the views of the activity
     */
    private fun setUpViews() {
        val layoutManager =
            GridLayoutManager(
                this,
                ALBUM_GRID_COLONES,
                GridLayoutManager.VERTICAL,
                false
            )
        this.albumAdapter = AlbumListAdapter(this)
        this.binding.albumsRv.adapter = albumAdapter
        this.binding.albumsRv.layoutManager = layoutManager
    }

    /**
     * Observe album data list update events, then handles the displaying of items in the
     * RecyclerView
     */
    private fun observeAlbumData() {
        this.albumViewModel.albumsLiveData.observe(this){
            this.albumAdapter.submitList(it)
        }

        this.albumViewModel.stillLoading.subscribe { finished ->
            this.binding.swipeRefreshLayout.isRefreshing = finished
        }.let(disposeBag::add)

        this.binding.swipeRefreshLayout.setOnRefreshListener {
            this.albumViewModel.getAllAlbums()
        }
    }

    /**
     * Handle click on an album cell
     */
    override fun onClickListener(position: Int) {
        Intent(
            this,
            AlbumContentActivity::class.java
        ).also {
            it.putExtra(
                ALBUM_CONTENT_POSITION_KEY,
                position
            )
            startActivity(it)
        }
    }
}

