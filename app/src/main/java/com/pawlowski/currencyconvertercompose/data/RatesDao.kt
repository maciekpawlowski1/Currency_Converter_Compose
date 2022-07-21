package com.pawlowski.currencyconvertercompose.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.pawlowski.currencyconvertercompose.data.entities.CurrencyRateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RatesDao {

    @Insert
    suspend fun insertRates(rates: List<CurrencyRateEntity>)

    @Query("SELECT * FROM CurrencyRateEntity")
    fun getAllRates(): Flow<List<CurrencyRateEntity>>

    @Query("SELECT COUNT(*) FROM CurrencyRateEntity")
    suspend fun getRatesCount(): Int


    @Query("DELETE FROM CurrencyRateEntity")
    suspend fun deleteAllRates()
}