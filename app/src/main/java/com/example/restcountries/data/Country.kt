package com.example.restcountries.data

import com.google.gson.annotations.SerializedName

data class Country(

    @SerializedName("names")
    val name: CountryName?,

    @SerializedName("flag")
    val flags: CountryFlags?,

    val region: String?,

    val subregion: String?,

    @SerializedName("capitals")
    val capital: List<Capital>?,

    val population: Long?,

    val currencies: List<Currency>?,

    val languages: List<Language>?
)

data class CountryName(
    val common: String?,
    val official: String?
)

data class CountryFlags(
    @SerializedName("url_png")
    val png: String?,

    @SerializedName("url_svg")
    val svg: String?,

    val emoji: String?
)

data class Capital(
    val name: String?
)

data class Currency(
    val code: String?,
    val name: String?,
    val symbol: String?
)

data class Language(
    val name: String?,
    val bcp47: String?
)