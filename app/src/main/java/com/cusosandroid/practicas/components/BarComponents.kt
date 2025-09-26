package com.cusosandroid.practicas.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleBar(name:String){
    Text(text=name, fontSize = (25.sp),
        color= Color.White)
}

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier // por defecto vacío
) {
    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun MainIconButton(icon: ImageVector, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = "Botón",
            tint = Color.White
        )
    }
}