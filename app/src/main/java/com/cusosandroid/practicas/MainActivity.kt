package com.cusosandroid.practicas

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction

import androidx.compose.ui.text.input.KeyboardType
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
    var ingresoQuincenal by remember { mutableStateOf("") } // ingreso del usuario
    var resultadoISR by remember { mutableStateOf(0.0) } // resultado del ISR
    var netoRecibir by remember { mutableStateOf(0.0) } // Neto después del ISR
    var errorMensaje by remember{mutableStateOf<String?>(null)} //Para manejar errores

    val keyboardController = LocalSoftwareKeyboardController.current //Control del teclado


    Column(
        modifier = Modifier
            .background(color = Color(0xFF520F01))
            .fillMaxSize()
            .padding(18.dp, 80.dp, 18.dp, 60.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Box(
                modifier=Modifier
                    .size(355.dp)
                    .clip(CircleShape)
            ) {
                Image(
                    painter = painterResource(R.drawable.sat),
                    contentDescription = "SAT",
                    contentScale = ContentScale.Crop,//Recorta
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            scaleX = 1.25f,//zoom horizontal
                            scaleY = 1.25f// zoom vertical
                        )
                        //Degradado encima como textura luz


                    //           contentScale = ContentScale.Crop
                )
                Box(
                    modifier= Modifier
                        .matchParentSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.2f))
                            )
                        )
                )
            }
            Spacer(modifier=Modifier.height(20.dp))

            //Este es el campo de texto que solo acpeta numeros
            OutlinedTextField(
                value = ingresoQuincenal,
                onValueChange = { newValue ->
                    // Solo permite números
                    if (newValue.all { char -> char.isDigit() }) {
                        ingresoQuincenal = newValue
                    }
                },
                label = { Text("Ingreso quincenal") },
                placeholder = { Text("Ej. 6000") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true //

            )
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {

                    val ingreso = ingresoQuincenal.toDoubleOrNull()
                    if(ingreso != null && ingreso> 0){
                        resultadoISR=calcularISRQuincenal(ingreso)
                        netoRecibir=ingreso-resultadoISR
                        errorMensaje=null
                    }else{
                         errorMensaje="Ingresa una cantidad valida"
                    }
                    keyboardController?.hide() //ocultar el teclado al calcular
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .width(150.dp) //Ancho del boton
                    .height(60.dp), //Alto del boton
                colors= ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A90E2),//Color de fondo del boton
                    contentColor = Color.White
                )
            ) {
                Text(
                    "Calcular",
                    fontSize = 25.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Mostrar error si lo hay
            errorMensaje?.let {
                Text(
                    text=it,
                    fontSize = 30.sp,
                    color=Color.Red,
                    modifier=Modifier
                        .fillMaxWidth()
                        .padding(9.dp),
                    textAlign = TextAlign.Center
                )
            }


            Text(
                text = "ISR a pagar: $${String.format("%.2f", resultadoISR)}",
                fontSize = 28.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(4.dp, 30.dp, 4.dp, 4.dp)
            )

            Text(
                text = "Neto a recibir: $${String.format("%.2f", netoRecibir)}",
                fontSize = 29.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(4.dp)
            )

        }
    }
}

// Función para calcular ISR quincenal
fun calcularISRQuincenal(salario: Double): Double {
    val tablaISR = listOf(
        Triple(0.01, 368.10, 0.0 to 0.0192),
        Triple(368.11, 3124.35, 7.05 to 0.064),
        Triple(3124.36, 5490.75, 183.45 to 0.1088),
        Triple(5490.76, 6382.80, 441.00 to 0.16),
        Triple(6382.81, 7641.90, 583.65 to 0.1792),
        Triple(7641.91, 15412.80, 809.25 to 0.2136),
        Triple(15412.81, 24292.65, 2469.15 to 0.2352),
        Triple(24292.66, 46378.50, 4557.75 to 0.30),
        Triple(46378.51, 61838.10, 11183.40 to 0.32),
        Triple(61838.11, 185514.30, 16130.55 to 0.34),
        Triple(185514.31, Double.MAX_VALUE, 58180.35 to 0.35)
    )
                                      //  if (operand ==null && display != "0"){
                                        //    operand=second //guardamps solo si el display no es cero
    for ((limiteInf, limiteSup, datos) in tablaISR) {
        val (cuotaFija, porcentaje) = datos
        if (salario in limiteInf..limiteSup) {
            val excedente = salario - limiteInf
            return cuotaFija + (excedente * porcentaje)

          //  val isr = cuotaFija + (excedente * porcentaje)
            // Redondear a 2 decimales como en la tabla oficial
          //  return round(isr * 100) / 100
        }
    }
    return 0.0
}




@Preview(showBackground = true)
@Composable
            fun GreetingPreview() {
                PracticasTheme {
                    MainScreen()
                }

        }
