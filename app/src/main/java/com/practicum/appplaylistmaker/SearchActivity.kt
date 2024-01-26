package com.practicum.appplaylistmaker

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.practicum.appplaylistmaker.api.ITunesApi
import com.practicum.appplaylistmaker.api.SearchTrackResponse
import com.practicum.appplaylistmaker.api.Track
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {
    companion object {
        const val TEXT_VALUE = "TEXT_VALUE"
        const val AMOUNT_DEF = ""
        const val MAX_HISTORY_SIZE = 10
    }

    private lateinit var mEditText: EditText
    private lateinit var mClearText: ImageButton
    private var textValue: String = AMOUNT_DEF
    private lateinit var contentContainer: FrameLayout
    private lateinit var noTracksView: View
    private lateinit var noInternetView: View
    private val baseUrl = "https://itunes.apple.com"
    private lateinit var historyWithMusic: ViewGroup
    private lateinit var sharedPreferences: SharedPreferences
    private val retrofit =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .build()
    private val iTunesService = retrofit.create(ITunesApi::class.java)

    private val tracks = ArrayList<Track>()
    private val adapter = MusicAdapter {
        showTrack(it)
    }
    private val historyAdapter = MusicAdapter {
        showTrack(it)
    }


    private fun showTrack(it: Track) {
        addingToHistory(it)
        // TODO: Переход на плеер (в следующих спринтах)
    }



    private fun addingToHistory(it: Track) {
        var history = getTracksHistoryFromSharedPrefs()
        history = history.filter { track -> track != it }.toTypedArray()
        history = arrayOf(it) + history
        history = history.take(MAX_HISTORY_SIZE).toTypedArray()
        sharedPreferences.edit().putString(HISTORY_TRACKS_KEY, Gson().toJson(history)).apply()
    }

    private fun createTrackListFromJson(json: String): Array<Track> {
        return Gson().fromJson(json, Array<Track>::class.java)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TEXT_VALUE, textValue)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mEditText.setText(savedInstanceState.getString(TEXT_VALUE))
    }

    private fun updateHistory() {
        historyAdapter.tracks = ArrayList(getTracksHistoryFromSharedPrefs().toList())
        adapter.tracks.clear()
        adapter.notifyDataSetChanged()
        historyAdapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        mEditText = findViewById<View>(R.id.Search) as EditText
        mClearText = findViewById<View>(R.id.clearText) as ImageButton
        contentContainer = findViewById<ViewGroup>(R.id.contentContainer) as FrameLayout
        noInternetView = findViewById(R.id.no_internet_view)
        noTracksView = findViewById(R.id.no_music_view)
        historyWithMusic = findViewById(R.id.history_music_list)

        sharedPreferences = getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)

        val buttonClearHistory: Button? = findViewById(R.id.button_clean)
        buttonClearHistory?.setOnClickListener {
            historyAdapter.tracks.clear()
            sharedPreferences.edit().putString(HISTORY_TRACKS_KEY, Gson().toJson(arrayOf<Track>()))
                .apply()
            historyAdapter.notifyDataSetChanged()
            historyWithMusic.visibility = View.GONE
        }


        val buttonUpdate: Button? = findViewById(R.id.button_update)
        buttonUpdate?.setOnClickListener {
            search()
        }
        mEditText.setOnFocusChangeListener { _, hasFocus ->
            updateHistory()
            historyWithMusic.visibility =
                if (hasFocus && mEditText.text.isEmpty() && historyAdapter.tracks.isNotEmpty()) View.VISIBLE else View.GONE
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

        val recyclerMusicHistoryView =
            findViewById<RecyclerView>(R.id.musicListHistory)
        recyclerMusicHistoryView.layoutManager = LinearLayoutManager(this)
        recyclerMusicHistoryView.adapter = historyAdapter

        val recyclerMusicView = findViewById<RecyclerView>(R.id.music_list)
        adapter.tracks = tracks
        recyclerMusicView.layoutManager = LinearLayoutManager(this)
        recyclerMusicView.adapter = adapter

        mClearText.setOnClickListener {
            clear(mEditText)
        }
        mClearText.visibility = View.INVISIBLE
        mEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length != 0) {
                    historyWithMusic.visibility = View.GONE
                    mClearText.visibility = View.VISIBLE
                } else {
                    updateHistory()
                    mClearText.visibility = View.GONE
                    if (historyAdapter.tracks.isEmpty()) {
                        historyWithMusic.visibility = View.GONE
                    } else {
                        historyWithMusic.visibility = View.VISIBLE
                    }
                }
                textValue = s.toString()
            }
        })
    }

    //clear button onclick
    fun clear(view: View?) {
        mEditText.setText("")
        mClearText.visibility = View.GONE
        noTracksView.visibility = View.GONE
        noInternetView.visibility = View.GONE
        tracks.clear()
        adapter.notifyDataSetChanged()

    }

    private fun showNoResults() {

        tracks.clear()
        adapter.notifyDataSetChanged()
        noTracksView.visibility = View.VISIBLE

    }

    private fun getTracksHistoryFromSharedPrefs(): Array<Track> {
        val tracksHistory = sharedPreferences.getString(HISTORY_TRACKS_KEY, null)
        if (tracksHistory != null) {
            return createTrackListFromJson(tracksHistory)
        }
        return arrayOf()

    }


    private fun showInternetError() {
        tracks.clear()
        adapter.notifyDataSetChanged()
        noInternetView.visibility = View.VISIBLE

    }

    private fun search() {
        noTracksView.visibility = View.GONE
        noInternetView.visibility = View.GONE
        iTunesService.search(mEditText.text.toString())
            .enqueue(object : Callback<SearchTrackResponse> {
                override fun onResponse(
                    call: Call<SearchTrackResponse>, response: Response<SearchTrackResponse>
                ) {
                    println("Got response " + response.code())
                    when (response.code()) {
                        200 -> {
                            if (response.body()?.results?.isNotEmpty() == true) {
                                tracks.clear()
                                tracks.addAll(response.body()!!.results)
                                adapter.notifyDataSetChanged()
                            } else {
                                showNoResults()
                            }

                        }

                        else -> showInternetError()
                    }

                }

                override fun onFailure(call: Call<SearchTrackResponse>, t: Throwable) {
                    println(t.message)
                    showInternetError()
                }

            })
    }

}