package com.cusosandroid.practicas.navigation

import androidx.compose.runtime.Composable
import com.cusosandroid.practicas.view.SplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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

        // Conferencias según el equipo
        composable(
            route = "Conference/{team}",
            arguments = listOf(navArgument("team") { type = NavType.StringType })
        ) {
            val team = it.arguments?.getString("team") ?: ""
            ConferenceView(navController, team)
        }

        // Detalle de una conferencia
        composable(
            route = "Detail/{conferenceId}",
            arguments = listOf(navArgument("conferenceId") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("conferenceId") ?: 0
            DetailsView(navController, id)
        }
    }
}

