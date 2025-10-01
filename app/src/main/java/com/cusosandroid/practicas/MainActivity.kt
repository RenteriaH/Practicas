package com.cusosandroid.practicas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.cusosandroid.practicas.navigation.NavManager
import com.cusosandroid.practicas.ui.theme.PracticasTheme
import com.cusosandroid.practicas.view.ConferenceSelectorView
import com.cusosandroid.practicas.view.ConferenceView
import com.cusosandroid.practicas.view.DetailsView
import com.cusosandroid.practicas.view.HomeView
import com.cusosandroid.practicas.view.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticasTheme {
                NavManager()
            }
          }
        }
    }

@Preview(showBackground = true)
@Composable
            fun GreetingPreview() {
                PracticasTheme {
                    NavManager()
                }

        }

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController() // NavController simulado
    PracticasTheme {
        HomeView(navController = navController)
    }
}
@Preview(showBackground = true)
@Composable
fun ConferenceSelectorView() {
    val navController = rememberNavController() // NavController simulado
    PracticasTheme {
        ConferenceSelectorView(navController = navController,"Lakers")
    }
}

@Preview(showBackground = true)
@Composable
fun ConferencePreview() {
    val navController = rememberNavController() // NavController simulado
    PracticasTheme {
        ConferenceView(navController = navController,"Los Ageles Lakers","Oeste")
    }
}
@Preview(showBackground = true)
@Composable
fun DetailsPreview() {
    val navController = rememberNavController() // simulación del NavController
    PracticasTheme {
        DetailsView(navController = navController,"Los Angeles Lakers","Pacifico") // Division de ejemplo
    }
}

