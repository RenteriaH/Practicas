package com.cusosandroid.practicas.view

import com.cusosandroid.practicas.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cusosandroid.practicas.components.ActionButton


@Composable
fun ConferenceSelectorView(navController: NavHostController, team: String) {
    // Colores por equipo
    val teamBackground = when (team) {
        "Lakers" -> Color(0xFF552583) // morado
        "Knicks" -> Color(0xFF006BB6) // azul
        else -> Color.Gray
    }

    val teamButtonColor = when (team) {
        "Lakers" -> Color(0xFFFFB81C) // amarillo
        "Knicks" -> Color(0xFFF58426) // naranja
        else -> Color.LightGray
    }
    // Nombre completo del equipo
    val teamFullName = when (team) {
        "Lakers" -> "Los Angeles Lakers"
        "Knicks" -> "New York Knicks"
        else -> team
    }

    // Imágenes de fondo
    val oesteImage = when (team) {
        "Lakers" -> R.drawable.oestelackers
        "Knicks" -> R.drawable.lakerss
        else -> R.drawable.lakerss
    }
    val esteImage = when (team) {
        "Lakers" -> R.drawable.lakerss
        "Knicks" -> R.drawable.lakerss
        else -> R.drawable.lakerss
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(teamBackground)
    ) {
        // Encabezado centrado
        Text(
            text = "Selecciona la Conferencia de  $teamFullName",
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp,top = 25.dp,10.dp, bottom = 10.dp),
               // .align(Alignment.CenterHorizontally),
            maxLines = 2
        )

        // Botón "Oeste" con imagen de fondo
        Box(
            modifier = Modifier
                .weight(1f) // ocupa la mitad de la pantalla vertical
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = oesteImage),
                contentDescription = "Oeste",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            // Botón centrado (más pequeño)
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(12.dp))
                    .background(teamButtonColor.copy(alpha = 0.9f))
                    .clickable { navController.navigate("ConferenceView/Oeste") }
                    .padding(horizontal = 32.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Oeste",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Botón "Este" con imagen de fondo
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = esteImage),
                contentDescription = "Este",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            // Botón centrado (más pequeño)
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(12.dp))
                    .background(teamButtonColor.copy(alpha = 0.9f))
                    .clickable { navController.navigate("ConferenceView/Este") }
                    .padding(horizontal = 32.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Este",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

            }
        }
        Spacer(modifier = Modifier.height(30.dp))

    }
}