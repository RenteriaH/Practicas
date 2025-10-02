package com.cusosandroid.practicas.view

// ... (importaciones sin cambios) ...
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import kotlinx.coroutines.delay

import com.cusosandroid.practicas.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConferenceSelectorView(navController: NavHostController, team: String) {

    // --- Colores y lógica de equipo (sin cambios) ---
    val lakersPrimary = Color(0xFF552583)
    val lakersSecondary = Color(0xFFFFB81C)
    val lakersTextOnSecondary = Color.Black

    val knicksPrimary = Color(0xFF006BB6)
    val knicksSecondary = Color(0xFFF58426)
    val knicksTextOnSecondary = Color.White

    val textOnPrimary = Color.White

    // Color para el fondo del Card parpadeante, igual que en HomeView
    val blinkingCardBackgroundColor = Color.DarkGray.copy(alpha = 0.8f) // Igual que HomeView
    val blinkingCardTextColor = Color.White // Igual que HomeView
    val blinkingCardFontSize = 18.sp // Igual que HomeView
    val blinkingCardPaddingBottom = 32.dp // Igual que HomeView, ajusta si es necesario
    val blinkingCardInternalVerticalPadding = 8.dp // Igual que HomeView
    val blinkingCardInternalHorizontalPadding = 16.dp // Igual que HomeView

    val currentTeamPrimaryColor: Color
    val currentTeamSecondaryColor: Color
    val currentTextOnSecondaryColor: Color

    when (team) {
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

    val teamFullName = when (team) {
        "Lakers" -> "Los Angeles Lakers"
        "Knicks" -> "New York Knicks"
        else -> team
    }

    // ... (lógica de imágenes sin cambios) ...
    val oesteLakersImage = R.drawable.los_angeles_lakers_conferencia_oeste
    val esteLakersImage = R.drawable.los_angeles_lakers_conferencia_este
    val oesteKnicksImage = R.drawable.new_york_knicks_conferencia_oeste
    val esteKnicksImage = R.drawable.new_york_knicks_conferencia_este
    val defaultConferenceImage = R.drawable.oestelackers

    val imagenParaConferenciaOeste = when (team) {
        "Lakers" -> oesteLakersImage
        "Knicks" -> oesteKnicksImage
        else -> defaultConferenceImage
    }

    val imagenParaConferenciaEste = when (team) {
        "Lakers" -> esteLakersImage
        "Knicks" -> esteKnicksImage
        else -> defaultConferenceImage
    }


    // --- Lógica para el parpadeo ---
    var blinkAlphaTarget by remember { mutableStateOf(1f) }
    LaunchedEffect(Unit) {
        while (true) {
            blinkAlphaTarget = 0.3f
            delay(800) // Puedes ajustar estos valores para que coincidan con HomeView si son diferentes
            blinkAlphaTarget = 1f
            delay(800)
        }
    }
    val animatedBlinkAlpha by animateFloatAsState(
        targetValue = blinkAlphaTarget,
        animationSpec = tween(durationMillis = 700, easing = LinearEasing),
        label = "conferenceSelectorCardBlinkAlpha"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Seleccionar Conferencia", color = textOnPrimary) },
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
                )
            )
        }
    ) { paddingValues ->
        // Box para permitir la superposición del Card parpadeante
        Box(modifier = Modifier.fillMaxSize().background(currentTeamPrimaryColor)) {
            Column(
                modifier = Modifier
                    .padding(paddingValues) // Padding de la TopAppBar
                    .fillMaxSize()
                    // Aplicamos un padding inferior a la columna para dejar espacio al Card
                    .padding(bottom = blinkingCardPaddingBottom + 40.dp) // Espacio para el Card + un poco más
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top // Mantenemos el contenido principal arriba
            ) {
                // --- TÍTULO PRINCIPAL CON DEGRADADO ---
                val gradientBrush = Brush.verticalGradient(
                    colors = listOf(currentTeamSecondaryColor, textOnPrimary)
                )
                Text(
                    text = "Conferencia para\n$teamFullName",
                    // 1. Aplicamos un TextStyle que contiene el degradado
                    style = TextStyle(
                        brush = gradientBrush, // El Brush que ya tienes definido
                        fontSize = 32.sp,      // Vamos a darle un tamaño de fuente explícito
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 36.sp     // Un poco más de altura de línea para que no se vea apretado
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 16.dp)
                )

                ConferenceButton(
                    text = "Oeste",
                    backgroundImageRes = imagenParaConferenciaOeste,
                    buttonColor = currentTeamSecondaryColor,
                    textColor = currentTextOnSecondaryColor,
                    onClick = {
                        val encodedConference = URLEncoder.encode("Oeste", StandardCharsets.UTF_8.toString())
                        navController.navigate("Conference/$teamFullName/$encodedConference")
                    },
                    modifier = Modifier.height(300.dp) // Altura de botones ajustada para dejar espacio
                )

                Spacer(modifier = Modifier.height(20.dp)) // Espacio entre botones ajustado

                ConferenceButton(
                    text = "Este",
                    backgroundImageRes = imagenParaConferenciaEste,
                    buttonColor = currentTeamSecondaryColor,
                    textColor = currentTextOnSecondaryColor,
                    onClick = {
                        val encodedConference = URLEncoder.encode("Este", StandardCharsets.UTF_8.toString())
                        navController.navigate("Conference/$teamFullName/$encodedConference")
                    },
                    modifier = Modifier.height(300.dp) // Altura de botones ajustada
                )
            } // Fin de la columna de contenido principal

            // Card parpadeante, alineado al estilo de HomeView
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter) // Alinea el Card en la parte inferior del Box
                    .fillMaxWidth() // Ocupa todo el ancho
                    // Padding laterales y el padding inferior que definimos
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        bottom = blinkingCardPaddingBottom
                    )
                    .alpha(animatedBlinkAlpha), // El Card completo parpadea
                shape = RoundedCornerShape(12.dp), // Misma forma
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Misma elevación
                colors = CardDefaults.cardColors(containerColor = blinkingCardBackgroundColor) // Mismo color de fondo
            ) {
                Text(
                    text = "Seleccionar Conferencia",
                    color = blinkingCardTextColor, // Mismo color de texto
                    fontSize = blinkingCardFontSize, // Mismo tamaño de fuente
                    fontWeight = FontWeight.SemiBold, // Puedes ajustar esto si en HomeView era diferente
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding( // Mismos paddings internos
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
fun ConferenceButton(
    text: String,
    backgroundImageRes: Int,
    buttonColor: Color,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier // Aceptamos un modifier
) {
    Card(
        modifier = modifier // Aplicamos el modifier aquí (que incluye la altura)
            .fillMaxWidth()
            // .height(300.dp) // La altura se pasa ahora por el modifier desde la llamada
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
                    .fillMaxHeight(0.80f)
                    .align(Alignment.TopCenter)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)
                    .background(buttonColor)
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = textColor,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// --- Previews (sin cambios) ---
// ... (Tus Previews se mantienen igual) ...

