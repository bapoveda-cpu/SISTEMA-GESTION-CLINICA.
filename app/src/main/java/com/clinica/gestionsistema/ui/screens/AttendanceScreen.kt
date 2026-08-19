package com.clinica.gestionsistema.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.clinica.gestionsistema.model.Attendance
import com.clinica.gestionsistema.viewmodel.ClinicViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceScreen(navController: NavController, viewModel: ClinicViewModel = viewModel()) {
    val attendanceLogs by viewModel.allAttendance.collectAsState(initial = emptyList())
    var doctorId by remember { mutableStateOf("") }
    
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sistema de Marcaciones") },
                navigationIcon = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Registrar Marcación", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = doctorId,
                        onValueChange = { doctorId = it },
                        label = { Text("ID del Doctor") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        Button(
                            onClick = {
                                if (doctorId.isNotEmpty()) {
                                    viewModel.addAttendance(Attendance(doctorId = doctorId.toIntOrNull() ?: 0, timestamp = System.currentTimeMillis(), type = "ENTRADA"))
                                    doctorId = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text("Entrada")
                        }
                        Button(
                            onClick = {
                                if (doctorId.isNotEmpty()) {
                                    viewModel.addAttendance(Attendance(doctorId = doctorId.toIntOrNull() ?: 0, timestamp = System.currentTimeMillis(), type = "SALIDA"))
                                    doctorId = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Salida")
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            Text("Historial Reciente", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(attendanceLogs) { log ->
                    ListItem(
                        headlineContent = { Text("Doctor ID: ${log.doctorId}") },
                        supportingContent = { Text("Fecha: ${dateFormat.format(Date(log.timestamp))}") },
                        trailingContent = { 
                            Text(
                                log.type, 
                                color = if (log.type == "ENTRADA") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    )
                    Divider()
                }
            }
        }
    }
}
