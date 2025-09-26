package com.cusosandroid.practicas.view

import com.cusosandroid.practicas.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cusosandroid.practicas.components.TextView
import com.cusosandroid.practicas.ui.theme.Purple40
import androidx.compose.ui.res.painterResource

@Composable
fun HomeView(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Mitad superior: Lakers
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clickable { navController.navigate("ConferenceSelector/Lakers") },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.lakers2),
                contentDescription = "Lakers",
                modifier = Modifier.fillMaxSize() ,
                contentScale = ContentScale.Crop // LLENA TODO EL ESPACIO

            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                TextView(texto = "Los Angeles Lakers", color = Color.White, fontSize = 24.sp)

            }

        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clickable { navController.navigate("ConferenceSelector/Knicks") },
                contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.new_york_knicks),
                contentDescription = "Knicks",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                TextView(texto = "New York Knicks", color = Color.White, fontSize = 24.sp)
            }
        }
    }
}