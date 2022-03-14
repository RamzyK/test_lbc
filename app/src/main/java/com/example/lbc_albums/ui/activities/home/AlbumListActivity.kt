package com.example.lbc_albums.ui.activities.home

import android.content.Intent
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import android.view.View
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
        this.observeUserConnectivity()
        this.observeAlbumData()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.dispose()
    }


    /**
     * Observe user's network connectivity and updates the field 'isuserOnline' in the view model
     * So when user turns off the network connectivity, we won't do useless networks calls
     */
    private fun observeUserConnectivity() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager
            .requestNetwork(
                networkRequest,
                object : NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        albumViewModel.isUserOnline = true
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        albumViewModel.isUserOnline = false
                    }
                }
            )
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
        this.binding.emptyListLinearView.visibility = View.INVISIBLE
    }

    /**
     * Observe album data list update events, then handles the displaying of items in the
     * RecyclerView
     */
    private fun observeAlbumData() {
        this.albumViewModel.albumsLiveData.observe(this){ albums ->
            if (albums.isNotEmpty()) {
                this.albumAdapter.submitList(albums)
                this.updateEmptyListLayerVisibility(newVisibility = View.INVISIBLE)
            } else {
                this.updateEmptyListLayerVisibility(newVisibility = View.VISIBLE)
            }
        }

        this.albumViewModel.stillLoading.subscribe { finished ->
            this.binding.swipeRefreshLayout.isRefreshing = finished
        }.let(disposeBag::add)

        this.binding.swipeRefreshLayout.setOnRefreshListener {
            this.albumViewModel.getAllAlbums()
        }
    }

    /**
     * Shows a layer with an image view and a text when no data can be displayed to the user
     *
     * @param newVisibility: New visibility given to the 'emptyListLinearView' layer
     */
    private fun updateEmptyListLayerVisibility(newVisibility: Int){
        this.binding.emptyListLinearView.visibility = newVisibility
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

