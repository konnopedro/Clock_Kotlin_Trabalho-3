package com.example.tungclock

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TopAppBarDefaults

private val Fundo = Color(0xFF0B0E17)
private val CardFundo = Color(0xFF171D2B)
private val Roxo = Color(0xFF9B7CFF)
private val Azul = Color(0xFF42B9F2)
private val Texto = Color.White
private val TextoSecundario = Color(0xFF9CA3B5)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmesScreen(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    alarmes: androidx.compose.runtime.snapshots.SnapshotStateList<Alarme>,
    onVoltar: () -> Unit,
    onAbrirDetalhes: (Int) -> Unit
) {

    var novoHorario by remember {
        mutableStateOf("")
    }

    var novoNome by remember {
        mutableStateOf("")
    }

    var novosDias by remember {
        mutableStateOf("")
    }


    Scaffold(

        containerColor = Fundo,

        topBar = {

            TopAppBar(
                title = {
                    Text(
                        text = "Meus Alarmes",
                        color = Color.White
                    )
                },

                navigationIcon = {
                    IconButton(
                        onClick = onVoltar
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
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
                .fillMaxHeight()
                .background(Fundo)
                .padding(innerPadding)
                .padding(16.dp)
        ) {


            // ==========================================
            // TÍTULO
            // ==========================================

            Text(
                text = "ADICIONAR ALARME",
                color = Roxo,
                fontSize = 12.sp
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )


            // ==========================================
            // CAMPO HORÁRIO
            // ==========================================

            OutlinedTextField(

                value = novoHorario,

                onValueChange = {
                    novoHorario = it
                },

                modifier = Modifier.fillMaxWidth(),

                label = {
                    Text("Horário")
                },

                placeholder = {
                    Text("Ex: 07:30")
                },

                singleLine = true,
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
                modifier = Modifier.height(10.dp)
            )


            // ==========================================
            // CAMPO NOME
            // ==========================================

            OutlinedTextField(

                value = novoNome,

                onValueChange = {
                    novoNome = it
                },

                modifier = Modifier.fillMaxWidth(),

                label = {
                    Text("Nome do alarme")
                },

                placeholder = {
                    Text("Ex: Acordar")
                },

                singleLine = true,
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
                modifier = Modifier.height(10.dp)
            )


            // ==========================================
            // CAMPO DIAS
            // ==========================================

            OutlinedTextField(

                value = novosDias,

                onValueChange = {
                    novosDias = it
                },

                modifier = Modifier.fillMaxWidth(),

                label = {
                    Text("Dias")
                },

                placeholder = {
                    Text("Ex: Segunda a sexta")
                },

                singleLine = true,
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


            // ==========================================
            // BOTÃO ADICIONAR
            // ==========================================

            Button(

                onClick = {

                    if (
                        novoHorario.isNotBlank() &&
                        novoNome.isNotBlank()
                    ) {

                        val novoId =
                            (alarmes.maxOfOrNull { it.id } ?: 0) + 1

                        alarmes.add(

                            Alarme(
                                id = novoId,
                                horario = novoHorario,
                                nome = novoNome,
                                dias = novosDias.ifBlank {
                                    "Todos os dias"
                                },
                                ativo = true
                            )
                        )

                        novoHorario = ""
                        novoNome = ""
                        novosDias = ""
                    }
                },

                modifier = Modifier.fillMaxWidth()
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar"
                )

                Spacer(
                    modifier = Modifier.padding(4.dp)
                )

                Text("ADICIONAR ALARME")
            }


            Spacer(
                modifier = Modifier.height(24.dp)
            )


            // ==========================================
            // TÍTULO DA LISTA
            // ==========================================

            Text(
                text = "SEUS ALARMES",
                color = Texto,
                fontSize = 12.sp
            )


            Spacer(
                modifier = Modifier.height(10.dp)
            )


            // ==========================================
            // LISTA
            // ==========================================

            LazyColumn(

                modifier = Modifier.fillMaxSize(),

                verticalArrangement = Arrangement.spacedBy(
                    10.dp
                )
            ) {

                items(

                    items = alarmes,

                    key = {
                        it.id
                    }

                ) { alarme ->


                    // ======================================
                    // CARD DO ALARME
                    // ======================================

                    Card(

                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                onAbrirDetalhes(
                                    alarme.id
                                )
                            }
                    ) {

                        Row(

                            modifier = Modifier
                                .fillMaxWidth()
                                .background(CardFundo)
                                .padding(16.dp),

                            verticalAlignment =
                                Alignment.CenterVertically,

                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {


                            // ==============================
                            // INFORMAÇÕES
                            // ==============================

                            Column(

                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = alarme.horario,
                                    color = Texto,
                                    fontSize = 24.sp
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(
                                    text = alarme.nome,
                                    color = Texto,
                                    fontSize = 14.sp
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(
                                    text = alarme.dias,
                                    color = TextoSecundario,
                                    fontSize = 12.sp
                                )

                                Spacer(
                                    modifier = Modifier.height(6.dp)
                                )

                                Text(
                                    text =
                                        if (alarme.ativo)
                                            "● ATIVO"
                                        else
                                            "○ DESATIVADO",

                                    color =
                                        if (alarme.ativo)
                                            Azul
                                        else
                                            TextoSecundario,

                                    fontSize = 11.sp
                                )
                            }


                            // ==============================
                            // BOTÃO REMOVER
                            // ==============================

                            IconButton(

                                onClick = {

                                    alarmes.remove(
                                        alarme
                                    )
                                }

                            ) {

                                Icon(

                                    imageVector =
                                        Icons.Default.Delete,

                                    contentDescription =
                                        "Remover alarme",

                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
