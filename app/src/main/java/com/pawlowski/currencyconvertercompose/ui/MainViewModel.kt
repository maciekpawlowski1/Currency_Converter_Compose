package com.pawlowski.currencyconvertercompose.ui

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

    private val _isFromSelected = MutableLiveData(true)
    val isFromSelected: LiveData<Boolean> get() = _isFromSelected

    private val _chosenCurrency = MutableLiveData("EUR")
    val chosenCurrency: LiveData<String> get() = _chosenCurrency

    private val _isDialogVisible = MutableLiveData(false)
    val isDialogVisible: LiveData<Boolean> = _isDialogVisible



    fun updateRates()
    {
        viewModelScope.launch {
            updateRatesUseCase.execute()
        }
    }

    fun changeFromTo(isFromSelected: Boolean)
    {
        _isFromSelected.value = isFromSelected
    }

    fun changeChosenCurrency(currency: String)
    {
        _chosenCurrency.value = currency
    }

    fun changeVisibilityOfDialog(isVisible: Boolean)
    {
        _isDialogVisible.value = isVisible
    }



    init {
        //updateRates()
        
    }
}