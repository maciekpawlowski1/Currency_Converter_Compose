package com.pawlowski.currencyconvertercompose.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.pawlowski.currencyconvertercompose.MyApplication
import com.pawlowski.currencyconvertercompose.data.RatesDao
import com.pawlowski.currencyconvertercompose.data.RatesDatabase
import com.pawlowski.currencyconvertercompose.domain.RatesRepository
import com.pawlowski.currencyconvertercompose.domain.RatesRepositoryImpl
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun context(application: Application): Context
    {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun ratesDatabase(context: Context):RatesDatabase
    {
        return Room.databaseBuilder(context, RatesDatabase::class.java, "RatesDatabase")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun ratesDao(ratesDatabase: RatesDatabase): RatesDao
    {
        return ratesDatabase.ratesDao()
    }

    @Singleton
    @Provides
    fun ratesRepository(ratesRepositoryImpl: RatesRepositoryImpl): RatesRepository
    {
        return ratesRepositoryImpl
    }
}