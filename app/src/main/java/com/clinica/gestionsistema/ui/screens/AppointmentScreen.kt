package com.clinica.gestionsistema.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.clinica.gestionsistema.model.Appointment
import com.clinica.gestionsistema.viewmodel.ClinicViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentScreen(navController: NavController, viewModel: ClinicViewModel = viewModel()) {
    val appointments by viewModel.allAppointments.collectAsState(initial = emptyList())
    val patients by viewModel.allPatients.collectAsState(initial = emptyList())
    val doctors by viewModel.allDoctors.collectAsState(initial = emptyList())
    
    var showDialog by remember { mutableStateOf(false) }
    var selectedAppointment by remember { mutableStateOf<Appointment?>(null) }
    var appointmentToDelete by remember { mutableStateOf<Appointment?>(null) }

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
                val patient = patients.find { it.id == appointment.patientId }
                val doctor = doctors.find { it.id == appointment.doctorId }
                val patientName = patient?.name ?: "Desconocido"
                val doctorName = doctor?.name ?: "Desconocido"

                ListItem(
                    modifier = Modifier.clickable { selectedAppointment = appointment },
                    headlineContent = { Text("Paciente: $patientName") },
                    supportingContent = { 
                        Column {
                            Text("Doctor: $doctorName")
                            Text("Fecha: ${appointment.date} - ${appointment.time}")
                        }
                    },
                    overlineContent = { Text("Estado: ${appointment.status}") },
                    trailingContent = {
                        IconButton(onClick = { appointmentToDelete = appointment }) {
                            Icon(Icons.Default.Delete, contentDescription = "Cancelar Cita", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                )
                Divider()
            }
        }

        if (appointmentToDelete != null) {
            AlertDialog(
                onDismissRequest = { appointmentToDelete = null },
                title = { Text("Confirmar cancelación") },
                text = { Text("¿Estás seguro de cancelar la cita?") },
                confirmButton = {
                    Button(
                        onClick = {
                            appointmentToDelete?.let { viewModel.removeAppointment(it) }
                            appointmentToDelete = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { appointmentToDelete = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        if (showDialog) {
            AddAppointmentDialog(
                appointments = appointments,
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

        selectedAppointment?.let { appointment ->
            val patient = patients.find { it.id == appointment.patientId }
            val doctor = doctors.find { it.id == appointment.doctorId }
            
            AlertDialog(
                onDismissRequest = { selectedAppointment = null },
                title = { Text("Detalle de la Cita") },
                text = {
                    Column {
                        Text("Información del Paciente:", style = MaterialTheme.typography.titleSmall)
                        Text("Nombre: ${patient?.name ?: "N/A"}")
                        Text("DNI/ID: ${patient?.id ?: "N/A"}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Información del Doctor:", style = MaterialTheme.typography.titleSmall)
                        Text("Nombre: ${doctor?.name ?: "N/A"}")
                        Text("ID Doctor: ${doctor?.id ?: "N/A"}")
                        Text("Especialidad: ${doctor?.specialty ?: "N/A"}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Horario:", style = MaterialTheme.typography.titleSmall)
                        Text("Fecha: ${appointment.date}")
                        Text("Hora: ${appointment.time}")
                    }
                },
                confirmButton = {
                    Button(onClick = { selectedAppointment = null }) { Text("Cerrar") }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAppointmentDialog(
    appointments: List<Appointment>,
    onDismiss: () -> Unit, 
    onConfirm: (String, String, String, String) -> Unit
) {
    var patientId by remember { mutableStateOf("") }
    var doctorId by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    
    // Date Picker Dialog
    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            date = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Time Picker Dialog
    val timePickerDialog = android.app.TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            time = String.format("%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Programar Nueva Cita") },
        text = {
            Column {
                TextField(
                    value = patientId, 
                    onValueChange = { 
                        patientId = it
                        errorMessage = null 
                    }, 
                    label = { Text("ID Paciente") }, 
                    modifier = Modifier.fillMaxWidth(),
                    isError = patientId.isEmpty() && errorMessage != null
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = doctorId, 
                    onValueChange = { 
                        doctorId = it
                        errorMessage = null
                    }, 
                    label = { Text("ID Doctor") }, 
                    modifier = Modifier.fillMaxWidth(),
                    isError = doctorId.isEmpty() && errorMessage != null
                )
                
                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                
                // Clickable TextField for Date
                OutlinedTextField(
                    value = date,
                    onValueChange = { },
                    label = { Text("Fecha") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth().clickable { datePickerDialog.show() },
                    enabled = false, 
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    trailingIcon = {
                        IconButton(onClick = { datePickerDialog.show() }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Seleccionar Fecha")
                        }
                    }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Clickable TextField for Time
                OutlinedTextField(
                    value = time,
                    onValueChange = { },
                    label = { Text("Hora") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth().clickable { timePickerDialog.show() },
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    trailingIcon = {
                        IconButton(onClick = { timePickerDialog.show() }) {
                            Icon(Icons.Default.Notifications, contentDescription = "Seleccionar Hora") 
                        }
                    }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val docIdInt = doctorId.toIntOrNull()
                    val patIdInt = patientId.toIntOrNull()

                    if (patientId.isEmpty() || doctorId.isEmpty() || date.isEmpty() || time.isEmpty()) {
                        errorMessage = "Todos los campos son obligatorios"
                    } else if (docIdInt == null || patIdInt == null) {
                        errorMessage = "Los IDs deben ser numéricos"
                    } else {
                        // Validar límite de 15 citas por doctor
                        val appointmentsCount = appointments.count { it.doctorId == docIdInt }
                        if (appointmentsCount >= 15) {
                            errorMessage = "La agenda del doctor está llena (máx. 15 citas)"
                        } else {
                            onConfirm(patientId, doctorId, date, time)
                        }
                    }
                }
            ) { 
                Text("Agendar") 
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
