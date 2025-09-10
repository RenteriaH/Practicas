package com.cusosandroid.practicas

import android.R
import android.os.Bundle
import android.text.Layout
import android.text.style.BackgroundColorSpan
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Ro
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cusosandroid.practicas.ui.theme.PracticasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticasTheme {
                Nombre()

                }
            }
        }
    }


@Composable
fun Nombre() {
// Estado para guardar el texto ingresado
    var PrimerN by remember { mutableStateOf("") }
    var SegundoN by remember { mutableStateOf("") }
    var textoGenerado by remember { mutableStateOf("") }


    // Contexto necesario para Toast
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        // Campo de texto
        Row(modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 10.dp)) {
            TextField(
                value = PrimerN,
                onValueChange = { PrimerN = it },
                label = { Text("Ingresar Primer Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 10.dp)) {
            TextField(
                value = SegundoN,
                onValueChange = { SegundoN = it },
                label = { Text("Ingresar Segundo Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
        }


        // Botón
        Row(
            modifier = Modifier.fillMaxWidth().padding(0.dp, 0.dp, 20.dp, 0.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    // Crear un TextView personalizado
                    textoGenerado="$PrimerN $SegundoN"

                    val toastText = TextView(context).apply {
                        text = "Nombre Enviado:$PrimerN $SegundoN"
                        setTextColor(android.graphics.Color.RED) // Texto rojo
                        textSize = 16f
                        setPadding(20, 10, 20, 10)
                    }

                    // Mostrar el Toast con texto personalizado
                    Toast(context).apply {
                        duration = Toast.LENGTH_LONG
                        view = toastText
                    }.show()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50) // Verde
                )
            ) {
                Text(text = "Enviar")
            }
        }

        Row(modifier = Modifier.padding(20.dp, 10.dp, 20.dp, 10.dp)) {
            TextField(
                value = textoGenerado,
                onValueChange = { textoGenerado = it },
                label = { Text("Texto Generado") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        // Botón


        Row(
            modifier = Modifier.fillMaxWidth().padding(0.dp, 0.dp, 20.dp, 0.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    textoGenerado = ""// Esto limpia el tercer Textfield
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            )
            {
                Text(text = "Borrar")
            }
        }
    }
}



            @Preview(showBackground = true)
            @Composable
            fun GreetingPreview() {
                PracticasTheme {
                    Nombre()
                }

        }
