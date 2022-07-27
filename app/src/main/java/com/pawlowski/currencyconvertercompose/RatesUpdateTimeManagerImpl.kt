package com.pawlowski.currencyconvertercompose

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs


@Singleton
class RatesUpdateTimeManagerImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    RatesUpdateTimeManager {
    override fun wasRecentlyUpdated(): Boolean
    {
        val lastUpdated = synchronized(this)
        {
            return@synchronized sharedPreferences.getLong(LAST_UPDATE_TIME_TAG, 0)
        }
        return (abs(System.currentTimeMillis()-lastUpdated) > UPDATE_DELTA_MINUTES *1000)
    }

    override fun saveUpdateTime()
    {
        synchronized(this)
        {
            sharedPreferences.edit().putLong(LAST_UPDATE_TIME_TAG, System.currentTimeMillis()).apply()
        }
    }

    companion object
    {
        const val UPDATE_DELTA_MINUTES = 60
        const val LAST_UPDATE_TIME_TAG = "last_update_time"
    }
}