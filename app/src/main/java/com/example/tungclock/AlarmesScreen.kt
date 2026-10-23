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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
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
    var mostrarCadastro by remember { mutableStateOf(false) }
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
                        Text("${alarmes.size} alarmes salvos", color = TextoSecundario, fontSize = 12.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Fundo)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    erro = ""
                    mostrarCadastro = true
                },
                containerColor = Madeira,
                contentColor = Fundo,
                shape = RoundedCornerShape(18.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Criar alarme")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().background(Fundo).padding(innerPadding).padding(horizontal = 18.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Sua rotina", color = Texto, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Toque em um alarme para editar. Use o botão para ativar ou pausar.", color = TextoSecundario, fontSize = 13.sp)
            }

            if (alarmes.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardFundo),
                    shape = RoundedCornerShape(22.dp),
                    border = BorderStroke(1.dp, Borda)
                ) {
                    Column(Modifier.fillMaxWidth().padding(22.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Nenhum alarme por enquanto", color = Texto, fontWeight = FontWeight.SemiBold)
                        Text("Crie seu primeiro alarme tocando no botão +.", color = TextoSecundario, fontSize = 13.sp)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 88.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items = alarmes, key = { it.id }) { alarme ->
                        Card(
                            modifier = Modifier.fillMaxWidth().clickable { onAbrirDetalhes(alarme.id) },
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.cardColors(containerColor = CardFundo),
                            border = BorderStroke(1.dp, if (alarme.ativo) Borda else Color(0xFF332720))
                        ) {
                            Column(Modifier.fillMaxWidth().padding(horizontal = 17.dp, vertical = 15.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        alarme.horario,
                                        color = if (alarme.ativo) Texto else TextoSecundario,
                                        fontSize = 36.sp,
                                        fontWeight = FontWeight.Light,
                                        letterSpacing = 1.sp
                                    )
                                    Switch(
                                        checked = alarme.ativo,
                                        onCheckedChange = { ligado ->
                                            val atualizado = alarme.copy(ativo = ligado)
                                            scope.launch {
                                                withContext(Dispatchers.IO) { alarmeDao.atualizar(atualizado) }
                                                val index = alarmes.indexOfFirst { it.id == alarme.id }
                                                if (index >= 0) alarmes[index] = atualizado
                                            }
                                        }
                                    )
                                }
                                Text(alarme.nome, color = Texto, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(alarme.dias.ifBlank { "Uma vez" }, color = TextoSecundario, fontSize = 12.sp, modifier = Modifier.weight(1f))
                                    IconButton(onClick = { alarmeParaExcluir = alarme }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Excluir alarme", tint = Color(0xFFE58C79))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (mostrarCadastro) {
        AlertDialog(
            onDismissRequest = { mostrarCadastro = false },
            containerColor = CardFundo,
            title = { Text("Novo alarme", color = Texto, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Defina o horário e como quer identificar esse alarme.", color = TextoSecundario, fontSize = 13.sp)
                    OutlinedTextField(
                        value = novoHorario,
                        onValueChange = { novoHorario = it; erro = "" },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Horário (HH:mm)") },
                        placeholder = { Text("07:30") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Texto, unfocusedTextColor = Texto,
                            focusedLabelColor = MadeiraClara, unfocusedLabelColor = TextoSecundario,
                            cursorColor = MadeiraClara, focusedBorderColor = Madeira,
                            unfocusedBorderColor = Borda
                        )
                    )
                    OutlinedTextField(
                        value = novoNome,
                        onValueChange = { novoNome = it; erro = "" },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Nome do alarme") },
                        placeholder = { Text("Acordar") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Texto, unfocusedTextColor = Texto,
                            focusedLabelColor = MadeiraClara, unfocusedLabelColor = TextoSecundario,
                            cursorColor = MadeiraClara, focusedBorderColor = Madeira,
                            unfocusedBorderColor = Borda
                        )
                    )
                    OutlinedTextField(
                        value = novosDias,
                        onValueChange = { novosDias = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Repetição") },
                        placeholder = { Text("Todos os dias") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Texto, unfocusedTextColor = Texto,
                            focusedLabelColor = MadeiraClara, unfocusedLabelColor = TextoSecundario,
                            cursorColor = MadeiraClara, focusedBorderColor = Madeira,
                            unfocusedBorderColor = Borda
                        )
                    )
                    if (erro.isNotBlank()) Text(erro, color = Color(0xFFFF8A80), fontSize = 12.sp)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val partes = novoHorario.trim().split(":")
                        val horarioValido = partes.size == 2 &&
                            partes[0].toIntOrNull() in 0..23 &&
                            partes[1].toIntOrNull() in 0..59
                        if (!horarioValido) {
                            erro = "Use um horário válido, por exemplo 07:30."
                        } else if (novoNome.isBlank()) {
                            erro = "Digite um nome para o alarme."
                        } else {
                            val horarioNormalizado = partes.joinToString(":") {
                                it.toInt().toString().padStart(2, '0')
                            }
                            val novoAlarme = Alarme(
                                horario = horarioNormalizado,
                                nome = novoNome.trim(),
                                dias = novosDias.trim().ifBlank { "Uma vez" },
                                ativo = true
                            )
                            scope.launch {
                                try {
                                    val salvo = withContext(Dispatchers.IO) {
                                        val id = alarmeDao.inserir(novoAlarme)
                                        alarmeDao.buscarPorId(id.toInt())
                                    }
                                    salvo?.let { alarmes.add(it) }
                                    mostrarCadastro = false
                                    novoHorario = "07:00"
                                    novoNome = ""
                                    novosDias = "Todos os dias"
                                    erro = ""
                                } catch (e: Exception) {
                                    erro = "Não foi possível salvar. Tente novamente."
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Madeira, contentColor = Fundo)
                ) { Text("Salvar alarme") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarCadastro = false }) { Text("Cancelar", color = MadeiraClara) }
            }
        )
    }

    if (alarmeParaExcluir != null) {
        val alvo = alarmeParaExcluir!!
        AlertDialog(
            onDismissRequest = { alarmeParaExcluir = null },
            containerColor = CardFundo,
            title = { Text("Excluir alarme?", color = Texto) },
            text = { Text("O alarme “${alvo.nome}” será removido permanentemente.", color = TextoSecundario) },
            confirmButton = {
                TextButton(
                    onClick = {
                        scope.launch {
                            withContext(Dispatchers.IO) { alarmeDao.excluir(alvo) }
                            alarmes.removeAll { it.id == alvo.id }
                            alarmeParaExcluir = null
                        }
                    }
                ) { Text("Excluir", color = Color(0xFFE58C79)) }
            },
            dismissButton = {
                TextButton(onClick = { alarmeParaExcluir = null }) { Text("Cancelar", color = MadeiraClara) }
            }
        )
    }
}
