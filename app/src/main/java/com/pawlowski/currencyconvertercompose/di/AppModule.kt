package com.pawlowski.currencyconvertercompose.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.pawlowski.currencyconvertercompose.R
import com.pawlowski.currencyconvertercompose.RatesUpdateTimeManager
import com.pawlowski.currencyconvertercompose.RatesUpdateTimeManagerImpl
import com.pawlowski.currencyconvertercompose.data.RatesDao
import com.pawlowski.currencyconvertercompose.data.RatesDatabase
import com.pawlowski.currencyconvertercompose.data.service.RatesService
import com.pawlowski.currencyconvertercompose.domain.RatesRepository
import com.pawlowski.currencyconvertercompose.domain.RatesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
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

    @Singleton
    @Provides
    fun retrofit(context: Context): Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun ratesService(retrofit: Retrofit): RatesService
    {
        return retrofit.create(RatesService::class.java)
    }

    @Singleton
    @Provides
    fun sharedPreferences(context: Context): SharedPreferences
    {
        return context.getSharedPreferences("currencies_shared_preferences", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun ratesUpdateTimeManager(updateTimeManagerImpl: RatesUpdateTimeManagerImpl): RatesUpdateTimeManager
    {
        return updateTimeManagerImpl
    }
}