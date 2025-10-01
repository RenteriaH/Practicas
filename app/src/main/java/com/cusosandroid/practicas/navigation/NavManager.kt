package com.cusosandroid.practicas.navigation

import androidx.compose.runtime.Composable
import com.cusosandroid.practicas.view.SplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cusosandroid.practicas.view.ConferenceSelectorView
import com.cusosandroid.practicas.view.ConferenceView
import com.cusosandroid.practicas.view.HomeView
import com.cusosandroid.practicas.view.DetailsView
@Composable
fun NavManager() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Splash") {

        // Splash
        composable("Splash") {
            SplashScreen(navController)
        }
        // Home: Lakers / Knicks
        composable("Home") {
            HomeView(navController)
        }

// Selector de Conferencia (Este/Oeste)
        composable(
            route = "ConferenceSelector/{team}",
            arguments = listOf(navArgument("team") { type = NavType.StringType })
        ) {
            val team = it.arguments?.getString("team") ?: ""
            ConferenceSelectorView(navController, team)
        }

        // Vista de Conferencia (divisiones)
        composable(
            route = "Conference/{team}/{conference}",
            arguments = listOf(
                navArgument("team") { type = NavType.StringType },
                navArgument("conference") { type = NavType.StringType }
            )
        ) {
            val team = it.arguments?.getString("team") ?: ""
            val conference = it.arguments?.getString("conference") ?: ""
            ConferenceView(navController, team,conference)
        }

        // Vista de Detalles (equipo + división)
        composable(
            route = "Detail/{team}/{division}",
            arguments = listOf(
                navArgument("team") { type = NavType.StringType },
                navArgument("division") { type = NavType.StringType }
            )
        ) {
            val team = it.arguments?.getString("team") ?: ""
            val division = it.arguments?.getString("division") ?: ""
            DetailsView(navController, team, division)
        }


    }
}

