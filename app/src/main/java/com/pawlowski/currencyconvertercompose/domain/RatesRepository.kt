package com.pawlowski.currencyconvertercompose.domain

import com.pawlowski.currencyconvertercompose.data.entities.CurrencyRateEntity
import kotlinx.coroutines.flow.Flow

interface RatesRepository {
    fun getRates(): Flow<List<CurrencyRateEntity>>
    suspend fun deleteRates()
    suspend fun insertRates(rates: List<CurrencyRateEntity>)
    suspend fun updateRates()
}