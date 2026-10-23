package com.example.tungclock

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val Fundo = Color(0xFF0B0E17)
private val Divisoria = Color(0xFF303748)
private val Roxo = Color(0xFF9B7CFF)
private val TextoBranco = Color.White
private val TextoInativo = Color(0xFF9CA3B5)

object Rotas {
    const val RELOGIO = "relogio"
    const val CRONOMETRO = "cronometro"
    const val TIMER = "timer"

    const val ALARMES = "alarmes"
    const val DETALHE_ALARME = "detalhe_alarme/{id}"

    const val FUSOS = "fusos"
    const val DETALHE_FUSO = "detalhe_fuso/{id}"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val navBackStackEntry by
    navController.currentBackStackEntryAsState()

    val rotaAtual = navBackStackEntry?.destination?.route


    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }

    val alarmes = remember { mutableStateListOf<Alarme>() }
    val fusos = remember { mutableStateListOf<FusoHorario>() }

    LaunchedEffect(Unit) {
        val (alarmesSalvos, fusosSalvos) = withContext(Dispatchers.IO) {
            val alarmeDao = database.alarmeDao()
            val fusoDao = database.fusoHorarioDao()

            if (alarmeDao.buscarTodos().isEmpty()) {
                alarmeDao.inserir(
                    Alarme(
                        horario = "07:00",
                        nome = "Acordar",
                        dias = "Segunda a sexta",
                        ativo = true
                    )
                )
                alarmeDao.inserir(
                    Alarme(
                        horario = "12:00",
                        nome = "Almoço",
                        dias = "Todos os dias",
                        ativo = false
                    )
                )
            }

            if (fusoDao.buscarTodos().isEmpty()) {
                fusoDao.inserir(
                    FusoHorario(
                        cidade = "São Paulo",
                        pais = "Brasil",
                        zona = "America/Sao_Paulo",
                        offset = -3
                    )
                )
                fusoDao.inserir(
                    FusoHorario(
                        cidade = "New York",
                        pais = "Estados Unidos",
                        zona = "America/New_York",
                        offset = -4
                    )
                )
                fusoDao.inserir(
                    FusoHorario(
                        cidade = "Londres",
                        pais = "Reino Unido",
                        zona = "Europe/London",
                        offset = 1
                    )
                )
            }

            Pair(
                alarmeDao.buscarTodos(),
                fusoDao.buscarTodos()
            )
        }

        alarmes.clear()
        alarmes.addAll(alarmesSalvos)

        fusos.clear()
        fusos.addAll(fusosSalvos)
    }


    Scaffold(
        containerColor = Fundo,

        bottomBar = {
            Column(
                modifier = Modifier.background(Fundo)
            ) {

                // Divisória superior da barra inferior
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Divisoria)
                )

                NavigationBar(
                    containerColor = Fundo,
                    contentColor = TextoBranco,
                    tonalElevation = 0.dp
                ) {

                    // RELÓGIO
                    NavigationBarItem(
                        selected = rotaAtual == Rotas.RELOGIO,
                        onClick = {
                            navController.navigate(Rotas.RELOGIO) {
                                popUpTo(Rotas.RELOGIO) {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Text(
                                text = "◉",
                                color = if (rotaAtual == Rotas.RELOGIO)
                                    Roxo else TextoBranco
                            )
                        },
                        label = {
                            Text("Relógio")
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Roxo,
                            selectedTextColor = Roxo,
                            unselectedIconColor = TextoBranco,
                            unselectedTextColor = TextoInativo,
                            indicatorColor = Color(0xFF252039)
                        )
                    )

                    // CRONÔMETRO
                    NavigationBarItem(
                        selected = rotaAtual == Rotas.CRONOMETRO,
                        onClick = {
                            navController.navigate(Rotas.CRONOMETRO) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Text(
                                text = "⏱",
                                color = if (rotaAtual == Rotas.CRONOMETRO)
                                    Roxo else TextoBranco
                            )
                        },
                        label = {
                            Text("Crono")
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Roxo,
                            selectedTextColor = Roxo,
                            unselectedIconColor = TextoBranco,
                            unselectedTextColor = TextoInativo,
                            indicatorColor = Color(0xFF252039)
                        )
                    )

                    // TIMER
                    NavigationBarItem(
                        selected = rotaAtual == Rotas.TIMER,
                        onClick = {
                            navController.navigate(Rotas.TIMER) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Text(
                                text = "⏰",
                                color = if (rotaAtual == Rotas.TIMER)
                                    Roxo else TextoBranco
                            )
                        },
                        label = {
                            Text("Timer")
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Roxo,
                            selectedTextColor = Roxo,
                            unselectedIconColor = TextoBranco,
                            unselectedTextColor = TextoInativo,
                            indicatorColor = Color(0xFF252039)
                        )
                    )
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Rotas.RELOGIO
        ) {

            // RELÓGIO
            composable(Rotas.RELOGIO) {
                RelogioScreen(
                    paddingValues = paddingValues,
                    onAbrirAlarmes = {
                        navController.navigate(Rotas.ALARMES)
                    },
                    onAbrirFusos = {
                        navController.navigate(Rotas.FUSOS)
                    }
                )
            }

            // CRONÔMETRO
            composable(Rotas.CRONOMETRO) {
                CronometroScreen(
                    paddingValues = paddingValues
                )
            }

            // TIMER
            composable(Rotas.TIMER) {
                TimerScreen(
                    paddingValues = paddingValues
                )
            }

            // LISTA DE ALARMES
            composable(Rotas.ALARMES) {
                AlarmesScreen(
                    paddingValues = paddingValues,
                    alarmes = alarmes,
                    onVoltar = {
                        navController.popBackStack()
                    },
                    onAbrirDetalhes = { id ->
                        navController.navigate("detalhe_alarme/$id")
                    }
                )
            }

            // DETALHES DO ALARME
            composable(
                route = Rotas.DETALHE_ALARME,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val id =
                    backStackEntry.arguments?.getInt("id") ?: -1

                DetalheAlarmeScreen(
                    id = id,
                    alarmes = alarmes,
                    onVoltar = {
                        navController.popBackStack()
                    }
                )
            }

            // LISTA DE FUSOS
            composable(Rotas.FUSOS) {
                FusosScreen(
                    paddingValues = paddingValues,
                    fusos = fusos,
                    onVoltar = {
                        navController.popBackStack()
                    },
                    onAbrirDetalhes = { id ->
                        navController.navigate("detalhe_fuso/$id")
                    }
                )
            }

            // DETALHES DO FUSO
            composable(
                route = Rotas.DETALHE_FUSO,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val id =
                    backStackEntry.arguments?.getInt("id") ?: -1

                DetalheFusoScreen(
                    id = id,
                    fusos = fusos,
                    onVoltar = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}