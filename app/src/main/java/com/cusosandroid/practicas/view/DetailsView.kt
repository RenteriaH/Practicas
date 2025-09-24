package com.cusosandroid.practicas.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cusosandroid.practicas.components.ActionButton
import com.cusosandroid.practicas.components.TitleBar


@Composable
fun DetailsView(navController: NavHostController, id: Int) {
    val info = when (id) {
        0 -> "Información de la División 1"
        1 -> "Historia de la División 2"
        2 -> "Detalles de la División 3"
        3 -> "Datos de la División 4"
        else -> "No hay información disponible"
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // Parte superior: info
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TitleBar(info)
        }

        // Parte inferior: simulación de video
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("Aquí va el VideoView / YouTube Player")
        }
    }

    // Botón flotante (ejemplo: volver atrás)
    ActionButton()
}
