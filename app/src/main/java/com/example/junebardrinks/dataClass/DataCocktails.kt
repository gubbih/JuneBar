package com.example.junebardrinks.dataClass

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cocktail(
    val name: String,
    val price_dkk: Int,
    val glass: String,
    val ice: String?,
    val recipe: List<String>,
    val instructions: String,
    val garnish: List<String>
)

@JsonClass(generateAdapter = true)
data class CocktailList(val cocktails: List<Cocktail>)