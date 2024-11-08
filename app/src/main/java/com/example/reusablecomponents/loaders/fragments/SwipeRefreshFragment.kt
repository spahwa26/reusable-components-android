package com.example.reusablecomponents.loaders.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.loaders.loaderClasses.swipeRefresh.DataViewModel
import com.example.loaders.loaderClasses.swipeRefresh.startRefreshing
import com.example.loaders.loaderClasses.swipeRefresh.stopRefreshing
import com.example.reusablecomponents.R
import com.example.reusablecomponents.databinding.FragmentSwipeRefreshBinding
import kotlinx.coroutines.launch

class SwipeRefreshFragment : Fragment() {
    private var _binding: FragmentSwipeRefreshBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DataViewModel>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSwipeRefreshBinding.inflate(layoutInflater, container, false)
        val gestureEvent = binding.composeView.startRefreshing(requireActivity(), R.raw.loader)
        binding.root.setOnTouchListener { _, event ->
            viewModel.fetchData()
            binding.tvText.isVisible = false
            gestureEvent.onTouchEvent(event)
            true
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.data.observe(viewLifecycleOwner) { data ->
                binding.tvText.text = data
                binding.tvText.isVisible = true
                if (data != null)
                    binding.composeView.stopRefreshing(R.raw.loader)
            }
        }
    }
}


