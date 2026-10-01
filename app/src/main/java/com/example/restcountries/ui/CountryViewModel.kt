package com.example.restcountries.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restcountries.data.Country
import com.example.restcountries.data.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Loading : UiState()

    data class Success(
        val countries: List<Country>
    ) : UiState()

    data class Error(
        val message: String
    ) : UiState()
}

class CountryViewModel : ViewModel() {

    private val repository = CountryRepository()

    private val _uiState =
        MutableStateFlow<UiState>(UiState.Loading)

    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    private val _selectedCountry =
        MutableStateFlow<Country?>(null)

    val selectedCountry: StateFlow<Country?> =
        _selectedCountry.asStateFlow()

    init {
        getAllCountries()
    }

    fun getAllCountries() {

        viewModelScope.launch {

            _uiState.value = UiState.Loading

            try {

                val countries =
                    repository.getAllCountries()

                _uiState.value =
                    UiState.Success(countries)

            } catch (e: Exception) {

                _uiState.value =
                    UiState.Error(
                        e.message ?: "Error desconocido"
                    )
            }
        }
    }

    fun getCountriesByRegion(region: String) {

        viewModelScope.launch {

            _uiState.value = UiState.Loading

            try {

                val countries =
                    repository.getCountriesByRegion(region)

                _uiState.value =
                    UiState.Success(countries)

            } catch (e: Exception) {

                _uiState.value =
                    UiState.Error(
                        e.message ?: "Error al consultar el continente"
                    )
            }
        }
    }

    fun getCountryDetail(name: String) {

        viewModelScope.launch {

            try {

                val country =
                    repository.getCountryByName(name)

                _selectedCountry.value = country

            } catch (e: Exception) {

                _selectedCountry.value = null
            }
        }
    }

    fun clearSelectedCountry() {
        _selectedCountry.value = null
    }
}