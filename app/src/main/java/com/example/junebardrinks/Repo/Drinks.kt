package com.example.junebardrinks.Repo

import android.content.Context
import androidx.compose.runtime.Composable
import com.example.junebardrinks.DataClass.Cocktail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun readCocktailsFromFile(context: Context): List<Cocktail> {
    val jsonString = context.assets.open("../json/drinks.json").bufferedReader().use {
        it.readText()
    }
    val listCocktailType = object : TypeToken<List<Cocktail>>() {}.type
    return Gson().fromJson(jsonString, listCocktailType)
}