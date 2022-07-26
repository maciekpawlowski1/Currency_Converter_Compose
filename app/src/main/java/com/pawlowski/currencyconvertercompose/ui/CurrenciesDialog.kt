package com.pawlowski.currencyconvertercompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pawlowski.currencyconvertercompose.R


@Composable
fun CurrenciesDialog(countries: List<String>, onCurrencyChoose: (String) -> Unit, onDismissDialog: () -> Unit)
{

    val searchByInputState = rememberSaveable {
        mutableStateOf("")
    }

    val dialogSearchByInput = searchByInputState.value

    Dialog(onDismissRequest = {
        searchByInputState.value = ""
        onDismissDialog.invoke()
    }) {
        Box(modifier = Modifier.fillMaxHeight(0.9f).testTag("dialog_box"), contentAlignment = Alignment.Center) {
            Card(modifier = Modifier.wrapContentSize(), elevation = 5.dp) {
                Column {

                    SearchBar(dialogSearchByInput)
                    {
                        searchByInputState.value = it
                    }
                    val filteredCountries = remember(countries, dialogSearchByInput)
                    {
                        countries.filter { it.contains(dialogSearchByInput, ignoreCase = true) }
                    }
                    LazyColumn()
                    {
                        items(filteredCountries)
                        { item ->
                            CountryChooseItem(currencyName = item)
                            {
                                searchByInputState.value = ""
                                onCurrencyChoose.invoke(it)
                            }
                        }
                    }
                }
            }

        }

    }
}

@Composable
fun SearchBar(searchByInput: String, onSearchByInputChange: (String) -> Unit)
{

    Box(
        modifier = Modifier
            .padding(10.dp)
            .border(width = 1.dp, color = Color.Gray, shape = CircleShape)
            .fillMaxWidth()
    ) {
        BasicTextField(
            value = searchByInput,
            onValueChange = {
                onSearchByInputChange.invoke(it)
            },
            modifier = Modifier
                .background(Color.White, CircleShape)
                .height(38.dp)
                .fillMaxWidth()
                .testTag("search_field"),
            singleLine = true,
            maxLines = 1,
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "image",
                        tint = Color.Blue
                    )
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (searchByInput.isEmpty()) Text(
                            "Search"
                        )
                        innerTextField()
                    }
                    if (searchByInput.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                onSearchByInputChange.invoke("")
                            },
                            modifier = Modifier.testTag("search_close_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "image",
                                tint = Color.Blue
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun CountryChooseItem(currencyName: String, onCurrencyChoose: (String) -> Unit)
{
    Column(modifier=Modifier.clickable { onCurrencyChoose.invoke(currencyName) }) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)) {
            CountryFlag(flagsId[currencyName]?: R.drawable.flag_icon)
            Text(text = currencyName, modifier = Modifier.padding(horizontal = 10.dp).testTag("search_rate_tittle"), style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.W500)
        }
        Divider()
    }

}

@Preview(showBackground = true)
@Composable
fun DialogPreview()
{
    CurrenciesDialog(countries = defaultRatesForPreview.map { it.to }, onCurrencyChoose = {}, {})
}

