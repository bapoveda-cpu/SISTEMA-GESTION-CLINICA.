package com.clinica.gestionsistema.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    // Definimos el degradado celeste (celeste claro a blanco/transparente)
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFB3E5FC), // Celeste claro
            Color(0xFFE1F5FE), // Celeste muy pálido
            Color.White        // O se opaca hacia el final
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Control", color = Color(0xFF01579B)) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFB3E5FC))
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(gradient) // Aplicamos el fondo en cascada
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { DashboardCard("Pacientes", Icons.Default.Person) { navController.navigate("patients") } }
                item { DashboardCard("Citas", Icons.Default.DateRange) { navController.navigate("appointments") } }
                item { DashboardCard("Doctores", Icons.Default.Face) { navController.navigate("doctors") } }
                item { DashboardCard("Recetas", Icons.Default.List) { navController.navigate("prescriptions") } }
                item { DashboardCard("Reportes", Icons.Default.Info) { navController.navigate("reports") } }
                item { DashboardCard("Marcaciones", Icons.Default.CheckCircle) { navController.navigate("attendance") } }

                // Información de la App - Ahora clickeable para ver detalles
                item(span = { GridItemSpan(2) }) {
                    Card(
                        onClick = { navController.navigate("info") },
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF0277BD))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Más Información", style = MaterialTheme.typography.titleMedium, color = Color(0xFF01579B))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Presiona aquí para conocer detalladamente el funcionamiento de cada módulo de la aplicación.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardCard(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.height(120.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = title, modifier = Modifier.size(40.dp), tint = Color(0xFF0288D1))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, color = Color(0xFF01579B), style = MaterialTheme.typography.labelLarge)
        }
    }
}
