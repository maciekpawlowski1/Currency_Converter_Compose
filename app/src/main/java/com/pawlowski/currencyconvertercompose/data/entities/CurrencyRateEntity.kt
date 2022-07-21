package com.pawlowski.currencyconvertercompose.data.entities

import androidx.room.Entity

@Entity
data class CurrencyRateEntity(
    val id: Long,
    val from: String,
    val to: String,
    val exchangeRate: Double,
    val timestamp: String
)