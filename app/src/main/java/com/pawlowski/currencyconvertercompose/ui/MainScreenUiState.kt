package com.pawlowski.currencyconvertercompose.ui

data class MainScreenUiState(
    val isFromSelected: Boolean,
    val chosenCurrency: String,
    val isDialogVisible: Boolean,
    val isRefreshing: Boolean
)
