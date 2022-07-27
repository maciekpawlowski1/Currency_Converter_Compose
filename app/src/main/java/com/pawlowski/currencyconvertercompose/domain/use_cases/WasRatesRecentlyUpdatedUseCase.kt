package com.pawlowski.currencyconvertercompose.domain.use_cases

import com.pawlowski.currencyconvertercompose.RatesUpdateTimeManager
import javax.inject.Inject

class WasRatesRecentlyUpdatedUseCase @Inject constructor(private val updateTimeManager: RatesUpdateTimeManager) {

    operator fun invoke(): Boolean
    {
        return updateTimeManager.wasRecentlyUpdated()
    }


}