package com.pawlowski.currencyconvertercompose.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pawlowski.currencyconvertercompose.R

@Composable
fun CurrenciesDialog(countries: List<String>, onCurrencyChoose: (String) -> Unit, onDismissDialog: () -> Unit)
{
    Dialog(onDismissRequest = { onDismissDialog.invoke() }) {

        androidx.compose.material.Surface(modifier = Modifier.wrapContentSize(), color = Color.LightGray) {
            LazyColumn()
            {
                items(countries)
                {
                    CountryChooseItem(currencyName = it)
                }
            }
        }
    }
}

@Composable
fun CountryChooseItem(currencyName: String)
{
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp)) {
        CountryFlag(flagsId[currencyName]?: R.drawable.flag_icon)
        Text(text = currencyName)
    }
}

@Preview(showBackground = true)
@Composable
fun DialogPreview()
{
    CurrenciesDialog(countries = defaultRatesForPreview.map { it.to }, onCurrencyChoose = {}, {})
}

