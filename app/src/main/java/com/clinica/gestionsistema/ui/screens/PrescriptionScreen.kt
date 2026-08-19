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
import com.clinica.gestionsistema.model.Prescription
import com.clinica.gestionsistema.viewmodel.ClinicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionScreen(navController: NavController, viewModel: ClinicViewModel = viewModel()) {
    val prescriptions by viewModel.allPrescriptions.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Recetas Médicas") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Generar Receta")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(prescriptions) { prescription ->
                ListItem(
                    headlineContent = { Text(prescription.medicine) },
                    supportingContent = { Text("Dosis: ${prescription.dosage}\nInstrucciones: ${prescription.instructions}") },
                    overlineContent = { Text("Cita ID: ${prescription.appointmentId}") }
                )
                Divider()
            }
        }

        if (showDialog) {
            AddPrescriptionDialog(
                onDismiss = { showDialog = false },
                onConfirm = { appointmentId, medicine, dosage, instructions ->
                    viewModel.addPrescription(
                        Prescription(
                            appointmentId = appointmentId.toIntOrNull() ?: 0,
                            medicine = medicine,
                            dosage = dosage,
                            instructions = instructions
                        )
                    )
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun AddPrescriptionDialog(onDismiss: () -> Unit, onConfirm: (String, String, String, String) -> Unit) {
    var appointmentId by remember { mutableStateOf("") }
    var medicine by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nueva Receta") },
        text = {
            Column {
                TextField(
                    value = appointmentId, 
                    onValueChange = { appointmentId = it }, 
                    label = { Text("ID de Cita") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = medicine, 
                    onValueChange = { medicine = it }, 
                    label = { Text("Medicamento") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = dosage, 
                    onValueChange = { dosage = it }, 
                    label = { Text("Dosis") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = instructions, 
                    onValueChange = { instructions = it }, 
                    label = { Text("Instrucciones") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(appointmentId, medicine, dosage, instructions) }) { Text("Generar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
