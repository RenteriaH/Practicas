package com.cusosandroid.practicas.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cusosandroid.practicas.components.MainButton

import kotlin.to
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.cusosandroid.practicas.R



@Composable
fun ConferenceView(navController: NavHostController, team: String) {
    // Divisiones correctas por conferencia
    val divisions = when (team) {
        "Oeste" -> listOf("Pacífico", "Noroeste", "Suroeste")
        "Este" -> listOf("Atlántico", "Central", "Sudeste")
        else -> emptyList()
    }

    // Imágenes de fondo por división
    val conferenceImages = mapOf(
        "Oeste" to listOf(
            R.drawable.lakerspacifico,    // Pacífico
            R.drawable.lakersnoroeste,    // Noroeste
            R.drawable.lakerssuroeste      // Suroeste
        ),
        "Este" to listOf(
            R.drawable.lakersatlantico,   // Atlántico
            R.drawable.lakerscentral,     // Central
            R.drawable.lakerssudeste      // Sudeste
        )
    )

    val images = conferenceImages[team] ?: emptyList()

    // Colores de fondo y botón por conferencia
    val backgroundColor = when (team) {
        "Oeste" -> Color(0xFF1E1E2F) // oscuro para Oeste
        "Este" -> Color(0xFF003366)  // azul para Este
        else -> Color.Gray
    }
    val buttonColor = when (team) {
        "Oeste" -> Color(0xFFFFB81C) // amarillo
        "Este" -> Color(0xFFF58426)  // naranja
        else -> Color.LightGray
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado centrado
        Text(
            text = "Selecciona una división de la Conferencia $team",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 12.dp)
        )

        // Filas de divisiones
        divisions.forEachIndexed { index, division ->
            Box(
                modifier = Modifier
                    .weight(1f) // cada fila ocupa 1/3 de la pantalla
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                // Imagen de fondo
                if (index < images.size) {
                    Image(
                        painter = painterResource(id = images[index]),
                        contentDescription = division,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                }

                // Botón centrado y pequeño
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(12.dp))
                        .background(buttonColor.copy(alpha = 0.9f))
                        .padding(horizontal = 32.dp, vertical = 12.dp)
                        .clickable { navController.navigate("Detail/$index") },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = division,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
    }

}