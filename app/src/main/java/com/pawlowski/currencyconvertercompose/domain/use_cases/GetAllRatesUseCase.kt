package com.pawlowski.currencyconvertercompose.domain.use_cases

import com.pawlowski.currencyconvertercompose.CurrencyRate
import com.pawlowski.currencyconvertercompose.domain.RatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllRatesUseCase @Inject constructor(private val ratesRepository: RatesRepository) {
    fun execute(): Flow<List<CurrencyRate>>
    {
        return ratesRepository.getRates().map { list -> list.map { CurrencyRate(it.from, it.to, it.exchangeRate, it.timestamp) } }
    }
}