package com.pawlowski.currencyconvertercompose.domain.use_cases

import com.pawlowski.currencyconvertercompose.domain.RatesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateRatesUseCase @Inject constructor(private val repository: RatesRepository) {
    suspend fun execute()
    {
        withContext(Dispatchers.IO)
        {
            repository.updateRates()
        }
    }
}