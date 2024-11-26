package com.example.paymentapp.data.stripe.models

data class CustomerInfo(
    val address: String,
    val balance: Int,
    val created: Int,
    val currency: String,
    val default_source: String,
    val delinquent: Boolean,
    val description: String,
    val discount: String,
    val email: String,
    val id: String,
    val invoice_prefix: String,
    val invoice_settings: InvoiceSettings,
    val livemode: Boolean,
    val metadata: Metadata,
    val name: String,
    val next_invoice_sequence: Int,
    val phone: String,
    val preferred_locales: List<String>,
    val shipping: String,
    val tax_exempt: String,
    val test_clock: String
)