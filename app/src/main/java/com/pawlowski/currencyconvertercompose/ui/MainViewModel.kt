package com.pawlowski.currencyconvertercompose.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.pawlowski.currencyconvertercompose.domain.use_cases.GetAllRatesUseCase
import com.pawlowski.currencyconvertercompose.domain.use_cases.UpdateRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllRatesUseCase: GetAllRatesUseCase,
    private val updateRatesUseCase: UpdateRatesUseCase
): ViewModel() {
    val rates = getAllRatesUseCase.execute()

    var uiState by mutableStateOf(MainScreenUiState(true, "EUR", false))
        private set

    fun updateRates()
    {
        viewModelScope.launch {
            try {
                updateRatesUseCase.execute()
            }
            catch (e: Exception)
            {

            }

        }
    }

    fun changeFromTo(isFromSelected: Boolean)
    {
        uiState = uiState.copy(isFromSelected = isFromSelected)
    }

    fun changeChosenCurrency(currency: String)
    {
        uiState = uiState.copy(chosenCurrency = currency)
    }

    fun changeVisibilityOfDialog(isVisible: Boolean)
    {
        uiState = uiState.copy(isDialogVisible = isVisible)
    }



    init {
        updateRates()

    }
}