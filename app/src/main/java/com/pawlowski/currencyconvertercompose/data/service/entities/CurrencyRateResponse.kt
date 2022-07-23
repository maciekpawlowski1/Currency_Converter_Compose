package com.pawlowski.currencyconvertercompose.data.service.entities

data class CurrencyRateResponse(
    val id: Long,
    val from: String,
    val to: String,
    val exchangeRate: Double,
    val timestamp: String
)
