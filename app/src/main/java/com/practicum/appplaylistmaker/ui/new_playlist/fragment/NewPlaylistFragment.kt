package com.practicum.appplaylistmaker.ui.new_playlist.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.practicum.appplaylistmaker.R
import com.practicum.appplaylistmaker.databinding.NewPlaylistFragmentBinding
import com.practicum.appplaylistmaker.domain.models.Playlist
import com.practicum.appplaylistmaker.setNavigationResult
import com.practicum.appplaylistmaker.ui.new_playlist.view_model.NewPlaylistViewModel
import com.practicum.appplaylistmaker.ui.playlist.OpenedPlaylistsFragmentArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar

class NewPlaylistFragment : Fragment() {

    private lateinit var binding: NewPlaylistFragmentBinding
    private val viewModel: NewPlaylistViewModel by viewModel()
    private var playlistImgUri: Uri? = null
    private val args: NewPlaylistFragmentArgs by navArgs()
    private var editingPlaylist: Playlist? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewPlaylistFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editingPlaylistJson = args.playlist
        if (!editingPlaylistJson.isNullOrEmpty()) {
            editingPlaylist = Gson().fromJson(editingPlaylistJson, Playlist::class.java)
            editMode()
        }

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                //обрабатываем событие выбора пользователем фотографии
                if (uri != null) {
                    binding.imageForPlaylist.setImageURI(uri)
                    binding.imageForPlaylist.scaleType = ImageView.ScaleType.CENTER_CROP
                    playlistImgUri = uri
                }
            }

        binding.imageForPlaylist.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.back.setOnClickListener {
            checkDataForDialog()
        }

        binding.etPlaylistTitle.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    binding.buttonCreatePlaylist.isEnabled = s.trim().isNotEmpty()
                }
            })
        binding.buttonCreatePlaylist.setOnClickListener {
            var imgPath = ""
            val timeForImgNameIndicator = Calendar.getInstance().timeInMillis.toString()
            if (playlistImgUri != null) {
                imgPath = saveImageToPrivateStorage(playlistImgUri!!, timeForImgNameIndicator)
            }

            var newPlaylist: Playlist? = null

            if (editingPlaylist != null) {
                editingPlaylist!!.playlistName = binding.etPlaylistTitle.text.toString()
                editingPlaylist!!.description = binding.etPlaylistDiscription.text.toString()
                editingPlaylist!!.imagePath = imgPath

                viewModel.editPlaylist(editingPlaylist!!)

                newPlaylist = editingPlaylist
            } else {
                newPlaylist = Playlist(
                    playlistName = binding.etPlaylistTitle.text.toString(),
                    description = binding.etPlaylistDiscription.text.toString(),
                    imagePath = imgPath,
                    tracksCountInPlaylist = 0,
                )
                viewModel.addPlaylist(
                    newPlaylist
                )
            }
            if(!isFromAudioplayerActivity) {
                setNavigationResult(Gson().toJson(newPlaylist), "newPlaylistKey")
            }

            navigateBack()
        }

        addButtonBackClickAction()
    }

    private fun editMode() {
        binding.back.title = "Редактировать"
        binding.buttonCreatePlaylist.text = "Сохранить"
        if (editingPlaylist?.imagePath?.isNotEmpty() == true) {
            binding.imageForPlaylist.setImageURI(Uri.parse(editingPlaylist?.imagePath))
        }
        binding.etPlaylistTitle.setText(editingPlaylist?.playlistName)
        binding.etPlaylistDiscription.setText(editingPlaylist?.description)
    }

    private fun saveImageToPrivateStorage(uri: Uri, fileName: String): String {
        val filePath =
            File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myalbum")
        if (!filePath.exists()) {
            filePath.mkdirs()
        }
        val file = File(filePath, "$fileName.jpg")
        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
        return file.absolutePath
    }


    private fun navigateBack() {
        if (isFromAudioplayerActivity) {
            requireActivity().findViewById<ScrollView>(R.id.scrollview_with_track).isVisible = true
            parentFragmentManager.popBackStack()
            isFromAudioplayerActivity = false
        } else {
            findNavController().popBackStack()
        }
    }

    private fun checkDataForDialog() {
        if (playlistImgUri != null ||
            !binding.etPlaylistTitle.text.isNullOrEmpty() ||
            !binding.etPlaylistDiscription.text.isNullOrEmpty()
        ) {
            showDialog()
        } else {
            navigateBack()
        }
    }

    fun showDialog(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Завершить создание плейлиста?") // Заголовок диалога
            .setMessage("Все несохраненные данные будут потеряны") // Описание диалога
            .setNegativeButton("Отмена") { dialog, which ->

            }
            .setPositiveButton("Завершить") { dialog, which ->
                navigateBack()
            }
            .show()
    }


    private fun addButtonBackClickAction() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    checkDataForDialog()
                }
            })
    }
    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        private var isFromAudioplayerActivity = false
        fun newInstance(isFromAudioplayer: Boolean): Fragment {
            isFromAudioplayerActivity = isFromAudioplayer
            return NewPlaylistFragment()
        }
    }
}