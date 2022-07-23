package com.pawlowski.currencyconvertercompose

data class CurrencyRate(
    val from: String,
    val to: String,
    val exchangeRate: Double,
    val timestamp: String
)
