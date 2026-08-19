package com.clinica.gestionsistema.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.clinica.gestionsistema.model.Patient
import com.clinica.gestionsistema.viewmodel.ClinicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientScreen(navController: NavController, viewModel: ClinicViewModel = viewModel()) {
    val patients by viewModel.allPatients.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Pacientes") },
                navigationIcon = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("Atrás")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Paciente")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(patients) { patient ->
                ListItem(
                    headlineContent = { Text(patient.name) },
                    supportingContent = { Text("DNI: ${patient.dni} - Edad: ${patient.age}") }
                )
                Divider()
            }
        }

        if (showDialog) {
            AddPatientDialog(
                onDismiss = { showDialog = false },
                onConfirm = { name, dni, age ->
                    viewModel.addPatient(Patient(name = name, dni = dni, age = age.toIntOrNull() ?: 0, gender = "M", phone = ""))
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun AddPatientDialog(onDismiss: () -> Unit, onConfirm: (String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo Paciente") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
                TextField(value = dni, onValueChange = { dni = it }, label = { Text("DNI") })
                TextField(value = age, onValueChange = { age = it }, label = { Text("Edad") })
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(name, dni, age) }) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
