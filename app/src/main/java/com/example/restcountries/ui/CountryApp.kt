package com.example.restcountries.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.restcountries.data.Country

@Composable
fun CountryApp(
    viewModel: CountryViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val selectedCountry by
    viewModel.selectedCountry.collectAsStateWithLifecycle()

    if (selectedCountry != null) {

        CountryDetailScreen(
            country = selectedCountry!!,
            onBack = {
                viewModel.clearSelectedCountry()
            }
        )

    } else {

        CountryListScreen(
            uiState = uiState,
            onAllCountries = {
                viewModel.getAllCountries()
            },
            onRegionSelected = {
                viewModel.getCountriesByRegion(it)
            },
            onCountrySelected = {
                viewModel.getCountryDetail(it)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListScreen(
    uiState: UiState,
    onAllCountries: () -> Unit,
    onRegionSelected: (String) -> Unit,
    onCountrySelected: (String) -> Unit
) {

    var menuExpanded by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text("🌎 Country Explorer")
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {

                Button(
                    onClick = onAllCountries,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Todos")
                }

                Box(
                    modifier = Modifier.weight(1f)
                ) {

                    Button(
                        onClick = {
                            menuExpanded = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Continente")
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = {
                            menuExpanded = false
                        }
                    ) {

                        DropdownMenuItem(
                            text = {
                                Text("África")
                            },
                            onClick = {
                                menuExpanded = false
                                onRegionSelected("Africa")
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("América")
                            },
                            onClick = {
                                menuExpanded = false
                                onRegionSelected("Americas")
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("Asia")
                            },
                            onClick = {
                                menuExpanded = false
                                onRegionSelected("Asia")
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("Europa")
                            },
                            onClick = {
                                menuExpanded = false
                                onRegionSelected("Europe")
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("Oceanía")
                            },
                            onClick = {
                                menuExpanded = false
                                onRegionSelected("Oceania")
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("Antártida")
                            },
                            onClick = {
                                menuExpanded = false
                                onRegionSelected("Antarctic")
                            }
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            when (uiState) {

                is UiState.Loading -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(
                            horizontalAlignment =
                                Alignment.CenterHorizontally
                        ) {

                            CircularProgressIndicator()

                            Spacer(
                                modifier =
                                    Modifier.height(12.dp)
                            )

                            Text("Cargando países...")
                        }
                    }
                }

                is UiState.Success -> {

                    CountryList(
                        countries = uiState.countries,
                        onCountrySelected =
                            onCountrySelected
                    )
                }

                is UiState.Error -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(
                            horizontalAlignment =
                                Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = "❌ Error",
                                style =
                                    MaterialTheme.typography.headlineSmall
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(8.dp)
                            )

                            Text(
                                text = uiState.message
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(16.dp)
                            )

                            Button(
                                onClick = onAllCountries
                            ) {
                                Text("Reintentar")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CountryList(
    countries: List<Country>,
    onCountrySelected: (String) -> Unit
) {

    LazyColumn(
        verticalArrangement =
            Arrangement.spacedBy(8.dp)
    ) {

        items(
            items = countries,
            key = {
                it.name?.common ?: ""
            }
        ) { country ->

            CountryItem(
                country = country,
                onClick = {

                    country.name?.common?.let {
                        onCountrySelected(it)
                    }
                }
            )
        }
    }
}

@Composable
fun CountryItem(
    country: Country,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            AsyncImage(
                model = country.flags?.png,
                contentDescription =
                    "Bandera de ${country.name?.common}",
                modifier = Modifier.size(70.dp)
            )

            Spacer(
                modifier = Modifier.size(16.dp)
            )

            Column {

                Text(
                    text =
                        country.name?.common
                            ?: "Sin nombre",
                    style =
                        MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )

                Text(
                    text =
                        "Región: ${country.region ?: "N/A"}"
                )

                Text(
                    text =
                        "Población: ${
                            country.population ?: 0
                        }"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailScreen(
    country: Country,
    onBack: () -> Unit
) {

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text(
                        country.name?.common
                            ?: "Detalle"
                    )
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            AsyncImage(
                model = country.flags?.png,
                contentDescription = "Bandera",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )

            Text(
                text =
                    country.name?.common
                        ?: "Sin nombre",
                style =
                    MaterialTheme.typography.headlineMedium
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            Text(
                text =
                    "Nombre oficial: ${
                        country.name?.official ?: "N/A"
                    }"
            )

            Text(
                text =
                    "Capital: ${
                        country.capital
                            ?.mapNotNull { it.name }
                            ?.joinToString(", ")
                            ?: "N/A"
                    }"
            )

            Text(
                text =
                    "Región: ${
                        country.region ?: "N/A"
                    }"
            )

            Text(
                text =
                    "Subregión: ${
                        country.subregion ?: "N/A"
                    }"
            )

            Text(
                text =
                    "Población: ${
                        country.population ?: 0
                    }"
            )

            val languages =
                country.languages
                    ?.mapNotNull { it.name }
                    ?.joinToString(", ")
                    ?: "N/A"

            Text(
                text =
                    "Idiomas: $languages"
            )

            val currency =
                country.currencies
                    ?.firstOrNull()

            Text(
                text =
                    "Moneda: ${
                        currency?.name ?: "N/A"
                    } ${
                        currency?.symbol ?: ""
                    }"
            )

            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("← Regresar")
            }
        }
    }
}