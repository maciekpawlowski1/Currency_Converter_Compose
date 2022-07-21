package com.pawlowski.currencyconvertercompose

data class CurrencyRate(
    val id: Long,
    val from: String,
    val to: String,
    val exchangeRate: Double,
    val timestamp: String
)
