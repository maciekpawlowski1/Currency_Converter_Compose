package com.pawlowski.currencyconvertercompose.domain

import com.pawlowski.currencyconvertercompose.data.RatesDao
import com.pawlowski.currencyconvertercompose.data.entities.CurrencyRateEntity
import com.pawlowski.currencyconvertercompose.data.service.RatesService
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RatesRepositoryImpl @Inject constructor(
    private val ratesDao: RatesDao,
    private val ratesService: RatesService
    ): RatesRepository {


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
        val response = ratesService.getRates()
        if(response.isSuccessful)
        {
            response.body()?.let {
                withContext(NonCancellable)
                {
                    deleteRates()
                    insertRates(it.map { CurrencyRateEntity(0, it.from, it.to, it.exchangeRate, it.timestamp) })
                }
            }
        }
    }
}