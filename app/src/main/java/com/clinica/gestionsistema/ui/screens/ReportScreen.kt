package com.clinica.gestionsistema.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.clinica.gestionsistema.model.MedicalReport
import com.clinica.gestionsistema.viewmodel.ClinicViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(navController: NavController, viewModel: ClinicViewModel = viewModel()) {
    val reports by viewModel.allMedicalReports.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Reportes Médicos") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo Reporte")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(reports) { report ->
                Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Fecha: ${report.date}", style = MaterialTheme.typography.labelSmall)
                        Text("Paciente ID: ${report.patientId}", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(report.content)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Doctor ID: ${report.doctorId}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        if (showDialog) {
            AddReportDialog(
                onDismiss = { showDialog = false },
                onConfirm = { patientId, doctorId, content ->
                    val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                    viewModel.addMedicalReport(
                        MedicalReport(
                            patientId = patientId.toIntOrNull() ?: 0,
                            doctorId = doctorId.toIntOrNull() ?: 0,
                            content = content,
                            date = currentDate
                        )
                    )
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun AddReportDialog(onDismiss: () -> Unit, onConfirm: (String, String, String) -> Unit) {
    var patientId by remember { mutableStateOf("") }
    var doctorId by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo Reporte Médico") },
        text = {
            Column {
                TextField(
                    value = patientId, 
                    onValueChange = { patientId = it }, 
                    label = { Text("ID del Paciente") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = doctorId, 
                    onValueChange = { doctorId = it }, 
                    label = { Text("ID del Doctor") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = content, 
                    onValueChange = { content = it }, 
                    label = { Text("Contenido del Reporte") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(patientId, doctorId, content) }) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
