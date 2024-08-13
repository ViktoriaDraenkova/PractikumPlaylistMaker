package com.practicum.appplaylistmaker.ui.media.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.practicum.appplaylistmaker.R
import com.practicum.appplaylistmaker.databinding.PlaylistsFragmentBinding
import com.practicum.appplaylistmaker.domain.models.Playlist
import com.practicum.appplaylistmaker.getNavigationResultLiveData
import com.practicum.appplaylistmaker.ui.common.PlaylistAdapter
import com.practicum.appplaylistmaker.ui.media.view_model.PlaylistsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment : Fragment() {

    private var binding: PlaylistsFragmentBinding? = null
    private val viewModel: PlaylistsViewModel by viewModel()
    private val adapter = PlaylistAdapter()


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PlaylistsFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getObservablePlaylists().observe(viewLifecycleOwner) { playlists ->
            Log.d("playlists", playlists.toString())

            if (playlists.isEmpty()){
                binding?.partWithProblems?.visibility =View.VISIBLE
                binding?.recyclerView?.visibility = View.GONE
            }
            else{
                binding?.partWithProblems?.visibility = View.GONE
                binding?.recyclerView?.visibility = View.VISIBLE
                adapter.playlists = playlists
                adapter.notifyDataSetChanged()
            }


        }

        val result = getNavigationResultLiveData<String>("newPlaylistKey")
        result?.observe(viewLifecycleOwner){ newPlaylist -> Log.d("add", "NewPlaylist: $newPlaylist")
            showSnackbar(Gson().fromJson(newPlaylist, Playlist::class.java).playlistName)
        }

        binding?.buttonNewPlaylist?.setOnClickListener {
            openNewPlaylistFragment()
        }

        val recyclerPlaylistView = binding?.recyclerView
        recyclerPlaylistView?.layoutManager = GridLayoutManager(requireContext(), 2)
        if (recyclerPlaylistView != null) {
            recyclerPlaylistView.adapter = adapter
        }

        viewModel.fillData()

    }


    private fun openNewPlaylistFragment() {
        findNavController().navigate(R.id.newPlaylistFragment)
    }

    private fun showSnackbar(playlistName: String) {
        // Create and show the snackbar
        val snackbar = Snackbar.make(binding?.myCoordinatorLayout as View, "This is $playlistName a snackbar", Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}