package com.cusosandroid.practicas.view

// Importaciones para animación
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import kotlinx.coroutines.delay

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cusosandroid.practicas.R
import com.cusosandroid.practicas.components.DivisionButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConferenceView(navController: NavHostController, team: String, conferencia: String) {

    // --- Definición de Colores del Equipo (sin cambios) ---
    val lakersPrimary = Color(0xFF552583)
    val lakersSecondary = Color(0xFFFFB81C)
    val lakersTextOnSecondary = Color.Black

    val knicksPrimary = Color(0xFF006BB6)
    val knicksSecondary = Color(0xFFF58426)
    val knicksTextOnSecondary = Color.White

    val textOnPrimary = Color.White // Para TopAppBar y texto del Card parpadeante

    val currentTeamPrimaryColor: Color
    val currentTeamSecondaryColor: Color
    val currentTextOnSecondaryColor: Color

    val shortTeamName = when {
        team.contains("Lakers", ignoreCase = true) -> "Lakers"
        team.contains("Knicks", ignoreCase = true) -> "Knicks"
        else -> team
    }

    when (shortTeamName) {
        "Lakers" -> {
            currentTeamPrimaryColor = lakersPrimary
            currentTeamSecondaryColor = lakersSecondary
            currentTextOnSecondaryColor = lakersTextOnSecondary
        }

        "Knicks" -> {
            currentTeamPrimaryColor = knicksPrimary
            currentTeamSecondaryColor = knicksSecondary
            currentTextOnSecondaryColor = knicksTextOnSecondary
        }

        else -> {
            currentTeamPrimaryColor = Color(0xFF607D8B)
            currentTeamSecondaryColor = Color(0xFFB0BEC5)
            currentTextOnSecondaryColor = Color.Black
        }
    }

    // --- Constantes de estilo para el Card parpadeante ---
    val blinkingMessage = "Selecciona una división"
    val blinkingCardBackgroundColor = Color.DarkGray.copy(alpha = 0.8f)
    val blinkingCardTextColor = textOnPrimary // Usamos textOnPrimary (Blanco)
    val blinkingCardFontSize = 18.sp
    val blinkingCardPaddingBottom = 32.dp
    val blinkingCardInternalVerticalPadding = 8.dp
    val blinkingCardInternalHorizontalPadding = 16.dp

    // --- Lógica para el parpadeo ---
    var blinkAlphaTarget by remember { mutableStateOf(1f) }
    LaunchedEffect(Unit) {
        while (true) {
            blinkAlphaTarget = 0.3f
            delay(800)
            blinkAlphaTarget = 1f
            delay(800)
        }
    }
    val animatedBlinkAlpha by animateFloatAsState(
        targetValue = blinkAlphaTarget,
        animationSpec = tween(durationMillis = 700, easing = LinearEasing),
        label = "conferenceViewCardBlinkAlpha"
    )

    val divisions = when (conferencia) {
        "Oeste" -> listOf("Pacifico", "Noroeste", "Suroeste")
        "Este" -> listOf("Atlantico", "Central", "Sudeste")
        else -> emptyList()
    }

    // --- Lógica de Selección de Imágenes (ACTUALIZADA) ---

    // 1. Definimos TODAS las posibles imágenes por equipo y conferencia
    val allTeamImages = mapOf(
        "Lakers" to mapOf(
            "Este" to listOf(
                R.drawable.lakersatlantico,
                R.drawable.lakerscentral,
                R.drawable.lakerssudeste
            ),
            "Oeste" to listOf(
                R.drawable.lakers1212,
                R.drawable.lakersnoroeste,
                R.drawable.lakerssuroeste
            )
        ),
        "Knicks" to mapOf(
            "Este" to listOf(
                // ¡Asegúrate de que los nombres coincidan con los archivos que agregaste!
                R.drawable.new_york_knicks_division_atlantico,
                R.drawable.new_york_knicks_division_central,
                R.drawable.new_york_knicks_division_sudeste
            ),
            "Oeste" to listOf(
                R.drawable.new_york_knicks_division_pacifico,
                R.drawable.new_york_knicks_division_noroeste1,
                R.drawable.new_york_knicks_division_suroeste
            )
        )
        // Puedes añadir más equipos aquí en el futuro
        // "Bulls" to mapOf(...)
    )

    // 2. Seleccionamos la lista de imágenes correcta basándonos
    //    en el equipo actual (shortTeamName) y la conferencia.
    val divisionImages = allTeamImages[shortTeamName]?.get(conferencia) ?: listOf(
        // Lista de imágenes por defecto si no se encuentra el equipo/conferencia
        R.drawable.lakerss, // Una imagen genérica
        R.drawable.lakerss,
        R.drawable.lakerss
    )



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("División en $conferencia", color = textOnPrimary) },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = textOnPrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = currentTeamPrimaryColor,
                    titleContentColor = textOnPrimary,
                    navigationIconContentColor = textOnPrimary
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(currentTeamPrimaryColor)
        ) {
            Column(
                modifier = Modifier
                    .padding(paddingValues) // Padding de la TopAppBar
                    .fillMaxSize()
                    // Padding inferior para dejar espacio al Card parpadeante
                   // .padding(bottom = blinkingCardPaddingBottom + 40.dp) // Ajusta el 50.dp
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                // --- TÍTULO PRINCIPAL CON DEGRADADO ---
                val gradientBrush = Brush.verticalGradient(
                    colors = listOf(currentTeamSecondaryColor, textOnPrimary)
                )
                Text(
                    text = "SELECCIONA UNA DIVISION",
                    // Aplicamos un TextStyle que contiene el degradado
                    style = TextStyle(
                        brush = gradientBrush, // Usamos el Brush que creamos
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    ),
                    // Se elimina el parámetro 'color' porque el Brush lo reemplaza.
                    modifier = Modifier
                        .weight(1f)
                )

                if (divisions.isNotEmpty()) {
                    divisions.forEachIndexed { index, division ->
                        val imageRes = divisionImages.getOrNull(index) ?: R.drawable.lakerss

                        DivisionButton(
                            text = division,
                            backgroundImageRes = imageRes,
                            buttonColor = currentTeamSecondaryColor,
                            textColor = currentTextOnSecondaryColor,
                            onClick = {
                                navController.navigate("Detail/$team/$division")
                            },
                            // Altura ajustada para los botones de división
                            modifier = Modifier.height(230.dp) // Reducimos un poco para más espacio abajo
                        )
                        if (index < divisions.size - 1) {
                            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre botones ajustado
                        }
                    }
                }
            }
        }
    }
}




