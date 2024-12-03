package com.app.retrofitexample.ui.samplefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.retrofitexample.databinding.FragmentBlankBinding
import com.app.retrofitexample.ui.samplefragment.BlankFragmentDirections

class BlankFragment : Fragment() {

    private var _binding: FragmentBlankBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBlankBinding.inflate(inflater, container, false)
        binding.btnCallCategories.setOnClickListener {
            findNavController().navigate(BlankFragmentDirections.actionCategoryGridFragment())
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
