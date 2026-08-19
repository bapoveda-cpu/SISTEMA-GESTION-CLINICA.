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
import com.clinica.gestionsistema.model.Appointment
import com.clinica.gestionsistema.viewmodel.ClinicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentScreen(navController: NavController, viewModel: ClinicViewModel = viewModel()) {
    val appointments by viewModel.allAppointments.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Citas") },
                navigationIcon = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("Atrás")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Nueva Cita")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(appointments) { appointment ->
                ListItem(
                    headlineContent = { Text("Cita: ${appointment.date} a las ${appointment.time}") },
                    supportingContent = { Text("Paciente ID: ${appointment.patientId} - Doctor ID: ${appointment.doctorId}") },
                    overlineContent = { Text("Estado: ${appointment.status}") }
                )
                Divider()
            }
        }

        if (showDialog) {
            AddAppointmentDialog(
                onDismiss = { showDialog = false },
                onConfirm = { patientId, doctorId, date, time ->
                    viewModel.addAppointment(
                        Appointment(
                            patientId = patientId.toIntOrNull() ?: 0,
                            doctorId = doctorId.toIntOrNull() ?: 0,
                            date = date,
                            time = time,
                            status = "PENDIENTE"
                        )
                    )
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun AddAppointmentDialog(onDismiss: () -> Unit, onConfirm: (String, String, String, String) -> Unit) {
    var patientId by remember { mutableStateOf("") }
    var doctorId by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Programar Nueva Cita") },
        text = {
            Column {
                TextField(value = patientId, onValueChange = { patientId = it }, label = { Text("ID Paciente") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = doctorId, onValueChange = { doctorId = it }, label = { Text("ID Doctor") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = date, onValueChange = { date = it }, label = { Text("Fecha (DD/MM/AAAA)") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = time, onValueChange = { time = it }, label = { Text("Hora (HH:MM)") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(patientId, doctorId, date, time) }) { Text("Agendar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
