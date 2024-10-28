package com.example.reusablecomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.reusablecomponents.R
import com.example.reusablecomponents.databinding.FragmentSecondBinding


class Second : Fragment() {
  private lateinit var secondBinding: FragmentSecondBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        secondBinding=FragmentSecondBinding.inflate(inflater,container,false)
        return secondBinding.root
    }
}