package com.cusosandroid.practicas

import android.R.attr.content
import android.os.Bundle
import android.text.InputFilter
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
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

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
    var history by remember { mutableStateOf("") }

    var operand by remember { mutableStateOf<Double?>(null) }
    var operation by remember { mutableStateOf<String?>(null) }
    var operatorPressed by remember { mutableStateOf(false) }
    val current = display.toDoubleOrNull() ?: 0.0


    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(18.dp,50.dp,18.dp,60.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        // ===== Pantallas de resultado =====
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            // Pantalla de historial (operación en curso)
            Text(
                text = history.take(40),
                fontSize = 60.sp,
                color = Color.Gray,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())//scroll horizontal
                    .padding(4.dp,30.dp,4.dp,4.dp)
            )

            // Pantalla principal (número actual / resultado)
            Text(
                text = display.take(13),
                fontSize = 90.sp,
                color = Color.White,

                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())//scroll horizontal
                    .padding(4.dp)
            )
        }

        // ===== Teclado =====
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            val botones = listOf(
                listOf("C","⌫"),
                listOf("7", "8", "9", "/"),
                listOf("4", "5", "6", "*"),
                listOf("1", "2", "3", "-"),
                listOf("0", ".", "+"),
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
                                    // Al presionar un número
                                    in "0".."9" -> {
                                        display = if (display == "0" || operatorPressed) {
                                            label
                                        } else {
                                            display + label
                                        }
                                        operatorPressed = false

                                    }
                                    "." -> {
                                        if(!display.contains(".")){ //Solo agregar si no hay otro
                                            display += "."
                                        }

                                    }
                                    "C" -> {
                                        display = "0"
                                        history = ""
                                        operand = null
                                        operation = null
                                    }
                                    "+", "-", "*", "/" -> {
                                        val second=display.toDoubleOrNull()

                                        if (operand==null) {
                                            //Primera vez:guardamos el nuemro
                                            operand =current
                                        }

                                        //}

                                        else if(operation!=null && second !=null){
                                            //Si ya habia operacion pendiente, acumulamos
                                            operand =when(operation){
                                                "+" -> operand!! + current
                                                "-" -> operand!! - current
                                                "*" -> operand!! * current
                                                "/" -> if (current ==0.0) Double.NaN else operand!! / current
                                                else -> operand
                                            }
                                          //  display= operand!!.toString() //Aqui mostramos la suma acumulada en el siplay
                                        }

                                        //Guardamos la nueva operacion
                                        operation =label
                                        //Actualizamos historial (se va acumulando)
                                        history += " $display $label"

                                        //Mostramos el total acumulado en el display
                                        display= if (operand!! % 1 == 0.0) operand!!.toInt()
                                                .toString() else operand!!.toString()
                                            operatorPressed=true


                                    }
                                    "⌫" -> {
                                        if(display.length>1){
                                            display=display.dropLast(1)
                                            if(display.last()=='.') display.dropLast(1)
                                        }else{
                                            display="0"
                                        }
                                    }
                                    "=" -> {
                                        val second = display.toDoubleOrNull()
                                        if (operand != null && second != null && operation != null) {
                                            val result: Double = when (operation) {
                                                "+" -> operand!! + second
                                                "-" -> operand!! - second
                                                "*" -> operand!! * second
                                                "/" -> if (second == 0.0) Double.NaN else operand!! / second
                                                else -> second
                                            }
                                            history = history + " " + display + " ="
                                            display = if (result.isNaN()) "Error" else {
                                                if (result % 1 == 0.0) result.toInt()
                                                    .toString() else result.toString()

                                            }
                                            operand = null
                                            operation = null
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(80.dp),
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (label in listOf("+", "-", "*", "/", "="))
                                    Color(0xFFD6850B) else Color(0xFF4F4D4D),
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
