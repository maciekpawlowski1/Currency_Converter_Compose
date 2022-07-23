package com.pawlowski.currencyconvertercompose.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyRateEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val from: String,
    val to: String,
    val exchangeRate: Double,
    val timestamp: String
)