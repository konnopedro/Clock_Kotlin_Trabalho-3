package com.example.tungclock

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val Fundo = Color(0xFF17120F)
private val CardFundo = Color(0xFF261D17)
private val Madeira = Color(0xFFC58B55)
private val MadeiraClara = Color(0xFFE0B17A)
private val Texto = Color(0xFFF6EBDD)
private val TextoSecundario = Color(0xFFBCA18A)
private val Borda = Color(0xFF493427)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmesScreen(
    paddingValues: PaddingValues,
    alarmes: SnapshotStateList<Alarme>,
    onVoltar: () -> Unit,
    onAbrirDetalhes: (Int) -> Unit
) {
    var mostrarNovoAlarme by remember { mutableStateOf(false) }
    var novoHorario by remember { mutableStateOf("07:00") }
    var novoNome by remember { mutableStateOf("") }
    var novosDias by remember { mutableStateOf("Todos os dias") }
    var erro by remember { mutableStateOf("") }
    var alarmeParaExcluir by remember { mutableStateOf<Alarme?>(null) }

    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val alarmeDao = remember { database.alarmeDao() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.padding(paddingValues),
        containerColor = Fundo,
        topBar = {
            TopAppBar(
                title = {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text("Alarmes", color = Texto, fontWeight = FontWeight.Bold)
                        Text(alarmes.count { it.ativo }.toString() + " alarmes ativos", color = TextoSecundario, fontSize = 12.sp)
                    }
                },
                navigationIcon = {
                    Icon(Icons.Default.Alarm, contentDescription = null, tint = MadeiraClara, modifier = Modifier.padding(start = 16.dp, end = 10.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Fundo, titleContentColor = Texto)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    novoHorario = "07:00"
                    novoNome = ""
                    novosDias = "Todos os dias"
                    erro = ""
                    mostrarNovoAlarme = true
                },
                containerColor = MadeiraClara,
                contentColor = Fundo,
                shape = RoundedCornerShape(18.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Criar alarme")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().background(Fundo).padding(innerPadding).padding(horizontal = 18.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Sua rotina", color = Texto, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                Text("Toque em um alarme para editar os detalhes.", color = TextoSecundario, fontSize = 13.sp)
            }

            if (alarmes.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = CardFundo),
                    border = BorderStroke(1.dp, Borda)
                ) {
                    Column(Modifier.fillMaxWidth().padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(Icons.Default.Alarm, contentDescription = null, tint = MadeiraClara, modifier = Modifier.height(36.dp))
                        Text("Nenhum alarme por enquanto", color = Texto, fontWeight = FontWeight.SemiBold)
                        Text("Use o botão + para criar seu primeiro horário.", color = TextoSecundario, fontSize = 13.sp)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 90.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items = alarmes, key = { it.id }) { alarme ->
                        Card(
                            modifier = Modifier.fillMaxWidth().clickable { onAbrirDetalhes(alarme.id) },
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.cardColors(containerColor = CardFundo),
                            border = BorderStroke(1.dp, if (alarme.ativo) Color(0xFF765137) else Borda)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(start = 18.dp, top = 16.dp, bottom = 16.dp, end = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                    Text(alarme.horario, color = if (alarme.ativo) Texto else TextoSecundario, fontSize = 34.sp, fontWeight = FontWeight.Light, letterSpacing = 1.sp)
                                    Text(alarme.nome, color = Texto, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                                    Text(alarme.dias, color = TextoSecundario, fontSize = 12.sp)
                                    Text(if (alarme.ativo) "ATIVO" else "DESATIVADO", color = if (alarme.ativo) MadeiraClara else TextoSecundario, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Switch(
                                        checked = alarme.ativo,
                                        onCheckedChange = { ativo ->
                                            val atualizado = alarme.copy(ativo = ativo)
                                            scope.launch {
                                                withContext(Dispatchers.IO) { alarmeDao.atualizar(atualizado) }
                                                val index = alarmes.indexOfFirst { it.id == alarme.id }
                                                if (index >= 0) alarmes[index] = atualizado
                                            }
                                        }
                                    )
                                    IconButton(onClick = { alarmeParaExcluir = alarme }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Excluir alarme", tint = Color(0xFFE18A75))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (mostrarNovoAlarme) {
        AlertDialog(
            onDismissRequest = { mostrarNovoAlarme = false },
            containerColor = CardFundo,
            titleContentColor = Texto,
            textContentColor = TextoSecundario,
            title = { Text("Novo alarme", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Defina quando você quer ser lembrado.", color = TextoSecundario, fontSize = 13.sp)
                    OutlinedTextField(
                        value = novoHorario,
                        onValueChange = { novoHorario = it },
                        label = { Text("Horário (HH:mm)") },
                        placeholder = { Text("07:30") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Texto, unfocusedTextColor = Texto, focusedLabelColor = MadeiraClara, unfocusedLabelColor = TextoSecundario, focusedBorderColor = Madeira, unfocusedBorderColor = Borda, cursorColor = MadeiraClara)
                    )
                    OutlinedTextField(
                        value = novoNome,
                        onValueChange = { novoNome = it },
                        label = { Text("Nome do alarme") },
                        placeholder = { Text("Acordar") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Texto, unfocusedTextColor = Texto, focusedLabelColor = MadeiraClara, unfocusedLabelColor = TextoSecundario, focusedBorderColor = Madeira, unfocusedBorderColor = Borda, cursorColor = MadeiraClara)
                    )
                    OutlinedTextField(
                        value = novosDias,
                        onValueChange = { novosDias = it },
                        label = { Text("Repetição") },
                        placeholder = { Text("Todos os dias") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Texto, unfocusedTextColor = Texto, focusedLabelColor = MadeiraClara, unfocusedLabelColor = TextoSecundario, focusedBorderColor = Madeira, unfocusedBorderColor = Borda, cursorColor = MadeiraClara)
                    )
                    if (erro.isNotBlank()) Text(erro, color = Color(0xFFE18A75), fontSize = 12.sp)
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val horarioValido = novoHorario.matches(Regex("^([01]?[0-9]|2[0-3]):[0-5][0-9]$"))
                    if (!horarioValido) {
                        erro = "Use um horário válido, como 07:30."
                    } else if (novoNome.isBlank()) {
                        erro = "Digite um nome para o alarme."
                    } else {
                        val novoAlarme = Alarme(horario = novoHorario, nome = novoNome.trim(), dias = novosDias.ifBlank { "Todos os dias" }, ativo = true)
                        scope.launch {
                            val salvo = withContext(Dispatchers.IO) {
                                val id = alarmeDao.inserir(novoAlarme)
                                alarmeDao.buscarPorId(id.toInt())
                            }
                            salvo?.let { alarmes.add(it) }
                            mostrarNovoAlarme = false
                        }
                    }
                }) { Text("Salvar", color = MadeiraClara) }
            },
            dismissButton = {
                TextButton(onClick = { mostrarNovoAlarme = false }) { Text("Cancelar", color = TextoSecundario) }
            }
        )
    }

    alarmeParaExcluir?.let { alarme ->
        AlertDialog(
            onDismissRequest = { alarmeParaExcluir = null },
            containerColor = CardFundo,
            title = { Text("Excluir alarme?", color = Texto) },
            text = { Text("O alarme “" + alarme.nome + "” será removido permanentemente.", color = TextoSecundario) },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        withContext(Dispatchers.IO) { alarmeDao.excluir(alarme) }
                        alarmes.removeAll { it.id == alarme.id }
                        alarmeParaExcluir = null
                    }
                }) { Text("Excluir", color = Color(0xFFE18A75)) }
            },
            dismissButton = {
                TextButton(onClick = { alarmeParaExcluir = null }) { Text("Cancelar", color = TextoSecundario) }
            }
        )
    }
}
