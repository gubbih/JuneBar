package com.example.junebardrinks

import JunebarTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.example.junebardrinks.dataClass.Cocktail
import com.example.junebardrinks.dataClass.CocktailList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JunebarTheme{
                val navController = rememberNavController()
                val cocktailList = loadCocktails()
                //could be moved to a navHost
                NavHost(navController = navController, startDestination = "cocktail_list") {
                    composable("cocktail_list") {
                        CocktailListScreen(cocktails = cocktailList, navController = navController)
                    }
                    composable("cocktail_details/{name}") { backStackEntry ->
                        val name = backStackEntry.arguments?.getString("name")
                        val cocktail = cocktailList.firstOrNull { it.name == name }
                        cocktail?.let { CocktailDetailScreen(it) }
                    }
                }
            }
        }
    }
    //after 3 hours of googling i got this code, not 100% sure what it does yet but it works
    // moshi and adapter needs some research soon
    private fun loadCocktails(): List<Cocktail> {
        //Reads json file
        val json = assets.open("Drinks.json").bufferedReader().use { it.readText() }
        // Builds a Moshi instance with KotlinJsonAdapterFactory for Kotlin compatibility.
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        // Creates an adapter to convert JSON to the CocktailList class.
        val adapter = moshi.adapter(CocktailList::class.java)
        // Parses the JSON string into a CocktailList object and returns its cocktails list,
        // or an empty list if parsing fails.
        return adapter.fromJson(json)?.cocktails ?: emptyList()
    }
}

//could be moved to a view
@Composable
fun CocktailListScreen(cocktails: List<Cocktail>, navController: NavHostController) {
    //could be a little smaller but works great
    LazyColumn(modifier = Modifier.padding(16.dp) ) {
        items(cocktails.size) { index ->
            val cocktail = cocktails[index]
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .clickable {
                    navController.navigate("cocktail_details/${cocktail.name}")
                }) {
                Text(
                    text = cocktail.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Divider()
        }
    }
}
// view
@Composable
fun CocktailDetailScreen(cocktail: Cocktail) {
    //find some better styling for it #GoogleIsMyLife
    val instructionsSplit = cocktail.instructions.split(";")
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = cocktail.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        Text(text = "Price: ${cocktail.price_dkk} DKK",color = MaterialTheme.colorScheme.onSurface)
        Text(text = "Glass: ${cocktail.glass}", style = MaterialTheme.typography.headlineSmall,color = MaterialTheme.colorScheme.onSurface)
        Text(text = "Ice: ${cocktail.ice ?: "None"}", style = MaterialTheme.typography.headlineSmall,color = MaterialTheme.colorScheme.onSurface)
        Text(text = "Recipe:", style = MaterialTheme.typography.headlineSmall,color = MaterialTheme.colorScheme.onSurface)
        cocktail.recipe.forEach { ingredient ->
            Text(text = "- $ingredient", modifier = Modifier.padding(start = 8.dp), style = MaterialTheme.typography.titleLarge,color = MaterialTheme.colorScheme.onSurface)
        }
        Text(text = "Instructions:", style = MaterialTheme.typography.titleLarge,color = MaterialTheme.colorScheme.onSurface)
        instructionsSplit.forEach { instruction ->
            Text(text = "- $instruction", modifier = Modifier.padding(start = 8.dp), style = MaterialTheme.typography.titleLarge,color = MaterialTheme.colorScheme.onSurface)
        }
        Text(text = "Garnish:", style = MaterialTheme.typography.titleLarge,color = MaterialTheme.colorScheme.onSurface)
        cocktail.garnish.forEach { garnishItem ->
            Text(text = "- $garnishItem", modifier = Modifier.padding(start = 8.dp), style = MaterialTheme.typography.titleLarge,color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

// not needed anymore
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val sampleCocktail = Cocktail(
        name = "Sample Cocktail",
        price_dkk = 100,
        glass = "Lowball",
        ice = "Cubed",
        recipe = listOf("4cl Gin", "2cl Lemon Juice"),
        instructions = "Shake ingredients and serve.",
        garnish = listOf("Lemon Slice")
    )
    CocktailDetailScreen(cocktail = sampleCocktail)
}