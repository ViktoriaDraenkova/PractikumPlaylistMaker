package com.practicum.appplaylistmaker

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
import com.practicum.appplaylistmaker.api.ITunesApi
import com.practicum.appplaylistmaker.api.SearchTrackResponse
import com.practicum.appplaylistmaker.api.Track
import com.practicum.recyclerview_lesson_1.MusicAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {
    companion object {
        const val TEXT_VALUE = "TEXT_VALUE"
        const val AMOUNT_DEF = ""
    }
    private var mEditText: EditText? = null
    private var mClearText: ImageButton? = null
    private var textValue: String = AMOUNT_DEF
    private var contentContainer: FrameLayout? = null
    private var noTracksView: View? = null
    private var noInternetView: View? = null
    private val baseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesService = retrofit.create(ITunesApi::class.java)

    private val tracks = ArrayList<Track>()
    private val adapter = MusicAdapter {
        showTrack(it)
    }

    private fun showTrack(it: Track) {
        println("Трек отображён")
        // TODO: Переход на плеер (в следующих спринтах)
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TEXT_VALUE, textValue)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mEditText?.setText(savedInstanceState.getString(TEXT_VALUE))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        mEditText = findViewById<View>(R.id.Search) as EditText
        mClearText = findViewById<View>(R.id.clearText) as ImageButton
        contentContainer = findViewById<ViewGroup>(R.id.contentContainer) as FrameLayout
        noInternetView = findViewById(R.id.no_internet_view)
        noTracksView = findViewById(R.id.no_music_view)

        val buttonUpdate: Button? = findViewById(R.id.button_update)
        buttonUpdate?.setOnClickListener {
            search()
        }

        mEditText?.setOnEditorActionListener { _, actionId, _ ->
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

        val recyclerMusicView = findViewById<RecyclerView>(R.id.musicList)

        adapter.tracks = tracks
        recyclerMusicView.layoutManager = LinearLayoutManager(this)
        recyclerMusicView.adapter = adapter

        mClearText?.setOnClickListener {
            clear(mEditText)
        }
        mClearText?.visibility = View.INVISIBLE
        mEditText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length != 0) {
                    mClearText?.visibility = View.VISIBLE
                } else {
                    mClearText?.visibility = View.GONE
                }
                textValue = s.toString()
            }
        })
    }

    //clear button onclick
    fun clear(view: View?) {
        mEditText?.setText("")
        mClearText?.visibility = View.GONE
        noTracksView?.visibility = View.GONE
        noInternetView?.visibility = View.GONE
        tracks.clear()
        adapter.notifyDataSetChanged()

    }

    private fun showNoResults() {

        tracks.clear()
        adapter.notifyDataSetChanged()
        noTracksView?.visibility = View.VISIBLE

    }

    private fun showInternetError() {
        tracks.clear()
        adapter.notifyDataSetChanged()
        noInternetView?.visibility = View.VISIBLE

    }

    private fun search() {
        noTracksView?.visibility = View.GONE
        noInternetView?.visibility = View.GONE
        iTunesService.search(mEditText?.text.toString())
            .enqueue(object : Callback<SearchTrackResponse> {
                override fun onResponse(
                    call: Call<SearchTrackResponse>,
                    response: Response<SearchTrackResponse>
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