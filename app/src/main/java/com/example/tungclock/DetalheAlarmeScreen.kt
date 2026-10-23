package com.example.tungclock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val Fundo = Color(0xFF17120F)
private val Roxo = Color(0xFFC58B55)
private val Texto = Color.White
private val TextoSecundario = Color(0xFFBCA18A)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalheAlarmeScreen(
    id: Int,
    alarmes: SnapshotStateList<Alarme>,
    onVoltar: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val alarmeDao = remember { database.alarmeDao() }
    val scope = rememberCoroutineScope()

    var alarme by remember(id) {
        mutableStateOf<Alarme?>(null)
    }

    LaunchedEffect(id) {
        alarme = withContext(Dispatchers.IO) {
            alarmeDao.buscarPorId(id)
        }
    }

    if (alarme == null) {
        // Aguarda o carregamento do banco de dados.
        // Se o ID não existir, o alarme continuará nulo.
        return
    }

    val alarmeAtual = alarme ?: return

    var horario by remember(id) {
        mutableStateOf(alarmeAtual.horario)
    }
    var nome by remember(id) {
        mutableStateOf(alarmeAtual.nome)
    }
    var dias by remember(id) {
        mutableStateOf(alarmeAtual.dias)
    }
    var ativo by remember(id) {
        mutableStateOf(alarmeAtual.ativo)
    }

    // Mantenha aqui o Scaffold e o restante do layout que você já tem.

    Scaffold(
        containerColor = Fundo,
        topBar = {
            TopAppBar(
                title = {
                    Text("Detalhes do Alarme")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onVoltar
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Fundo,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Fundo)
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = "EDITANDO ALARME",
                color = Roxo,
                fontSize = 12.sp
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            OutlinedTextField(
                value = horario,
                onValueChange = {
                    horario = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Horário")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    cursorColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                ),
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OutlinedTextField(
                value = nome,
                onValueChange = {
                    nome = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Nome")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    cursorColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                ),
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OutlinedTextField(
                value = dias,
                onValueChange = {
                    dias = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Dias")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    cursorColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                ),
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            androidx.compose.foundation.layout.Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {

                    Text(
                        text = "Alarme ativo",
                        color = Texto
                    )

                    Text(
                        text = if (ativo)
                            "O alarme está ligado"
                        else
                            "O alarme está desligado",
                        color = TextoSecundario,
                        fontSize = 12.sp
                    )
                }

                Switch(
                    checked = ativo,
                    onCheckedChange = {
                        ativo = it
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            Button(
                onClick = {
                    val alarmeAtualizado = alarmeAtual.copy(
                        horario = horario,
                        nome = nome,
                        dias = dias,
                        ativo = ativo
                    )

                    scope.launch {
                        withContext(Dispatchers.IO) {
                            alarmeDao.atualizar(alarmeAtualizado)
                        }

                        val indice = alarmes.indexOfFirst { it.id == id }
                        if (indice >= 0) {
                            alarmes[indice] = alarmeAtualizado
                        }

                        alarme = alarmeAtualizado
                        onVoltar()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "SALVAR ALTERAÇÕES"
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Esta tela permite editar os dados do item selecionado.",
                color = TextoSecundario,
                fontSize = 11.sp
            )
        }
    }
}
