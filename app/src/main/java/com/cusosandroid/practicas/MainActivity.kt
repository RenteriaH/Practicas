package com.cusosandroid.practicas

import android.R.attr.content
import android.os.Bundle
import android.text.Layout
import android.text.style.BackgroundColorSpan
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cusosandroid.practicas.ui.theme.PracticasTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticasTheme {
                MainScreen()
            }
          }
        }
    }

@Composable
fun MainScreen() {
    var display by remember { mutableStateOf("0") }
    var operand by remember { mutableStateOf<Double?>(null) }
    var operation by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Pantalla de resultado
        Text(
            text = display,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            maxLines = 1
        )

        // Teclado
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            val botones = listOf(
                listOf("7", "8", "9", "/"),
                listOf("4", "5", "6", "*"),
                listOf("1", "2", "3", "-"),
                listOf("0", ".", "C", "+"),
                listOf("=")
            )

            botones.forEach { fila ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    fila.forEach { label ->
                        Button(
                            onClick = {
                                when (label) {
                                    in "0".."9", "." -> {
                                        display =
                                            if (display == "0" && label != ".") label else display + label
                                    }
                                    "C" -> {
                                        display = "0"
                                        operand = null
                                        operation = null
                                    }
                                    "+", "-", "*", "/" -> {
                                        operand = display.toDoubleOrNull()
                                        operation = label
                                        display = "0"
                                    }
                                    "=" -> {
                                        val second = display.toDoubleOrNull()
                                        if (operand != null && second != null && operation != null) {
                                            display = when (operation) {
                                                "+" -> (operand!! + second).toString()
                                                "-" -> (operand!! - second).toString()
                                                "*" -> (operand!! * second).toString()
                                                "/" -> if (second == 0.0) "Error" else (operand!! / second).toString()
                                                else -> display
                                            }
                                            operand = null
                                            operation = null
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(70.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (label in listOf("+", "-", "*", "/", "="))
                                    Color(0xFF4CAF50) else Color(0xFF2196F3),
                                contentColor = Color.White
                            )
                        ) {
                            Text(text = label, fontSize = 22.sp)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
            fun GreetingPreview() {
                PracticasTheme {
                    MainScreen()
                }

        }
