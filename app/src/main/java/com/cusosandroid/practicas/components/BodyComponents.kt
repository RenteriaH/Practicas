package com.cusosandroid.practicas.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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

// --- NUEVOS COMPOSABLES ---

@OptIn(ExperimentalTextApi::class) // <-- Añade esta anotación
@Composable
fun SectionTitle(title: String, brush: Brush) { // <-- Cambiamos textColor por brush
    Text(
        text = title,style = TextStyle( // <-- Usamos TextStyle para aplicar el brush
            brush = brush,
            fontSize = MaterialTheme.typography.titleLarge.fontSize, // Mantenemos el tamaño
            fontWeight = FontWeight.Bold // Mantenemos la negrita
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, top = 16.dp)
    )
}


@Composable
fun KeyStatsRow(stats: List<Pair<String, String>>, cardColor: Color, textColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        stats.forEach { (label, value) ->
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor.copy(alpha = 0.9f))
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = value,
                        color = textColor,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = label,
                        color = textColor,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun YouTubeVideosRow(videoIds: List<String>, onVideoClick: (String) -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(videoIds) { videoId ->
            val thumbnailUrl = "https://img.youtube.com/vi/$videoId/hqdefault.jpg"
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier.clickable { onVideoClick(videoId) }
            ) {
                Box(
                    modifier = Modifier.size(width = 240.dp, height = 135.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = thumbnailUrl,
                        contentDescription = "Miniatura de YouTube",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        error = painterResource(id = R.drawable.lakerss) // Imagen de fallback
                    )
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.6f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = Color.White,
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }
            }
        }
    }
}

// --- COMPOSABLES MODIFICADOS/EXISTENTES ---

@Composable
fun ImageGallery(images: List<Int>) {
    LazyRow(
        modifier = Modifier.padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp) // Margen a los lados de la lista
    ) {
        items(images) { imageResId ->
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "Imagen de la galería",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(180.dp)
                        .width(300.dp)
                )
            }
        }
    }
}


@Composable
fun SectionCard(
    content: String,
    cardColor: Color,
    textColor: Color
) { // Ligeramente modificado para no repetir título
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Text(
            text = content,
            color = textColor,
            fontSize = 15.sp,
            lineHeight = 22.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}