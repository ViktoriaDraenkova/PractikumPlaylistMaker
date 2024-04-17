package com.practicum.appplaylistmaker.ui.search.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.practicum.appplaylistmaker.CLICK_DEBOUNCE_DELAY
import com.practicum.appplaylistmaker.KEY_FOR_TRACK
import com.practicum.appplaylistmaker.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.ui.audioplayer.AudioplayerActivity
import com.practicum.appplaylistmaker.ui.search.view_model.SearchViewModel


class SearchActivity : AppCompatActivity() {
    companion object {
        const val TEXT_VALUE = "TEXT_VALUE"
        const val AMOUNT_DEF = ""
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }


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
    private val viewModel: SearchViewModel by viewModel()

    private val adapter = MusicAdapter {
        showTrack(it)
    }
    private val historyAdapter = MusicAdapter {
        showTrack(it)
    }

    private val searchRunnable = Runnable { search() }

    private fun initViews() {
        mEditText = findViewById<View>(R.id.Search) as EditText
        mClearText = findViewById<View>(R.id.clearText) as ImageButton
        contentContainer = findViewById<ViewGroup>(R.id.contentContainer) as FrameLayout
        noInternetView = findViewById(R.id.no_internet_view)
        noTracksView = findViewById(R.id.no_music_view)
        historyWithMusic = findViewById(R.id.history_music_list)
        progressBar = findViewById(R.id.progressBar)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)
        initViews()

        viewModel.getTracksLiveData().observe(this) { tracks ->
            adapter.tracks = tracks
            adapter.notifyDataSetChanged()
        }
        viewModel.getTracksHistoryLiveData().observe(this) { history ->
            historyAdapter.tracks = history
            historyAdapter.notifyDataSetChanged()
        }
        viewModel.getScreenStateLiveData().observe(this) { screenState ->
            progressBar.visibility = View.GONE
            historyWithMusic.visibility = View.GONE
            noInternetView.visibility = View.GONE
            noTracksView.visibility = View.GONE
            Log.d("Logs of view!!!!", screenState.value)

            adapter.notifyDataSetChanged()
            when (screenState) {
                SearchViewModel.ScreenState.LOADING -> progressBar.visibility = View.VISIBLE
                SearchViewModel.ScreenState.HISTORY -> {historyWithMusic.visibility = View.VISIBLE
                    Log.d("Logs of view!!!!", screenState.value)
                }
                SearchViewModel.ScreenState.NO_INTERNET -> noInternetView.visibility = View.VISIBLE
                SearchViewModel.ScreenState.NO_RESULTS -> noTracksView.visibility = View.VISIBLE
                else -> {}
            }
        }

        val buttonClearHistory: Button? = findViewById(R.id.button_clean)
        buttonClearHistory?.setOnClickListener {
            viewModel.clearTrackHistory()
        }

        val buttonUpdate: Button? = findViewById(R.id.button_update)
        buttonUpdate?.setOnClickListener {
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

        val buttonBack = findViewById<Toolbar>(R.id.back)
        buttonBack.setNavigationOnClickListener {
            finish()
        }

        val recyclerMusicHistoryView = findViewById<RecyclerView>(R.id.musicListHistory)
        recyclerMusicHistoryView.layoutManager = LinearLayoutManager(this)
        recyclerMusicHistoryView.adapter = historyAdapter
        historyAdapter.tracks = viewModel.getTracksHistoryLiveData().value ?: ArrayList<Track>()

        val recyclerMusicView = findViewById<RecyclerView>(R.id.music_list)
        adapter.tracks = viewModel.getTracksLiveData().value ?: ArrayList<Track>()
        recyclerMusicView.layoutManager = LinearLayoutManager(this)
        recyclerMusicView.adapter = adapter

        mClearText.setOnClickListener {
            mEditText.setText("")
        }
        mClearText.visibility = View.INVISIBLE
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
            val intent = Intent(this, AudioplayerActivity::class.java)
            intent.putExtra(KEY_FOR_TRACK, Gson().toJson(it))
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TEXT_VALUE, textValue)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mEditText.setText(savedInstanceState.getString(TEXT_VALUE))
    }

}