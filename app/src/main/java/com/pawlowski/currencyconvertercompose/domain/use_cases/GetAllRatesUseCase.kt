package com.pawlowski.currencyconvertercompose.domain.use_cases

import com.pawlowski.currencyconvertercompose.data.entities.CurrencyRateEntity
import com.pawlowski.currencyconvertercompose.domain.RatesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllRatesUseCase @Inject constructor(private val ratesRepository: RatesRepository) {
    fun execute(): Flow<List<CurrencyRateEntity>>
    {
        return ratesRepository.getRates()
    }
}