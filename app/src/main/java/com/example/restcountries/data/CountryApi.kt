package com.example.restcountries.data

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CountryApi {

    @GET("countries/v5")
    suspend fun getAllCountries(
        @Header("Authorization") authorization: String
    ): CountryResponse

    @GET("countries/v5/region/{region}")
    suspend fun getCountriesByRegion(
        @Path("region") region: String,
        @Header("Authorization") authorization: String
    ): CountryResponse

    @GET("countries/v5/names.common/{name}")
    suspend fun getCountryByName(
        @Path("name") name: String,
        @Header("Authorization") authorization: String
    ): CountryResponse
}

data class CountryResponse(
    val data: CountryData?
)

data class CountryData(
    val objects: List<Country>?
)