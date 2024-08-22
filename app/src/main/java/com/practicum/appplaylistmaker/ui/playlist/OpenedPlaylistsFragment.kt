package com.practicum.appplaylistmaker.ui.playlist

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.practicum.appplaylistmaker.CLICK_DEBOUNCE_DELAY
import com.practicum.appplaylistmaker.KEY_FOR_TRACK
import com.practicum.appplaylistmaker.R
import com.practicum.appplaylistmaker.databinding.FragmentPlaylistOpenedBinding
import com.practicum.appplaylistmaker.domain.models.Playlist
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.getNavigationResultLiveData
import com.practicum.appplaylistmaker.ui.audioplayer.AudioplayerActivity
import com.practicum.appplaylistmaker.ui.common.MusicAdapter
import com.practicum.appplaylistmaker.ui.playlist.view_model.OpenedPlaylistViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class OpenedPlaylistsFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistOpenedBinding
    private val viewModel: OpenedPlaylistViewModel by viewModel()
    private val args: OpenedPlaylistsFragmentArgs by navArgs()
    private lateinit var playlist: Playlist
    private val adapter = MusicAdapter(
        {
            showTrack(it)
        },
        {
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.delete_track))
                .setMessage(getString(R.string.delete_track_agreement))
                .setPositiveButton(getString(R.string.delete)) { _, _ ->
                    viewModel.deleteTrack(playlist.playlistId, it.trackId)
                }
                .setNegativeButton(getString(R.string.discard)) { _, _ ->
                }
                .create()
            dialog.show()
        }
    )
    private var tracksInPlaylistBottomSheetBehavior: BottomSheetBehavior<LinearLayout>? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var bottomSheetContainer: LinearLayout

    private var isClickAllowed = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistOpenedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playlist = Gson().fromJson(args.playlist, Playlist::class.java)
        initPlaylist(playlist)
        initLittlePlaylistIcon(playlist)

        val result = getNavigationResultLiveData<String>("newPlaylistKey")
        result?.observe(viewLifecycleOwner) { newPlaylist ->
            playlist = Gson().fromJson(newPlaylist, Playlist::class.java)
            initPlaylist(playlist)
            initLittlePlaylistIcon(playlist)
            bottomSheetBehavior.state=BottomSheetBehavior.STATE_HIDDEN
        }

        viewModel.getTracksLiveData().observe(viewLifecycleOwner){tracks ->
            adapter.tracks = tracks
            adapter.notifyDataSetChanged()
            setPlaylistSummary(tracks)
            playlist.tracks = tracks
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.sharePlaylistButton.setOnClickListener {
            sharePlaylist(playlist)
        }

        binding.playlistLayoutPersistentBottomSheet.deletePlaylist.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.delete_playlist))
                .setMessage(getString(R.string.delete_playlist_agreement))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    viewModel.deletePlaylist(playlist.playlistId)

                    findNavController().popBackStack()
                }
                .setNegativeButton(getString(R.string.no)) { _, _ ->
                }
                .create()
            dialog.show()

        }

        binding.playlistLayoutPersistentBottomSheet.shareButton.setOnClickListener {
            if(playlist.tracks.isEmpty()){
                bottomSheetBehavior.state=BottomSheetBehavior.STATE_HIDDEN
            }
            sharePlaylist(playlist)
        }

        binding.playlistLayoutPersistentBottomSheet.redactPlaylist.setOnClickListener {
            val navController = findNavController()
            val bundle = bundleOf("playlist" to Gson().toJson(playlist))
            navController.navigate(R.id.newPlaylistFragment, bundle)
        }

        tracksInPlaylistBottomSheetBehavior =
            BottomSheetBehavior.from(binding.tracksInPlaylistBottomSheet)
        binding.tracksInPlaylistBottomSheet.post {
            setTracksBottomSheetHeight()
        }

        bottomSheetContainer = binding.playlistsBottomSheet
        val overlay = binding.overlayInplaylist

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetContainer).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {

                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        overlay.visibility = View.GONE
                    }

                    else -> {
                        overlay.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        binding.menuPlaylistButton.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
    }

    private fun sharePlaylist(playlist: Playlist) {
        if (playlist.tracks.isEmpty()) {
            Toast.makeText(
                requireContext(),
                getString(R.string.no_tracks_to_share),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, createMessageToShare(playlist))
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, getString(R.string.share_playlist)))
        }
    }

    private fun createMessageToShare(playlist: Playlist): String {
        var tracksText = ""
        var counter = 1
        for (track in playlist.tracks) {
            tracksText =
                tracksText.plus("$counter. ${track.artistName} - ${track.trackName} (${track.getHumanReadableDuration()}) \n")
            counter++
        }

        return "${playlist.playlistName}\n${playlist.description}\n${playlist.tracks.size} треков\n$tracksText"
    }

    private fun initLittlePlaylistIcon(playlist: Playlist) {

        binding.playlistLayoutPersistentBottomSheet.playlistNameLittle.text = playlist.playlistName
        binding.playlistLayoutPersistentBottomSheet.tracksCountLittle.text =
            getString(R.string.tracks_count_template, playlist.tracks.size)

        if (playlist.imagePath.isNotEmpty()) {
            binding.playlistLayoutPersistentBottomSheet.playlistLittleImage.setImageURI(
                Uri.parse(
                    playlist.imagePath
                )
            )
            binding.playlistIcon.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    private fun setPlaylistSummary(tracks: List<Track>) {
        var dura = 0
        for (i in tracks) {
            dura += i.trackTime / (1000 * 60)
        }
        binding.duration.text = getString(R.string.playlist_summary_template, dura, tracks.size)
    }

    private fun initPlaylist(playlist: Playlist) {
        binding.playlistName.text = playlist.playlistName
        if (playlist.description.isEmpty()) {
            binding.playlistDescription.visibility = View.GONE
        } else {
            binding.playlistDescription.visibility = View.VISIBLE
            binding.playlistDescription.text = playlist.description
        }
        setPlaylistSummary(playlist.tracks)

        if (playlist.imagePath.isNotEmpty()) {
            binding.playlistIcon.setImageURI(Uri.parse(playlist.imagePath))
            binding.playlistIcon.scaleType = ImageView.ScaleType.CENTER_CROP
        }

        val recyclerMusicView = binding.recyclerView
        recyclerMusicView.layoutManager = LinearLayoutManager(requireContext())
        recyclerMusicView.adapter = adapter
        viewModel.initTracks(playlist.tracks)
    }

    private fun showTrack(it: Track) {
        if (clickDebounce()) {
            val intent = Intent(requireContext(), AudioplayerActivity::class.java)
            intent.putExtra(KEY_FOR_TRACK, Gson().toJson(it))
            startActivity(intent)
        }
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

    private fun setTracksBottomSheetHeight() {
        val shareBtnCoordinates = IntArray(2)
        binding.sharePlaylistButton.getLocationOnScreen(shareBtnCoordinates)
        var bottomSheetHeight = binding.root.height - shareBtnCoordinates[1] - 24
        tracksInPlaylistBottomSheetBehavior?.peekHeight = bottomSheetHeight
    }


}