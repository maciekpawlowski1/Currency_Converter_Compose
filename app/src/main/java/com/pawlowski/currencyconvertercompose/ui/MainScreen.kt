package com.pawlowski.currencyconvertercompose.ui

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pawlowski.currencyconvertercompose.CurrencyRate
import com.pawlowski.currencyconvertercompose.R
import com.pawlowski.currencyconvertercompose.ui.theme.CurrencyConverterComposeTheme
import kotlinx.coroutines.delay

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
    Pair("CAD", R.drawable.ca),
    Pair("MXN", R.drawable.mx),
    Pair("PAB", R.drawable.pa),
    Pair("CRC", R.drawable.cr),
    Pair("CUP", R.drawable.cu),
    Pair("HNL", R.drawable.hn),
    Pair("NIO", R.drawable.ni),
    Pair("JMD", R.drawable.jm),
    Pair("BBD", R.drawable.bb),
    Pair("HTG", R.drawable.ht),
    Pair("JPY", R.drawable.jp),
    Pair("INR", R.drawable.`in`),
    Pair("KRW", R.drawable.kr),
    Pair("CNY", R.drawable.cn),
    Pair("HKD", R.drawable.hk),
    Pair("TWD", R.drawable.tw),
    Pair("SGD", R.drawable.sg),
    Pair("MOP", R.drawable.mo),
    Pair("THB", R.drawable.th),
    Pair("IDR", R.drawable.id),
    Pair("MYR", R.drawable.my),
    Pair("PHP", R.drawable.ph),
    Pair("VND", R.drawable.vn),
    Pair("BND", R.drawable.bn),
    Pair("BDT", R.drawable.bd),
    Pair("PKR", R.drawable.pk),
    Pair("KZT", R.drawable.kz),
    Pair("UZS", R.drawable.uz),
    Pair("KGS", R.drawable.kg),
    Pair("TJS", R.drawable.tj),
    Pair("NPR", R.drawable.np),
    Pair("MNT", R.drawable.mn),
    Pair("MMK", R.drawable.mm),
    Pair("KHR", R.drawable.kh),
    Pair("LAK", R.drawable.la),
    Pair("LKR", R.drawable.lk),
    Pair("MVR", R.drawable.mv),
    Pair("AED", R.drawable.ae),
    Pair("SAR", R.drawable.sa),
    Pair("IRR", R.drawable.ir),
    Pair("IQD", R.drawable.iq),
    Pair("TRY", R.drawable.tr),
    Pair("ILS", R.drawable.il),
    Pair("QAR", R.drawable.qa),
    Pair("KWD", R.drawable.kw),
    Pair("SYP", R.drawable.sy),
    Pair("JOD", R.drawable.jo),
    Pair("YER", R.drawable.ye),
    Pair("OMR", R.drawable.om),
    Pair("LBP", R.drawable.lb),
    Pair("BHD", R.drawable.bh),
    Pair("AFN", R.drawable.af),
    Pair("AZN", R.drawable.az),
    Pair("AMD", R.drawable.am),
    Pair("BRL", R.drawable.br),
    Pair("ARS", R.drawable.ar),
    Pair("CLP", R.drawable.cl),
    Pair("COP", R.drawable.co),
    Pair("PEN", R.drawable.pe),
    Pair("VED", R.drawable.ve),
    Pair("UYU", R.drawable.uy),
    Pair("GTQ", R.drawable.gt),
    Pair("BOB", R.drawable.bo),
    Pair("PYG", R.drawable.py),
    Pair("TTD", R.drawable.tt),
    Pair("GYD", R.drawable.gy),
    Pair("AWG", R.drawable.aw),
    Pair("AUD", R.drawable.au),
    Pair("NZD", R.drawable.nz),
    Pair("FJD", R.drawable.fj),
    Pair("PGK", R.drawable.pg),
    Pair("XPF", R.drawable.pf),
    Pair("ZAR", R.drawable.za),
    Pair("NGN", R.drawable.ng),
    Pair("EGP", R.drawable.eg),
    Pair("MAD", R.drawable.ma),
    Pair("DZD", R.drawable.dz),
    Pair("TND", R.drawable.tn),
    Pair("KES", R.drawable.ke),
    Pair("TZS", R.drawable.tz),
    Pair("AOA", R.drawable.ao),
    Pair("MUR", R.drawable.mu),
    Pair("NAD", R.drawable.na),
    Pair("ZMW", R.drawable.zm),
    Pair("SCR", R.drawable.sc),
    Pair("GHS", R.drawable.gh),
    Pair("LYD", R.drawable.ly),
    Pair("ETB", R.drawable.et),
    Pair("UGX", R.drawable.ug),
    Pair("BWP", R.drawable.bw),
    Pair("MGA", R.drawable.mg),
    Pair("MWK", R.drawable.mw),
    Pair("MZN", R.drawable.mz),
    Pair("GMD", R.drawable.gm),
    Pair("CVE", R.drawable.cv),
    Pair("ERN", R.drawable.er),
    Pair("SVC", R.drawable.sv),
    Pair("BTC", R.drawable.btc),
    )

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = viewModel()
    val isFromSelected = viewModel.uiState.isFromSelected

    //Rates from EURO to other currencies
    val rates = viewModel.rates.collectAsState(initial = listOf())
    val chosenCurrency = viewModel.uiState.chosenCurrency

    val mappedRates = remember(rates.value, chosenCurrency, isFromSelected) {
        generateRatesForChosenCurrency(rates.value, chosenCurrency, isFromSelected)
    }

    val refreshState = rememberSwipeRefreshState(isRefreshing = viewModel.uiState.isRefreshing)

    Scaffold(modifier = Modifier.fillMaxSize(), content = {
        SwipeRefresh(state = refreshState,
            onRefresh = {
                viewModel.changeRefreshingState(true)
                viewModel.updateRatesIfNeeded()
                        },
            modifier = Modifier.fillMaxSize()) {
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

            if(viewModel.uiState.isDialogVisible)
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
    })

    LaunchedEffect(viewModel.uiState.isRefreshing)
    {
        if(viewModel.uiState.isRefreshing)
        {
            delay(1000)
            //If it's not updating (update was not needed)
            if(!viewModel.isUpdating.value)
                viewModel.changeRefreshingState(false)
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
                .testTag("choose_currency_card")
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
        AsyncImage(model=ImageRequest.Builder(LocalContext.current).data(id).build(), contentDescription = "Country flag", contentScale = ContentScale.Crop)
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
                Text(text = currencyName,
                    Modifier
                        .padding(horizontal = 10.dp)
                        .testTag("rate_tittle"), style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.W500)

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