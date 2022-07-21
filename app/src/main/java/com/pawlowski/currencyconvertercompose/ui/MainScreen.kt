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
import com.pawlowski.currencyconvertercompose.CurrencyRate
import com.pawlowski.currencyconvertercompose.R
import com.pawlowski.currencyconvertercompose.ui.theme.CurrencyConverterComposeTheme

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
    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = CenterHorizontally) {
            CurrencyChoosePanel(10.dp)
            {

            }
            OtherCurrenciesPrice(listOf(
                CurrencyRate(1, "PLN", "EUR", 5.1, "21.07.2021"),
                CurrencyRate(1, "PLN", "GBP", 2.15, "21.07.2021"),
                CurrencyRate(1, "PLN", "USD", 3.47, "21.07.2021"),
                CurrencyRate(1, "PLN", "RUB", 193.4, "21.07.2021"),
                CurrencyRate(1, "PLN", "CHF", 3.47, "21.07.2021"),
                CurrencyRate(1, "PLN", "NOK", 3.49, "21.07.2021"),
                CurrencyRate(1, "PLN", "SEK", 3.16, "21.07.2021"),
            ), 30.dp)
        }
    }
}

@Composable
fun CurrencyChoosePanel(paddingTop: Dp, onChooseCurrencyClick: () -> Unit)
{
    Card(shape = CutCornerShape(30.dp), modifier = Modifier
        .fillMaxWidth(0.9f)
        .wrapContentHeight()
        .padding(top = paddingTop),
        elevation = 5.dp
    ) {
        Column(modifier = Modifier.padding(15.dp), horizontalAlignment = CenterHorizontally) {
            Text(text = "From:", style = MaterialTheme.typography.h5, fontWeight = FontWeight.W500)
            Card(elevation = 10.dp, modifier = Modifier
                .width(200.dp)
                .requiredHeight(110.dp)
                .padding(top = 10.dp, bottom = 10.dp)
                .clickable { onChooseCurrencyClick.invoke() }
            ) {
                Row(verticalAlignment = CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(15.dp)) {
                    CountryFlag(id = flagsId["PLN"]?:R.drawable.flag_icon)
                    Text(text = "PLN", style = MaterialTheme.typography.h6, fontWeight = FontWeight.W400)
                    Icon(imageVector = Icons.Default.ArrowDropDown, modifier = Modifier.size(30.dp), contentDescription = "Choose")

                }
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
fun OtherCurrenciesPrice(rates: List<CurrencyRate>, topPadding: Dp)
{
    Card(shape = CutCornerShape(topEnd = 20.dp), elevation = 5.dp, modifier = Modifier
        .wrapContentHeight()
        .fillMaxWidth(0.9f)
        .padding(top = topPadding)) {
        LazyColumn()
        {
            items(rates)
            {
                OneCurrencyCard(it.to, it.exchangeRate)
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
        MainScreen()
    }
}