package com.example.reusablecomponents.utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {
    var binding: T? = null
        private set

    abstract fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initBinding(inflater, container)
        return binding?.root
    }

    inline fun requireBinding(bind: T.() -> Unit) = binding?.let(bind)

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun hideKeyBoard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view?.let { v -> imm.hideSoftInputFromWindow(v.windowToken, 0) }
    }

    fun showKeyBoard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view?.let { v -> imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT) }
    }

    fun View.showKeyBoard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        this.let { v -> imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT) }
    }
}