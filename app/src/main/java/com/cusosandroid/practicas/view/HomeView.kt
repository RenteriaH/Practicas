package com.cusosandroid.practicas.view

import com.cusosandroid.practicas.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cusosandroid.practicas.components.Space
import com.cusosandroid.practicas.components.TextView
import com.cusosandroid.practicas.ui.theme.Purple40



@Composable
fun HomeView(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Mitad superior: Lakers
        Box(
            modifier = Modifier
                .weight(1f)
                .background(Purple40)
                .fillMaxWidth()
                .clickable { navController.navigate("Conference/Lakers") },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.lakerss),
                contentDescription = "Lakers",
                modifier = Modifier.fillMaxSize()
            )
        }

        // Mitad inferior: Knicks
        Box(
            modifier = Modifier
                .weight(1f)
                .background(Color.Blue)
                .fillMaxWidth()
                .clickable { navController.navigate("Conference/Knicks") },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.knicks),
                contentDescription = "Knicks",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}