package com.example.junebardrinks.DataClass

data class Cocktail(
    val name: String,
    val price_dkk: Int,
    val glass: String,val ice: String,
    val recipe: List<String>,
    val instructions: String,
    val garnish: List<String>
)