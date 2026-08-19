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
import com.clinica.gestionsistema.model.Doctor
import com.clinica.gestionsistema.viewmodel.ClinicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorScreen(navController: NavController, viewModel: ClinicViewModel = viewModel()) {
    val doctors by viewModel.allDoctors.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Administración de Doctores") },
                navigationIcon = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("Atrás")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Doctor")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(doctors) { doctor ->
                ListItem(
                    headlineContent = { Text(doctor.name) },
                    supportingContent = { Text("Especialidad: ${doctor.specialty} - Tel: ${doctor.phone}") },
                    overlineContent = { Text("ID: ${doctor.id}") }
                )
                Divider()
            }
        }

        if (showDialog) {
            AddDoctorDialog(
                onDismiss = { showDialog = false },
                onConfirm = { name, specialty, phone ->
                    viewModel.addDoctor(Doctor(name = name, specialty = specialty, phone = phone))
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun AddDoctorDialog(onDismiss: () -> Unit, onConfirm: (String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var specialty by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo Doctor") },
        text = {
            Column {
                TextField(
                    value = name, 
                    onValueChange = { name = it }, 
                    label = { Text("Nombre Completo") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = specialty, 
                    onValueChange = { specialty = it }, 
                    label = { Text("Especialidad (Ej: Cardiología)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = phone, 
                    onValueChange = { phone = it }, 
                    label = { Text("Teléfono") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(name, specialty, phone) }) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
