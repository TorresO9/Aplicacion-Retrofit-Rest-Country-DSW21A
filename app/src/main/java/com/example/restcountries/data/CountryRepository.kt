package com.example.restcountries.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountryRepository {

    private val api: CountryApi

    private val apiKey = "rc_live_b1f01c75f88d4762b1d8d8886df81db9"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.restcountries.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(CountryApi::class.java)
    }

    suspend fun getAllCountries(): List<Country> {
        val response = api.getAllCountries(
            "Bearer $apiKey"
        )

        return response.data?.objects ?: emptyList()
    }

    suspend fun getCountriesByRegion(
        region: String
    ): List<Country> {

        val response = api.getCountriesByRegion(
            region,
            "Bearer $apiKey"
        )

        return response.data?.objects ?: emptyList()
    }

    suspend fun getCountryByName(
        name: String
    ): Country {

        val response = api.getCountryByName(
            name,
            "Bearer $apiKey"
        )

        return response.data?.objects?.first()
            ?: throw Exception("País no encontrado")
    }
}