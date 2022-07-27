package com.pawlowski.currencyconvertercompose.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pawlowski.currencyconvertercompose.domain.use_cases.GetAllRatesUseCase
import com.pawlowski.currencyconvertercompose.domain.use_cases.UpdateRatesUseCase
import com.pawlowski.currencyconvertercompose.domain.use_cases.WasRatesRecentlyUpdatedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllRatesUseCase: GetAllRatesUseCase,
    private val updateRatesUseCase: UpdateRatesUseCase,
    private val wasRatesRecentlyUpdatedUseCase: WasRatesRecentlyUpdatedUseCase
): ViewModel() {
    val rates = getAllRatesUseCase.execute()

    var uiState by mutableStateOf(MainScreenUiState(
        isFromSelected = true, chosenCurrency = "EUR", isDialogVisible = false, isRefreshing = false))
        private set

    private val _isUpdating = MutableStateFlow(false)
    val isUpdating: StateFlow<Boolean> get() = _isUpdating

    private fun updateRates()
    {
        changeRefreshingState(true)
        _isUpdating.value = true
        viewModelScope.launch {
            try {
                updateRatesUseCase.execute()
            }
            catch (e: Exception)
            {

            }
            finally {
                _isUpdating.value = false
                changeRefreshingState(false)
            }

        }
    }

    fun updateRatesIfNeeded()
    {
        if(!wasRatesRecentlyUpdatedUseCase())
        {
            updateRates()
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

    fun changeRefreshingState(isRefreshing: Boolean)
    {
        if(isRefreshing == uiState.isRefreshing)
            return
        else
            uiState = uiState.copy(isRefreshing = isRefreshing)
    }

    init {
        updateRatesIfNeeded()
    }
}