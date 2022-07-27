package com.pawlowski.currencyconvertercompose

interface RatesUpdateTimeManager {
    fun wasRecentlyUpdated(): Boolean

    fun saveUpdateTime()
}