package com.example.tungclock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val Fundo = Color(0xFF0B0E17)
private val CardFundo = Color(0xFF171D2B)
private val Roxo = Color(0xFF9B7CFF)
private val Texto = Color.White
private val TextoSecundario = Color(0xFF9CA3B5)

@Composable
fun CronometroScreen(
    paddingValues: PaddingValues
) {

    var segundos by remember {
        mutableStateOf(0)
    }

    var rodando by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(rodando) {

        while (rodando) {

            delay(1000)

            segundos++
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fundo)
            .padding(paddingValues)
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "TUN TUN CLOCK · PRECISÃO",
            color = Roxo,
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        Text(
            text = "Cronômetro",
            color = Texto,
            fontSize = 20.sp
        )

        Spacer(
            modifier = Modifier.height(35.dp)
        )

        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(210.dp)
                .background(
                    CardFundo,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "%02d:%02d:%02d".format(
                    segundos / 3600,
                    (segundos % 3600) / 60,
                    segundos % 60
                ),
                color = Texto,
                fontSize = 28.sp
            )
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Row(
            horizontalArrangement = Arrangement.Center
        ) {

            Button(
                onClick = {
                    rodando = false
                    segundos = 0
                }
            ) {
                Text("RESETAR")
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Button(
                onClick = {
                    rodando = !rodando
                }
            ) {

                Text(
                    if (rodando)
                        "PAUSAR"
                    else
                        "▶ INICIAR"
                )
            }
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Text(
            text = if (rodando)
                "Cronômetro em andamento"
            else
                "Pronto para começar",
            color = TextoSecundario,
            fontSize = 12.sp
        )
    }
}
