package com.pawlowski.currencyconvertercompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pawlowski.currencyconvertercompose.CurrencyRate
import com.pawlowski.currencyconvertercompose.R
import com.pawlowski.currencyconvertercompose.ui.theme.CurrencyConverterComposeTheme

val defaultRatesForPreview =listOf(
    CurrencyRate( "PLN", "EUR", 5.1, "21.07.2021"),
    CurrencyRate( "PLN", "GBP", 2.15, "21.07.2021"),
    CurrencyRate( "PLN", "USD", 3.47, "21.07.2021"),
    CurrencyRate( "PLN", "RUB", 193.4, "21.07.2021"),
    CurrencyRate( "PLN", "CHF", 3.47, "21.07.2021"),
    CurrencyRate( "PLN", "NOK", 3.49, "21.07.2021"),
    CurrencyRate( "PLN", "SEK", 3.16, "21.07.2021"))

val flagsId = mapOf(Pair("PLN", R.drawable.pln_flag),
    Pair("EUR", R.drawable.eu),
    Pair("GBP", R.drawable.gb),
    Pair("USD", R.drawable.us),
    Pair("CHF", R.drawable.ch),
    Pair("RUB", R.drawable.ru),
    Pair("NOK", R.drawable.no),
    Pair("SEK", R.drawable.se),
    Pair("DKK", R.drawable.dk),
    Pair("CZK", R.drawable.cz),
    Pair("HUF", R.drawable.hu),
    Pair("RON", R.drawable.ro),
    Pair("ISK", R.drawable.`is`),
    Pair("UAH", R.drawable.ua),
    Pair("HRK", R.drawable.hr),
    Pair("RSD", R.drawable.rs),
    Pair("BGN", R.drawable.bg),
    Pair("BYN", R.drawable.by),
    Pair("BAM", R.drawable.ba),
    Pair("MKD", R.drawable.mk),
    Pair("ALL", R.drawable.al),
    Pair("GEL", R.drawable.ge),
    Pair("MDL", R.drawable.md),
    )

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = viewModel()
    val isFromSelected = viewModel.isFromSelected.asFlow().collectAsState(initial = true).value

    //Rates from EURO to other currencies
    val rates = viewModel.rates.collectAsState(initial = listOf())
    val chosenCurrency = viewModel.chosenCurrency.asFlow().collectAsState(initial = "EUR").value //TODO: Add choosing currency

    val mappedRates = remember(rates.value, chosenCurrency, isFromSelected) {
        generateRatesForChosenCurrency(rates.value, chosenCurrency, isFromSelected)
    }
    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = CenterHorizontally) {
            CurrencyChoosePanel(10.dp,
                isFromSelected,
                chosenCurrency,
                {
                    viewModel.changeFromTo(it)
                },
                {
                    viewModel.changeVisibilityOfDialog(true)
                }
            )
            OtherCurrenciesPrice(mappedRates, 30.dp, isFromSelected)
        }
    }

    if(viewModel.isDialogVisible.asFlow().collectAsState(initial = false).value)
    {
        CurrenciesDialog(countries = rates.value.map { it.to },
            onCurrencyChoose = {
            viewModel.changeChosenCurrency(it)
            viewModel.changeVisibilityOfDialog(false)
        }) {
            viewModel.changeVisibilityOfDialog(false)
        }
    }
}


fun generateRatesForChosenCurrency(rates: List<CurrencyRate>, chosenCurrency: String, isFromSelected: Boolean): List<CurrencyRate>
{
    return if(rates.isEmpty())
        listOf()
    else if(chosenCurrency == "EUR")
    {
        if(isFromSelected)
            rates
        else
            rates.map { CurrencyRate(it.to, it.from, 1/it.exchangeRate, it.timestamp) }
    }
    else {
        val chosenRate = rates.first { it.to == chosenCurrency }
        if (isFromSelected) {
            rates.map {
                it.copy(
                    from = chosenCurrency,
                    exchangeRate = it.exchangeRate / chosenRate.exchangeRate
                )
            }
        } else {
            rates.map {
                it.copy(
                    from = it.to, to = chosenCurrency,
                    exchangeRate = chosenRate.exchangeRate / it.exchangeRate
                )
            }
        }
    }
}


@Composable
fun CurrencyChoosePanel(paddingTop: Dp, isFromSelected: Boolean, chosenCurrency: String, onFromToChangeClick: (isFrom: Boolean) -> Unit, onChooseCurrencyClick: () -> Unit)
{
    Card(shape = CutCornerShape(30.dp), modifier = Modifier
        .fillMaxWidth(0.9f)
        .wrapContentHeight()
        .padding(top = paddingTop),
        elevation = 5.dp
    ) {
        Column(modifier = Modifier.padding(15.dp), horizontalAlignment = CenterHorizontally) {
            FromOrToSwap(isFromSelected)
            {
                onFromToChangeClick.invoke(it)
            }
            Card(elevation = 10.dp, modifier = Modifier
                .width(200.dp)
                .requiredHeight(110.dp)
                .padding(top = 10.dp, bottom = 10.dp)
                .clickable { onChooseCurrencyClick.invoke() }
            ) {
                Row(verticalAlignment = CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(15.dp)) {
                    CountryFlag(id = flagsId[chosenCurrency]?:R.drawable.flag_icon)
                    Text(text = chosenCurrency, style = MaterialTheme.typography.h6, fontWeight = FontWeight.W400)
                    Icon(imageVector = Icons.Default.ArrowDropDown, modifier = Modifier.size(30.dp), contentDescription = "Choose")

                }
            }

        }


    }
}

@Composable
fun FromOrToSwap(isFromSelected: Boolean, onFromToChangeClick: (isFrom: Boolean) -> Unit)
{

    Card(modifier = Modifier.height(50.dp), elevation = 5.dp) {
        Row()
        {
            Surface(color = if(isFromSelected) Color.LightGray else Color.White, modifier = Modifier.clickable { onFromToChangeClick.invoke(true) }) {
                Text(text = "From:", modifier = Modifier.padding(start = 15.dp, end= 5.dp, bottom = 10.dp, top = 10.dp), style = MaterialTheme.typography.h5, fontWeight = if(isFromSelected) FontWeight.W700 else FontWeight.W300)
            }
            VerticalDivider()
            Surface(color = if(!isFromSelected) Color.LightGray else Color.White, modifier = Modifier.clickable { onFromToChangeClick.invoke(false) }) {
                Text(text = "To:", modifier = Modifier.padding(start = 15.dp, end= 25.dp, bottom = 10.dp, top = 10.dp), style = MaterialTheme.typography.h5, fontWeight = if(!isFromSelected) FontWeight.W700 else FontWeight.W300)
            }
        }
    }
}

@Composable
fun CountryFlag(id: Int = R.drawable.flag_icon)
{
    Card(shape = CircleShape, modifier = Modifier.size(50.dp)) {
        Image(painter = painterResource(id = id), contentDescription = "Country flag", contentScale = ContentScale.Crop)
    }
}

@Composable
fun OtherCurrenciesPrice(rates: List<CurrencyRate>, topPadding: Dp, isFromSelected: Boolean)
{
    Card(shape = CutCornerShape(topEnd = 20.dp), elevation = 5.dp, modifier = Modifier
        .wrapContentHeight()
        .fillMaxWidth(0.9f)
        .padding(top = topPadding)) {
        LazyColumn()
        {
            items(rates)
            {
                OneCurrencyCard(if(isFromSelected) it.to else it.from, it.exchangeRate)
            }
        }
    }

}

@Composable
fun OneCurrencyCard(currencyName: String, price: Double)
{
    Column {
        Row(verticalAlignment = CenterVertically)
        {
            Row(
                Modifier
                    .padding(start = 10.dp)
                    .requiredHeight(80.dp)
                    .fillMaxWidth(0.65f), verticalAlignment = CenterVertically, horizontalArrangement = Arrangement.Start) {
                CountryFlag(id = flagsId[currencyName]?:R.drawable.flag_icon)
                Text(text = currencyName, Modifier.padding(horizontal = 10.dp), style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.W500)

            }
            Row(
                Modifier
                    .padding()
                    .requiredHeight(80.dp)
                    .fillMaxWidth(), verticalAlignment = CenterVertically, horizontalArrangement = Arrangement.End)
            {
                VerticalDivider()
                Text(text = String.format("%.2f",price), modifier = Modifier.padding(horizontal = 10.dp), style = MaterialTheme.typography.body1, fontWeight = FontWeight.W400)
            }
        }

        Divider()
    }
}


@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onSurface.copy(alpha = DividerAlpha),
    thickness: Dp = 1.dp
) {
    Box(
        modifier
            .fillMaxHeight()
            .width(thickness)
            .background(color = color)
    )
}

private const val DividerAlpha = 0.12f

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CurrencyConverterComposeTheme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = CenterHorizontally) {
                CurrencyChoosePanel(10.dp, true, "PLN", {}, {})

                OtherCurrenciesPrice(defaultRatesForPreview, 30.dp, true)
            }
        }
    }
}