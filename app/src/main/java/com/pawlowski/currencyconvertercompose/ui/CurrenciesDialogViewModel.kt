package com.pawlowski.currencyconvertercompose.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CurrenciesDialogViewModel @Inject constructor(
): ViewModel() {

    private val _dialogSearchByInput = MutableLiveData("")
    val dialogSearchByInput: LiveData<String> get() = _dialogSearchByInput

    fun changeSearchByInputValue(searchByValue: String)
    {
        _dialogSearchByInput.value = searchByValue
    }
}