package com.cusosandroid.practicas.view

import com.cusosandroid.practicas.R
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cusosandroid.practicas.components.TextView // Asegúrate que tu TextView acepte Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

@Composable
fun HomeView(navController: NavHostController) {
    var blinkAlpha by remember { mutableStateOf(1f) }

    // Efecto para el parpadeo continuo del mensaje
    LaunchedEffect(Unit) {
        while (true) {
            blinkAlpha = 0.3f // Atenuado
            delay(700)
            blinkAlpha = 1f   // Opaco
            delay(700)
        }
    }

    // Animación suave para el alpha del mensaje
    val animatedBlinkAlpha by animateFloatAsState(
        targetValue = blinkAlpha,
        animationSpec = tween(durationMillis = 600, easing = LinearEasing),
        label = "blinkAlphaAnimation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            // Aplica padding para la barra de estado (arriba) y la barra de navegación/gestos (abajo)
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Mitad superior: Lakers
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("ConferenceSelector/Lakers")
                    },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.lakers2),
                    contentDescription = "Lakers",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.TopCenter)
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    TextView(texto = "Los Angeles Lakers", color = Color.White, fontSize = 24.sp)
                }
            }

            // Mitad inferior: Knicks
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable { navController.navigate("ConferenceSelector/Knicks") },
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
                        .align(Alignment.TopCenter)
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    TextView(texto = "New York Knicks", color = Color.White, fontSize = 24.sp)
                }
            }
        }


        // Contenedor para el mensaje flotante, para controlar su posición final
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                // Aplica aquí el padding para subir todo el mensaje
                .padding(bottom = 32.dp) // Ajusta este valor
        ) {
            Card(
                modifier = Modifier
                    // El padding horizontal se mantiene para el Card
                    .padding(horizontal = 16.dp)
                    .alpha(animatedBlinkAlpha),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha = 0.8f))
            ) {
                TextView(
                    texto = "¡Selecciona tu equipo favorito!",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

        }
    }
}

}
