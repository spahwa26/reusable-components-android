package com.example.reusablecomponents.loaders

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.reusablecomponents.databinding.ActivityLoadersBinding

class LoaderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoadersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadersBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}


