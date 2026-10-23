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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val Fundo = Color(0xFF17120F)
private val Divisoria = Color(0xFF493427)
private val Madeira = Color(0xFFC58B55)
private val TextoBranco = Color(0xFFF6EBDD)
private val TextoInativo = Color(0xFFBCA18A)
private val Indicador = Color(0xFF493023)

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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
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
                alarmeDao.inserir(Alarme(horario = "07:00", nome = "Acordar", dias = "Segunda a sexta", ativo = true))
                alarmeDao.inserir(Alarme(horario = "12:00", nome = "Almoço", dias = "Todos os dias", ativo = false))
            }
            if (fusoDao.buscarTodos().isEmpty()) {
                fusoDao.inserir(FusoHorario(cidade = "São Paulo", pais = "Brasil", zona = "America/Sao_Paulo", offset = -3))
                fusoDao.inserir(FusoHorario(cidade = "New York", pais = "Estados Unidos", zona = "America/New_York", offset = -4))
                fusoDao.inserir(FusoHorario(cidade = "Londres", pais = "Reino Unido", zona = "Europe/London", offset = 1))
            }
            Pair(alarmeDao.buscarTodos(), fusoDao.buscarTodos())
        }
        alarmes.clear()
        alarmes.addAll(alarmesSalvos)
        fusos.clear()
        fusos.addAll(fusosSalvos)
    }

    val mostrarBarra = rotaAtual == Rotas.RELOGIO || rotaAtual == Rotas.ALARMES || rotaAtual == Rotas.CRONOMETRO || rotaAtual == Rotas.TIMER

    Scaffold(
        containerColor = Fundo,
        bottomBar = {
            if (mostrarBarra) {
                Column(modifier = Modifier.background(Fundo)) {
                    Spacer(
                        modifier = Modifier.fillMaxWidth().height(1.dp).background(Divisoria)
                    )
                    NavigationBar(
                        containerColor = Fundo,
                        contentColor = TextoBranco,
                        tonalElevation = 0.dp
                    ) {
                        NavigationBarItem(
                            selected = rotaAtual == Rotas.RELOGIO,
                            onClick = { navController.navigate(Rotas.RELOGIO) { popUpTo(Rotas.RELOGIO) { inclusive = false }; launchSingleTop = true } },
                            icon = { Text("🕑") },
                            label = { Text("Relógio") },
                            colors = NavigationBarItemDefaults.colors(selectedIconColor = Madeira, selectedTextColor = Madeira, unselectedIconColor = TextoBranco, unselectedTextColor = TextoInativo, indicatorColor = Indicador)
                        )
                        NavigationBarItem(
                            selected = rotaAtual == Rotas.ALARMES,
                            onClick = { navController.navigate(Rotas.ALARMES) { launchSingleTop = true } },
                            icon = { Text("⏰") },
                            label = { Text("Alarmes") },
                            colors = NavigationBarItemDefaults.colors(selectedIconColor = Madeira, selectedTextColor = Madeira, unselectedIconColor = TextoBranco, unselectedTextColor = TextoInativo, indicatorColor = Indicador)
                        )
                        NavigationBarItem(
                            selected = rotaAtual == Rotas.CRONOMETRO,
                            onClick = { navController.navigate(Rotas.CRONOMETRO) { launchSingleTop = true } },
                            icon = { Text("⏱️") },
                            label = { Text("Cronômetro") },
                            colors = NavigationBarItemDefaults.colors(selectedIconColor = Madeira, selectedTextColor = Madeira, unselectedIconColor = TextoBranco, unselectedTextColor = TextoInativo, indicatorColor = Indicador)
                        )
                        NavigationBarItem(
                            selected = rotaAtual == Rotas.TIMER,
                            onClick = { navController.navigate(Rotas.TIMER) { launchSingleTop = true } },
                            icon = { Text("⏲️") },
                            label = { Text("Timer") },
                            colors = NavigationBarItemDefaults.colors(selectedIconColor = Madeira, selectedTextColor = Madeira, unselectedIconColor = TextoBranco, unselectedTextColor = TextoInativo, indicatorColor = Indicador)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = Rotas.RELOGIO) {
            composable(Rotas.RELOGIO) {
                RelogioScreen(
                    paddingValues = paddingValues,
                    fusos = fusos,
                    onAbrirAlarmes = { navController.navigate(Rotas.ALARMES) },
                    onAbrirFusos = { navController.navigate(Rotas.FUSOS) },
                    onAbrirDetalhesFuso = { id -> navController.navigate("detalhe_fuso/$id") }
                )
            }
            composable(Rotas.ALARMES) {
                AlarmesScreen(
                    paddingValues = paddingValues,
                    alarmes = alarmes,
                    onVoltar = { navController.navigate(Rotas.RELOGIO) { launchSingleTop = true } },
                    onAbrirDetalhes = { id -> navController.navigate("detalhe_alarme/$id") }
                )
            }
            composable(Rotas.CRONOMETRO) { CronometroScreen(paddingValues = paddingValues) }
            composable(Rotas.TIMER) { TimerScreen(paddingValues = paddingValues) }
            composable(Rotas.FUSOS) {
                FusosScreen(
                    paddingValues = paddingValues,
                    fusos = fusos,
                    onVoltar = { navController.popBackStack() },
                    onAbrirDetalhes = { id -> navController.navigate("detalhe_fuso/$id") }
                )
            }
            composable(
                route = Rotas.DETALHE_ALARME,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { entry ->
                DetalheAlarmeScreen(
                    id = entry.arguments?.getInt("id") ?: -1,
                    alarmes = alarmes,
                    onVoltar = { navController.popBackStack() }
                )
            }
            composable(
                route = Rotas.DETALHE_FUSO,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { entry ->
                DetalheFusoScreen(
                    id = entry.arguments?.getInt("id") ?: -1,
                    fusos = fusos,
                    onVoltar = { navController.popBackStack() }
                )
            }
        }
    }
}
