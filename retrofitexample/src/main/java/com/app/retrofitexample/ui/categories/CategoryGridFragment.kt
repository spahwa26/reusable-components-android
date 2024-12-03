package com.app.retrofitexample.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.retrofitexample.data.models.Categories
import com.app.retrofitexample.databinding.FragmentCategoryGridBinding
import com.app.retrofitexample.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryGridFragment : Fragment(),
    CategoriesRecyclerAdapter.InteractionListener {

    private var _binding: FragmentCategoryGridBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SampleCategoriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryGridBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.userIntent.send(MainIntent.FetchCategories)
        }
        setupObservers()
    }

    private fun setupObservers() {

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is SampleCategoriesViewModel.UIState.Loading -> {
                    binding.progressBar.visibility = it.visibility
                }
                is SampleCategoriesViewModel.UIState.Error -> {
                    binding.root.showSnackBar(it.message)
                }
                is SampleCategoriesViewModel.UIState.Success -> {
                    val adapter = CategoriesRecyclerAdapter()
                    adapter.addAll(it.categories)
                    adapter.serInteractionListener(this)
                    binding.rvCategoryList.adapter = adapter
                }
                else -> {}
            }
        }
    }

    override fun onCategoryClick(category: Categories) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
