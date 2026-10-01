package com.example.restcountries

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.restcountries.ui.CountryApp
import com.example.restcountries.ui.theme.RestCountriesTheme
//hola
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            RestCountriesTheme {
                CountryApp()
            }
        }
    }
}