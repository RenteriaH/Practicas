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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cusosandroid.practicas.R

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

    val conferenceImagesMap = mapOf(
        "Oeste" to listOf(
            R.drawable.lakerspacifico,
            R.drawable.lakersnoroeste,
            R.drawable.lakerssuroeste
        ),
        "Este" to listOf(
            R.drawable.lakersatlantico,
            R.drawable.lakerscentral,
            R.drawable.lakerssudeste
        )
    )
    val divisionImages = conferenceImagesMap[conferencia] ?: emptyList()

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
                    .padding(bottom = blinkingCardPaddingBottom + 40.dp) // Ajusta el 50.dp
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Selecciona una división",
                    color = textOnPrimary,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 0.dp, bottom = 16.dp)
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
                            modifier = Modifier.height(210.dp) // Reducimos un poco para más espacio abajo
                        )
                        if (index < divisions.size - 1) {
                            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre botones ajustado
                        }
                    }
                } else {
                    Text(
                        text = "No hay divisiones disponibles para esta conferencia.",
                        color = textOnPrimary,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                // El Spacer con weight(1f) se elimina o se ajusta si queremos que el Card esté siempre visible
                // Si el contenido es corto, este Spacer empujaría el Card parpadeante hacia abajo (lo cual es bueno)
                // Si el contenido + el Card ya llenan la pantalla, este Spacer no tendrá mucho efecto visible.
                Spacer(modifier = Modifier.weight(1f)) // Para empujar el contenido hacia arriba y el Card al fondo
            } // Fin de la Column de contenido principal

            // Card parpadeante
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = blinkingCardPaddingBottom
                    )
                    .alpha(animatedBlinkAlpha),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = blinkingCardBackgroundColor)
            ) {
                Text(
                    text = blinkingMessage,
                    color = blinkingCardTextColor,
                    fontSize = blinkingCardFontSize,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(
                            horizontal = blinkingCardInternalHorizontalPadding,
                            vertical = blinkingCardInternalVerticalPadding
                        )
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun DivisionButton(
    text: String,
    backgroundImageRes: Int,
    buttonColor: Color,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier // Aceptamos un modifier que incluye la altura
) {
    Card(
        modifier = modifier // Aplicamos el modifier aquí
            // .height(230.dp) // La altura ahora se pasa desde la llamada
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = backgroundImageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
                    .align(Alignment.TopCenter)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.30f)
                    .background(buttonColor)
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = textColor,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
