package com.practicum.appplaylistmaker.ui.media

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.practicum.appplaylistmaker.databinding.NfragmentBinding

class NFragment : Fragment() {
    companion object {
        private const val TEXT = "text"
        private const val BUTTON_VISIBILITY = "buttonVisibility"
        fun newInstance(text: String, buttonVisibility: Boolean) = NFragment().apply {
            Log.d("AAAAAAAAAAAAA", "We are in fragment onCreateView")

            arguments = Bundle().apply {
                putString(TEXT, text)
                putBoolean(BUTTON_VISIBILITY, buttonVisibility)
            }
        }
    }

    private lateinit var binding: NfragmentBinding

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("AAAAAAAAAAAAA", "We are in fragment onCreateView")
        binding = NfragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("AAAAAAAAAAAAA", "We are in fragment onViewCreated")

        binding.text.text = requireArguments().getString(TEXT)
        binding.buttonNewPlaylist.visibility =
            if (requireArguments().getBoolean(BUTTON_VISIBILITY)) {
                View.VISIBLE
            } else View.INVISIBLE
    }


}