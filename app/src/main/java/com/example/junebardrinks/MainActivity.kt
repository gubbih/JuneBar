package com.example.junebardrinks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.junebardrinks.Repo.readCocktailsFromFile
import com.example.junebardrinks.ui.theme.JunebarDrinksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JunebarDrinksTheme {
                CocktailListScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
@Composable
fun CocktailListScreen() {
    val context = LocalContext.current
    val cocktails = readCocktailsFromFile(context)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        cocktails.forEach { cocktail ->
            Text(
                text = cocktail.name,
                modifier = Modifier.clickable {
                    // Handle click to show full details (e.g., navigate to a details screen)
                    // You can pass the cocktail object to the details screen
                    println("Clicked on: ${cocktail.name}") // Replace with your navigation logic
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JunebarDrinksTheme {
        Greeting("Android")
    }
}