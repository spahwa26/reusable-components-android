package com.app.retrofitexample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.retrofitexample.databinding.ActivityNetworkBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NetworkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNetworkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}