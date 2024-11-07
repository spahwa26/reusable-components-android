package com.example.paymentapp.data.stripe.models

data class InvoiceSettings(
    val custom_fields: Any,
    val default_payment_method: Any,
    val footer: Any,
    val rendering_options: Any
)