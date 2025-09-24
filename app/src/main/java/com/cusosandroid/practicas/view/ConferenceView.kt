package com.cusosandroid.practicas.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.cusosandroid.practicas.components.MainButton
import com.cusosandroid.practicas.components.Space
import com.cusosandroid.practicas.components.TitleBar


@Composable
fun ConferenceView(navController: NavHostController, team: String) {
    val divisions = when (team) {
        "Lakers" -> listOf("Pacífico", "Noroeste", "Suroeste", "Oeste Extra")
        "Knicks" -> listOf("Atlántico", "Central", "Sureste", "Este Extra")
        else -> emptyList()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBar("Conferencias de $team")

        Space(20)

        divisions.forEachIndexed { index, division ->
            MainButton(
                name = division,
                backColor = Color.Blue,
                color = Color.White
            ) {
                navController.navigate("Detail/$index")
            }
            Space(10)
        }
    }
}