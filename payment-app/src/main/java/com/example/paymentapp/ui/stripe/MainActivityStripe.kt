package com.example.paymentapp.ui.stripe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.paymentapp.databinding.StripeActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivityStripe : AppCompatActivity() {
    private lateinit var binding: StripeActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StripeActivityMainBinding.inflate(layoutInflater)
        return setContentView(binding.root)
    }
}