package com.cusosandroid.practicas.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cusosandroid.practicas.R

@Composable
fun TextView(
    texto: String,
    modifier: Modifier = Modifier, // <--- AÑADE ESTE PARÁMETRO
    color: Color = Color.Black,
    fontSize: TextUnit = 40.sp
    // No es necesario pasar fontWeight aquí si siempre será Bold,
    // o puedes hacerlo un parámetro también si necesitas variarlo.
) {
    Text(
        text = texto,
        modifier = modifier, // <--- PASA EL MODIFIER AL TEXT INTERNO
        fontSize = fontSize,
        fontWeight = FontWeight.Bold, // Puedes mantenerlo o hacerlo un parámetro
        color = color
    )
}
@Composable
fun Space(espacio:Int){
    Spacer(modifier = Modifier.height(espacio.dp))
}
@Composable
fun MainButton(
    modifier: Modifier = Modifier,    // <--- importante: por defecto
    name: String,
    backColor: Color,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = backColor),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues()
    ) {
        Text(
            text = name,
            color = color,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun TeamButton(
    text: String,
    team: String,
    onClick: () -> Unit
) {
    // Colores dinámicos según el equipo
    val buttonColor = when (team) {
        "Lakers" -> Color(0xFFFFB81C) // Amarillo Lakers
        "Knicks" -> Color(0xFF006BB6) // Azul Knicks
        else -> Color.Gray
    }

    val textColor = when (team) {
        "Lakers" -> Color(0xFF552583) // Morado Lakers
        "Knicks" -> Color.White
        else -> Color.White
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(buttonColor)
            .clickable { onClick() }
            .shadow(8.dp, RoundedCornerShape(12.dp)) // Efecto 3D
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = textColor,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
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
                    .fillMaxHeight(0.85f)
                //    .align(Alignment.TopCenter)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.20f)
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