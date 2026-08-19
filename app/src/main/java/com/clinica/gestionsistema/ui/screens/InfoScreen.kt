package com.clinica.gestionsistema.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Información del Sistema") },
                navigationIcon = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                "Guía de Funcionamiento",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            InfoSection(
                title = "1. Gestión de Pacientes",
                description = "Permite registrar nuevos pacientes ingresando su nombre, DNI y edad. La información se almacena de forma permanente para consultas futuras."
            )

            InfoSection(
                title = "2. Agendamiento de Citas",
                description = "Módulo para programar consultas médicas vinculando a un paciente con un doctor en una fecha y hora específica."
            )

            InfoSection(
                title = "3. Administración de Doctores",
                description = "Registro de personal médico, especialidades y datos de contacto. Es fundamental para la asignación de citas y reportes."
            )

            InfoSection(
                title = "4. Recetas Médicas",
                description = "Generación de prescripciones digitales detallando el medicamento, la dosis y las instrucciones de uso para el paciente."
            )

            InfoSection(
                title = "5. Reportes Médicos",
                description = "Consolidación del historial clínico. Permite redactar informes detallados sobre la evolución y diagnóstico del paciente."
            )

            InfoSection(
                title = "6. Sistema de Marcaciones",
                description = "Control de asistencia para el personal médico, permitiendo registrar la hora exacta de entrada y salida."
            )

            Spacer(modifier = Modifier.height(24.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(
                    "Desarrollado para la optimización de servicios de salud mediante una arquitectura moderna en Kotlin y Jetpack Compose.",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun InfoSection(title: String, description: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        Text(description, style = MaterialTheme.typography.bodyMedium)
        Divider(modifier = Modifier.padding(top = 8.dp))
    }
}
