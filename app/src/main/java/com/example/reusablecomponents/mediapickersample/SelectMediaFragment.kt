package com.example.reusablecomponents.mediapickersample

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reusablecomponents.R
import com.example.reusablecomponents.databinding.FragmentSelectMediaBinding
import com.example.reusablecomponents.utils.clearInternalCache
import com.example.reusablecomponents.utils.getFIleFromUri
import com.nickelfox.media_picker.ui.MediaPickerForFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


@AndroidEntryPoint
class SelectMediaFragment : Fragment() {
    private lateinit var binding: FragmentSelectMediaBinding
    private lateinit var itemAdapter: SelectedMediaAdapter
    private lateinit var mediaItemList: ArrayList<File>
    private lateinit var mediaPickerForFragment: MediaPickerForFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireContext().clearInternalCache()
        binding.apply {
            mediaItemList = ArrayList()
            mediaPickerForFragment = MediaPickerForFragment(this@SelectMediaFragment)
            itemAdapter =
                SelectedMediaAdapter(requireContext(), object : SelectedMediaAdapter.ClickItem {
                    override fun onClick(type: String, uri: Uri) {
                        ItemViewFragment(uri, type).show(
                            childFragmentManager,
                            getString(R.string.show_item)
                        )
                    }
                })
            itemRv.adapter = itemAdapter
            itemRv.layoutManager = LinearLayoutManager(requireContext())
            selectImageBtn.setOnClickListener {
                selectMedia(isMultiple = false, isVideoOnly = false)
            }
            selectVideoBtn.setOnClickListener {
                selectMedia(isMultiple = false, isVideoOnly = true)
            }
            selectMultipleImagesBtn.setOnClickListener {
                selectMedia(isMultiple = true, isVideoOnly = false)
            }
            selectMultipleVideosBtn.setOnClickListener {
                selectMedia(isMultiple = true, isVideoOnly = true)
            }
            noSelectBtn.setOnClickListener {
                selectMedia(isMultiple = true, isVideoOnly = false, isBoth = true)
            }
            selectBothImagesVideos.setOnClickListener {
                selectMedia(isMultiple = true, isVideoOnly = false, isBoth = true)
            }
            selectOvalImagesBtn.setOnClickListener {
                selectCroppedImage(true)
            }
            selectRectImageBtn.setOnClickListener {
                selectCroppedImage(false)
            }
            checkVisibility()
            selectedCount()
        }
    }

    private fun selectCroppedImage(isOval: Boolean) {
        mediaPickerForFragment.pickAndCropImage(isOval) { bitmap, file ->
            mediaItemList.add(file)
            addFileToAdapter()
        }
    }

    private fun selectMedia(
        isMultiple: Boolean, isVideoOnly: Boolean,
        isBoth: Boolean = false
    ) {
        mediaPickerForFragment.pickMedia(
            isMultiple,
            isVideoOnly,
            isBoth
        ) { mediaUris, _ ->
            CoroutineScope(IO).launch {
                mediaUris.forEach {
                    requireContext().getFIleFromUri(it)?.let {file->
                        mediaItemList.add(file)
                    }
                }
                withContext(Main){
                    addFileToAdapter()
                }
            }
        }
    }

    private fun addFileToAdapter() {
        itemAdapter.submitList(mediaItemList)
        itemAdapter.notifyDataSetChanged()
        checkVisibility()
        selectedCount()
    }

    private fun checkVisibility() {
        binding.apply {
            noItemSelectedGroup.isVisible = itemAdapter.currentList.isEmpty()
            itemSelectedGroup.isVisible = itemAdapter.currentList.isNotEmpty()
        }
    }

    private fun selectedCount() {
        binding.selectedCountTv.text = buildString {
            append("(${itemAdapter.currentList.size})")
        }
    }
}
