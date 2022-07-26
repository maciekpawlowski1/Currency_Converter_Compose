package com.pawlowski.currencyconvertercompose.data.service

import com.pawlowski.currencyconvertercompose.CurrencyRate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers


interface RatesService {

    //Example ApiKey, should be hidden if moved to production
    @Headers("ApiKey: ajtwyepo17dsahjzusdjksdjk213909a32389sdjkaeeqamjwj")
    @GET("api/rates")
    suspend fun getRates(): Response<List<CurrencyRate>>
}