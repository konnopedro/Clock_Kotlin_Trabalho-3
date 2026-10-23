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

private val Fundo = Color(0xFF0B0E17)
private val Roxo = Color(0xFF9B7CFF)
private val Texto = Color.White
private val TextoSecundario = Color(0xFF9CA3B5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalheAlarmeScreen(
    id: Int,
    alarmes: SnapshotStateList<Alarme>,
    onVoltar: () -> Unit
) {

    val alarme = alarmes.find {
        it.id == id
    }

    if (alarme == null) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .background(Fundo)
                .padding(20.dp)
        ) {

            Text(
                text = "Alarme não encontrado",
                color = Texto
            )

            Button(
                onClick = onVoltar
            ) {
                Text("Voltar")
            }
        }

        return
    }

    var horario by remember {
        mutableStateOf(alarme.horario)
    }

    var nome by remember {
        mutableStateOf(alarme.nome)
    }

    var dias by remember {
        mutableStateOf(alarme.dias)
    }

    var ativo by remember {
        mutableStateOf(alarme.ativo)
    }

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

                    val indice = alarmes.indexOfFirst {
                        it.id == id
                    }

                    if (indice >= 0) {

                        alarmes[indice] = alarme.copy(
                            horario = horario,
                            nome = nome,
                            dias = dias,
                            ativo = ativo
                        )
                    }

                    onVoltar()
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
