package com.example.reusablecomponents.loaders.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.unit.dp
import com.example.loaders.loaderClasses.RectangularLoader
import com.example.loaders.utils.LoaderVisibility
import com.example.reusablecomponents.databinding.FragmentRectangularSpinnerBinding

class RectangularSpinnerFragment : Fragment() {
    private var _binding: FragmentRectangularSpinnerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRectangularSpinnerBinding.inflate(layoutInflater, container, false)
        showHideLoader(true)
        return binding.root
    }

    private fun showHideLoader(visible: Boolean) {
        binding.composeView.apply {
            setContent {
                LoaderVisibility(visible) {
                    RectangularLoader(
                        width = 100.dp,
                        height = 70.dp,
                        cornerRadius = 32f
                    )
                }
            }
        }
    }
}