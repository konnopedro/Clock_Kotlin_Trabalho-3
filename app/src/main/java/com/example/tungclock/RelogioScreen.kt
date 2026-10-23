package com.example.tungclock

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val Fundo = Color(0xFF0B0E17)
private val CardFundo = Color(0xFF171D2B)
private val Roxo = Color(0xFF9B7CFF)
private val Texto = Color(0xFFF6F4FF)
private val TextoSecundario = Color(0xFF9CA3B5)
private val Borda = Color(0xFF2A3041)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RelogioScreen(
    paddingValues: PaddingValues,
    onAbrirAlarmes: () -> Unit,
    onAbrirFusos: () -> Unit
) {
    var horaAtual by remember {
        mutableStateOf(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
    }

    LaunchedEffect(Unit) {
        while (true) {
            horaAtual = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))
            delay(1000)
        }
    }

    val hora = horaAtual.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
    val data = horaAtual.format(
        DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", Locale("pt", "BR"))
    ).replaceFirstChar { it.titlecase(Locale("pt", "BR")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fundo)
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                    text = "TUN TUN",
                    color = Roxo,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "CLOCK",
                    color = Texto,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }
            Text(
                text = "●  AO VIVO",
                color = Color(0xFF8BE0B0),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = "Tudo no seu tempo.",
                color = Texto,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = data,
                color = TextoSecundario,
                fontSize = 14.sp
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = CardFundo),
            border = androidx.compose.foundation.BorderStroke(1.dp, Borda)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp, vertical = 26.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "HORÁRIO DE BRASÍLIA",
                    color = Roxo,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = hora,
                    color = Texto,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 1.sp,
                    textAlign = TextAlign.Center
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("◉", color = Roxo, fontSize = 13.sp)
                    Spacer(Modifier.padding(horizontal = 3.dp))
                    Text(
                        text = "São Paulo, Brasil  ·  UTC−03:00",
                        color = TextoSecundario,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = "Acesso rápido",
                color = Texto,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
            Card(
                onClick = onAbrirAlarmes,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = CardFundo),
                border = androidx.compose.foundation.BorderStroke(1.dp, Borda)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text("◷", color = Roxo, fontSize = 28.sp)
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Meus alarmes", color = Texto, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        Text("Organize seus horários e rotinas", color = TextoSecundario, fontSize = 12.sp)
                    }
                    Text("›", color = Roxo, fontSize = 28.sp)
                }
            }
            Card(
                onClick = onAbrirFusos,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = CardFundo),
                border = androidx.compose.foundation.BorderStroke(1.dp, Borda)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text("◎", color = Roxo, fontSize = 28.sp)
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Fusos horários", color = Texto, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        Text("Confira a hora em outras cidades", color = TextoSecundario, fontSize = 12.sp)
                    }
                    Text("›", color = Roxo, fontSize = 28.sp)
                }
            }
        }

        Button(
            onClick = onAbrirAlarmes,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Roxo, contentColor = Fundo),
            contentPadding = PaddingValues(12.dp)
        ) {
            Text("GERENCIAR MEU TEMPO", fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
        }
    }
}
