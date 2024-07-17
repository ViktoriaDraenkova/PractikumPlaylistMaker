package com.practicum.appplaylistmaker.ui.search.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.practicum.appplaylistmaker.CLICK_DEBOUNCE_DELAY
import com.practicum.appplaylistmaker.KEY_FOR_TRACK
import com.practicum.appplaylistmaker.R
import com.practicum.appplaylistmaker.databinding.FragmentSearchBinding
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.ui.audioplayer.AudioplayerActivity
import com.practicum.appplaylistmaker.ui.search.view_model.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {
    companion object {
        const val TEXT_VALUE = "TEXT_VALUE"
        const val AMOUNT_DEF = ""
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private lateinit var binding: FragmentSearchBinding
    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var mEditText: EditText
    private lateinit var mClearText: ImageButton
    private var textValue: String = AMOUNT_DEF
    private lateinit var contentContainer: FrameLayout
    private lateinit var noTracksView: View
    private lateinit var noInternetView: View
    private lateinit var progressBar: ProgressBar
    private lateinit var historyWithMusic: ViewGroup
    private lateinit var musicListView: View
    private val viewModel: SearchViewModel by viewModel()

    private val adapter = MusicAdapter {
        showTrack(it)
    }
    private val historyAdapter = MusicAdapter {
        showTrack(it)
    }

    private val searchRunnable = Runnable { search() }

    private fun initViews() {
        mEditText = binding.Search
        mClearText = binding.clearText
        contentContainer = binding.contentContainer
        musicListView = binding.musicList
        noInternetView = binding.noInternetView
        noTracksView = binding.noMusicView
        historyWithMusic = binding.historyMusicList
        progressBar = binding.progressBar
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        viewModel.getTracksLiveData().observe(viewLifecycleOwner) { tracks ->
            adapter.tracks = tracks
            adapter.notifyDataSetChanged()
        }
        viewModel.getTracksHistoryLiveData().observe(viewLifecycleOwner) { history ->
            historyAdapter.tracks = history
            historyAdapter.notifyDataSetChanged()
        }
        viewModel.getScreenStateLiveData().observe(viewLifecycleOwner) { screenState ->
            progressBar.visibility = View.GONE
            historyWithMusic.visibility = View.GONE
            noInternetView.visibility = View.GONE
            noTracksView.visibility = View.GONE
            musicListView.visibility = View.GONE

            adapter.notifyDataSetChanged()
            when (screenState) {
                SearchViewModel.ScreenState.LOADING -> progressBar.visibility = View.VISIBLE
                SearchViewModel.ScreenState.HISTORY -> historyWithMusic.visibility = View.VISIBLE
                SearchViewModel.ScreenState.LOADED -> musicListView.visibility = View.VISIBLE
                SearchViewModel.ScreenState.NO_INTERNET -> noInternetView.visibility = View.VISIBLE
                SearchViewModel.ScreenState.NO_RESULTS -> noTracksView.visibility = View.VISIBLE
                else -> {}
            }
        }

        binding.buttonClean.setOnClickListener {
            viewModel.clearTrackHistory()
        }

        val buttonUpdate = view.findViewById<Button>(R.id.button_update)

        buttonUpdate.setOnClickListener {
            search()
        }


        mEditText.setOnFocusChangeListener { _, hasFocus ->

            if (hasFocus && mEditText.text.isEmpty()) {
                viewModel.showHistory()
            }
        }

        mEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
                true
            }
            false
        }


        val recyclerMusicHistoryView = binding.musicListHistory
        recyclerMusicHistoryView.layoutManager = LinearLayoutManager(requireContext())
        recyclerMusicHistoryView.adapter = historyAdapter
        historyAdapter.tracks = viewModel.getTracksHistoryLiveData().value ?: ArrayList<Track>()

        val recyclerMusicView = binding.musicList
        adapter.tracks = viewModel.getTracksLiveData().value ?: ArrayList<Track>()
        recyclerMusicView.layoutManager = LinearLayoutManager(requireContext())
        recyclerMusicView.adapter = adapter

        mClearText.setOnClickListener {
            mEditText.setText("")
        }
        mClearText.visibility = View.GONE
        mEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.trim().isNotEmpty()) {
                    searchDebounce()
                    mClearText.visibility = View.VISIBLE
                } else {
                    if (viewModel.getTracksHistoryLiveData().value?.isEmpty() == false) {
                        viewModel.showHistory()
                    }
                    mClearText.visibility = View.GONE
                }
                textValue = s.toString()
            }
        })
    }

    private fun search() {
        Log.d("AAAAAAAA", "ISCHU!!!!!!!")

        viewModel.search(mEditText.text.toString())
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun showTrack(it: Track) {
        if (clickDebounce()) {
            viewModel.addTrackToHistory(it)
            val intent = Intent(requireContext(), AudioplayerActivity::class.java)
            intent.putExtra(KEY_FOR_TRACK, Gson().toJson(it))
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TEXT_VALUE, textValue)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            mEditText.setText(savedInstanceState.getString(TEXT_VALUE))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}