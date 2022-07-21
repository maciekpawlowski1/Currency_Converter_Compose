package com.pawlowski.currencyconvertercompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pawlowski.currencyconvertercompose.CurrencyRate
import com.pawlowski.currencyconvertercompose.data.entities.CurrencyRateEntity

@Database(entities = [CurrencyRateEntity::class], version = 1)
abstract class RatesDatabase: RoomDatabase() {
    abstract fun ratesDao(): RatesDao
}