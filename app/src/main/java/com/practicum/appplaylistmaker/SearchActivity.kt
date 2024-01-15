package com.practicum.appplaylistmaker

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity


class SearchActivity : AppCompatActivity() {
    private var mEditText: EditText? = null
    private var mClearText: ImageButton? = null
    private var textValue: String = AMOUNT_DEF

    companion object {
        const val TEXT_VALUE = "TEXT_VALUE"
        const val AMOUNT_DEF = ""
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TEXT_VALUE, textValue)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mEditText!!.setText(savedInstanceState.getString(TEXT_VALUE))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        mEditText = findViewById<View>(R.id.Search) as EditText
        mClearText = findViewById<View>(R.id.clearText) as ImageButton

        val buttonBack = findViewById<Toolbar>(R.id.back)
        buttonBack.setNavigationOnClickListener {
            finish()
        }

        mClearText!!.setOnClickListener {
            clear(mEditText)
        }
        mClearText!!.visibility = View.INVISIBLE
        mEditText!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length != 0) {
                    mClearText!!.visibility = View.VISIBLE
                } else {
                    mClearText!!.visibility = View.GONE
                }
                textValue = s.toString()
            }
        })
    }

    //clear button onclick
    fun clear(view: View?) {
        mEditText!!.setText("")
        mClearText!!.visibility = View.GONE
    }
}