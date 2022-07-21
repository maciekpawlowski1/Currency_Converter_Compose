package com.pawlowski.currencyconvertercompose.domain

import com.pawlowski.currencyconvertercompose.data.RatesDao
import com.pawlowski.currencyconvertercompose.data.entities.CurrencyRateEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RatesRepositoryImpl @Inject constructor(private val ratesDao: RatesDao): RatesRepository {


    override fun getRates(): Flow<List<CurrencyRateEntity>> {
        return ratesDao.getAllRates()
    }

    override suspend fun deleteRates() {
        ratesDao.deleteAllRates()
    }

    override suspend fun insertRates(rates: List<CurrencyRateEntity>) {
        ratesDao.insertRates(rates)
    }

    override suspend fun updateRates() {
        TODO("Not yet implemented")
    }
}