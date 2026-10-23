package com.example.tungclock

import android.os.Build
import androidx.compose.foundation.layout.PaddingValues
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

private val Fundo = Color(0xFF0B0E17)
private val CardFundo = Color(0xFF171D2B)
private val Roxo = Color(0xFF9B7CFF)
private val Texto = Color.White
private val TextoSecundario = Color(0xFF9CA3B5)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RelogioScreen(
    paddingValues: PaddingValues,
    onAbrirAlarmes: () -> Unit,
    onAbrirFusos: () -> Unit
) {

    var horaAtual by remember {
        mutableStateOf(
            ZonedDateTime.now(
                ZoneId.of("America/Sao_Paulo")
            )
        )
    }

    LaunchedEffect(Unit) {
        while (true) {

            horaAtual = ZonedDateTime.now(
                ZoneId.of("America/Sao_Paulo")
            )

            delay(1000)
        }
    }

    val hora = horaAtual.format(
        DateTimeFormatter.ofPattern("HH:mm:ss")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Fundo)
            .padding(paddingValues)
            .padding(20.dp)
    ) {

        Text(
            text = "TUN TUN CLOCK",
            color = Roxo,
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Bom dia, meu docinho",
            color = Texto,
            fontSize = 15.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Seu relógio pessoal",
            color = TextoSecundario,
            fontSize = 13.sp
        )

        Spacer(
            modifier = Modifier.height(40.dp)
        )

        Text(
            text = hora,
            color = Texto,
            fontSize = 36.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "São Paulo, Brasil",
            color = TextoSecundario,
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier
                    .background(CardFundo)
                    .padding(16.dp)
            ) {

                Text(
                    text = "PRÓXIMO ALARME",
                    color = Roxo,
                    fontSize = 12.sp
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "07:00",
                    color = Texto,
                    fontSize = 24.sp
                )

                Text(
                    text = "Acordar",
                    color = TextoSecundario,
                    fontSize = 12.sp
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Button(
                    onClick = onAbrirAlarmes,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("VER MEUS ALARMES")
                }
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(
            onClick = onAbrirFusos,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("VER FUSOS HORÁRIOS")
        }
    }
}
