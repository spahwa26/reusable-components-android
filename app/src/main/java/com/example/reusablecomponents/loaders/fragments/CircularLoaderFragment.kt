package com.example.reusablecomponents.loaders.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loaders.loaderClasses.CircularProgressBar
import com.example.loaders.utils.LoaderVisibility
import com.example.reusablecomponents.databinding.FragmentCircularLoaderBinding

class CircularLoaderFragment : Fragment() {
    private var _binding: FragmentCircularLoaderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCircularLoaderBinding.inflate(layoutInflater, container, false)
        showHideLoader(true)
        return binding.root
    }

    private fun showHideLoader(visible: Boolean) {
        binding.composeView.apply {
            setContent {
                LoaderVisibility(visible) {
                    CircularProgressBar(1f, 100)
                }
            }
        }
    }
}