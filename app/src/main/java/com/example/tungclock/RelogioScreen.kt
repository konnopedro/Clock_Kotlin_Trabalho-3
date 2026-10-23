package com.example.tungclock

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val Fundo = Color(0xFF17120F)
private val CardFundo = Color(0xFF261D17)
private val Madeira = Color(0xFFC58B55)
private val MadeiraClara = Color(0xFFE0B17A)
private val Texto = Color(0xFFF6EBDD)
private val TextoSecundario = Color(0xFFBCA18A)
private val Borda = Color(0xFF493427)

private data class CidadeDisponivel(
    val cidade: String,
    val pais: String,
    val zona: String,
    val offset: Int
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RelogioScreen(
    paddingValues: PaddingValues,
    fusos: SnapshotStateList<FusoHorario>,
    onAbrirAlarmes: () -> Unit,
    onAbrirFusos: () -> Unit,
    onAbrirDetalhesFuso: (Int) -> Unit
) {
    var horaAtual by remember {
        mutableStateOf(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
    }
    var mostrarCidades by remember { mutableStateOf(false) }
    var erroFuso by remember { mutableStateOf("") }
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val fusoDao = remember { database.fusoHorarioDao() }
    val scope = rememberCoroutineScope()

    val cidadesDisponiveis = remember {
        listOf(
            CidadeDisponivel("São Paulo", "Brasil", "America/Sao_Paulo", -3),
            CidadeDisponivel("Nova York", "Estados Unidos", "America/New_York", -4),
            CidadeDisponivel("Londres", "Reino Unido", "Europe/London", 0),
            CidadeDisponivel("Tóquio", "Japão", "Asia/Tokyo", 9),
            CidadeDisponivel("Paris", "França", "Europe/Paris", 1),
            CidadeDisponivel("Sydney", "Austrália", "Australia/Sydney", 10),
            CidadeDisponivel("Dubai", "Emirados Árabes", "Asia/Dubai", 4),
            CidadeDisponivel("Lisboa", "Portugal", "Europe/Lisbon", 0)
        )
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
            .padding(horizontal = 20.dp, vertical = 22.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text("TUN TUN", color = MadeiraClara, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
                Text("CLOCK", color = Texto, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.5.sp)
            }
            Text("●  AO VIVO", color = Color(0xFF9FB79A), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        }

        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text("Seu tempo, do seu jeito.", color = Texto, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Text(data, color = TextoSecundario, fontSize = 14.sp)
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = CardFundo),
            border = BorderStroke(1.dp, Borda)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 26.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("HORÁRIO DE BRASÍLIA", color = MadeiraClara, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.7.sp)
                Text(hora, color = Texto, fontSize = 43.sp, fontWeight = FontWeight.Light, letterSpacing = 1.sp, textAlign = TextAlign.Center)
                Text("◉   São Paulo, Brasil  ·  UTC−03:00", color = TextoSecundario, fontSize = 12.sp, textAlign = TextAlign.Center)
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("Relógios mundiais", color = Texto, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("A hora em outras cidades", color = TextoSecundario, fontSize = 12.sp)
                }
                IconButton(onClick = { erroFuso = ""; mostrarCidades = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Adicionar cidade", tint = MadeiraClara)
                }
            }

            if (fusos.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardFundo),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Borda)
                ) {
                    Column(Modifier.fillMaxWidth().padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Ainda não há cidades", color = Texto, fontWeight = FontWeight.SemiBold)
                        Text("Use o botão + para adicionar um relógio mundial.", color = TextoSecundario, fontSize = 13.sp)
                    }
                }
            } else {
                fusos.forEach { fuso ->
                    val horaCidade = remember(fuso.zona) {
                        mutableStateOf(ZonedDateTime.now(ZoneId.of(fuso.zona)))
                    }
                    LaunchedEffect(fuso.zona) {
                        while (true) {
                            horaCidade.value = ZonedDateTime.now(ZoneId.of(fuso.zona))
                            delay(1000)
                        }
                    }
                    Card(
                        onClick = { onAbrirDetalhesFuso(fuso.id) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = CardFundo),
                        border = BorderStroke(1.dp, Borda)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 17.dp, vertical = 15.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(fuso.cidade, color = Texto, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                Text(fuso.pais, color = TextoSecundario, fontSize = 12.sp)
                            }
                            Spacer(Modifier.width(10.dp))
                            Text(
                                horaCidade.value.format(DateTimeFormatter.ofPattern("HH:mm")),
                                color = MadeiraClara,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                    }
                }
            }

            OutlinedButton(
                onClick = onAbrirFusos,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MadeiraClara),
                border = BorderStroke(1.dp, Borda)
            ) {
                Icon(Icons.Default.Public, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Gerenciar relógios mundiais")
            }
        }
    }

    if (mostrarCidades) {
        AlertDialog(
            onDismissRequest = { mostrarCidades = false },
            containerColor = CardFundo,
            title = { Text("Adicionar cidade", color = Texto, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Escolha uma cidade para acompanhar o horário local.", color = TextoSecundario, fontSize = 13.sp)
                    if (erroFuso.isNotBlank()) Text(erroFuso, color = Color(0xFFFF8A80), fontSize = 12.sp)
                    LazyColumn {
                        items(cidadesDisponiveis.filter { cidade -> fusos.none { it.zona == cidade.zona } }) { cidade ->
                            TextButton(
                                onClick = {
                                    scope.launch {
                                        try {
                                            val salvo = withContext(Dispatchers.IO) {
                                                val id = fusoDao.inserir(
                                                    FusoHorario(cidade = cidade.cidade, pais = cidade.pais, zona = cidade.zona, offset = cidade.offset)
                                                )
                                                fusoDao.buscarPorId(id.toInt())
                                            }
                                            salvo?.let { fusos.add(it) }
                                            mostrarCidades = false
                                        } catch (e: Exception) {
                                            erroFuso = "Não foi possível adicionar a cidade. Tente novamente."
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(Modifier.fillMaxWidth()) {
                                    Text(cidade.cidade, color = Texto, fontWeight = FontWeight.SemiBold)
                                    Text(cidade.pais, color = TextoSecundario, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { mostrarCidades = false }) {
                    Text("Fechar", color = MadeiraClara)
                }
            }
        )
    }
}
