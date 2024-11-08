package com.example.reusablecomponents.loaders.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.reusablecomponents.databinding.FragmentLoaderBinding

class LoaderFragment : Fragment() {
    private var _binding: FragmentLoaderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoaderBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding.apply {

            btnCircular.setOnClickListener {
                findNavController().navigate(LoaderFragmentDirections.actionCircularLoader())
            }

            btnDotsLoader.setOnClickListener {
                findNavController().navigate(LoaderFragmentDirections.actionDotsLoader())
            }

            btnPulseLoader.setOnClickListener {
                findNavController().navigate(LoaderFragmentDirections.actionPulseLoader())
            }

            btnCircularSpinner.setOnClickListener {
                findNavController().navigate(LoaderFragmentDirections.actionCircularSpinner())
            }

            btnHorizontalProgress.setOnClickListener {
                findNavController().navigate(LoaderFragmentDirections.actionHorizontalProgress())
            }

            btnHorizontalProgressPercent.setOnClickListener {
                findNavController().navigate(LoaderFragmentDirections.actionHorizontalPercentLoader())
            }

            btnRippleLoader.setOnClickListener {
                findNavController().navigate(LoaderFragmentDirections.actionRippleLoader())
            }

            btnDotsFadyCirclularLoader.setOnClickListener {
                findNavController().navigate(LoaderFragmentDirections.actionCircularDotsLoader())
            }

            btnDotsFadyLoader.setOnClickListener {
                findNavController().navigate(LoaderFragmentDirections.actionDotsFady())
            }

            btnDotsScalyLoader.setOnClickListener {
                findNavController().navigate(LoaderFragmentDirections.actionDotsScaly())
            }

            btnRectangularSpinner.setOnClickListener {
                findNavController().navigate(LoaderFragmentDirections.actionRectangularSpinner())
            }

            btnSwipeRefresh.setOnClickListener {
                findNavController().navigate(LoaderFragmentDirections.actionSwipeRefresh())
            }
        }
    }
}
