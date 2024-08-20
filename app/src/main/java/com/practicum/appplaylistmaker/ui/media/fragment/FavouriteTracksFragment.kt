package com.practicum.appplaylistmaker.ui.media.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.practicum.appplaylistmaker.CLICK_DEBOUNCE_DELAY
import com.practicum.appplaylistmaker.KEY_FOR_TRACK
import com.practicum.appplaylistmaker.databinding.FavouriteTracksFragmentBinding
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.ui.audioplayer.AudioplayerActivity
import com.practicum.appplaylistmaker.ui.common.MusicAdapter
import com.practicum.appplaylistmaker.ui.media.FavouritesTrackState
import com.practicum.appplaylistmaker.ui.media.view_model.FavouriteTracksViewModel
import com.practicum.appplaylistmaker.ui.media.view_model.PlaylistsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouriteTracksFragment : Fragment() {

    private lateinit var binding: FavouriteTracksFragmentBinding
    private val viewModel: FavouriteTracksViewModel by viewModel()
    private var adapter: MusicAdapter? = null

    private lateinit var favouriteTracksList: RecyclerView
    private lateinit var placeholder: ViewGroup

    private var isClickAllowed = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavouriteTracksFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MusicAdapter({ track ->
            showTrack(track)
        })
        favouriteTracksList = binding.likedMusicList
        favouriteTracksList.adapter = adapter
        favouriteTracksList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        placeholder = binding.noFavouriteTracks

        viewModel.fillData()
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fillData()
    }

    private fun render(state: FavouritesTrackState) {
        when (state) {
            is FavouritesTrackState.Content -> showContent(state.tracks)
            is FavouritesTrackState.Empty -> showPlaceholder()
            else -> {}
        }
    }

    private fun showContent(tracks: List<Track>) {
        favouriteTracksList.visibility = View.VISIBLE
        placeholder.visibility = View.GONE
        adapter?.tracks = tracks
        adapter?.notifyDataSetChanged()

    }

    private fun showPlaceholder() {
        placeholder.visibility = View.VISIBLE
        favouriteTracksList.visibility = View.GONE
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

    private fun showTrack(it: Track) {
        if (clickDebounce()) {
            val intent = Intent(requireContext(), AudioplayerActivity::class.java)
            intent.putExtra(KEY_FOR_TRACK, Gson().toJson(it))
            startActivity(intent)
            Log.d("showTrack", "activity ended")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }


}