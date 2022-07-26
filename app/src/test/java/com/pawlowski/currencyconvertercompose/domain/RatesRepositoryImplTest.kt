package com.pawlowski.currencyconvertercompose.domain

import com.pawlowski.currencyconvertercompose.data.RatesDao
import com.pawlowski.currencyconvertercompose.data.entities.CurrencyRateEntity
import com.pawlowski.currencyconvertercompose.data.service.RatesService
import com.pawlowski.currencyconvertercompose.data.service.entities.CurrencyRateResponse
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class RatesRepositoryImplTest {

    private val defaultRates = listOf(
        CurrencyRateEntity( 0, "PLN", "EUR", 5.1, "21.07.2021"),
        CurrencyRateEntity( 0, "PLN", "GBP", 2.15, "21.07.2021"),
        CurrencyRateEntity( 0, "PLN", "USD", 3.47, "21.07.2021"),

    )

    private val defaultSuccessfulResponse = Response.success(listOf(
        CurrencyRateResponse(1, "PLN", "EUR", 5.1, "21.07.2021"),
        CurrencyRateResponse(2, "PLN", "GBP", 2.15, "21.07.2021"),
        CurrencyRateResponse(3, "PLN", "USD", 3.47, "21.07.2021"),

        ))



    private lateinit var SUT: RatesRepositoryImpl

    @Mock
    lateinit var serviceMock: RatesService

    @Mock
    lateinit var daoMock: RatesDao

    @Before
    fun setUp()
    {
        SUT = RatesRepositoryImpl(ratesService = serviceMock, ratesDao = daoMock)
    }

    @Test
    fun getRates() {
        SUT.getRates()
        verify(daoMock).getAllRates()
        verifyNoMoreInteractions(daoMock, serviceMock)
    }

    @Test
    fun deleteRates() {
        runBlocking {
            SUT.deleteRates()
            verify(daoMock).deleteAllRates()
            verifyNoMoreInteractions(daoMock, serviceMock)
        }

    }

    @Test
    fun insertRates() {
        runBlocking {
            SUT.insertRates(defaultRates)
            verify(daoMock).insertRates(defaultRates)
            verifyNoMoreInteractions(daoMock, serviceMock)
        }
    }

    @Test
    fun updateRates_successfulResponse() {

        runBlocking {
            `when`(serviceMock.getRates()).thenReturn(defaultSuccessfulResponse)
            SUT.updateRates()
            verify(serviceMock).getRates()
            verify(daoMock).deleteAllRates()
            verify(daoMock).insertRates(defaultRates)
            verifyNoMoreInteractions(daoMock, serviceMock)
        }
    }


}