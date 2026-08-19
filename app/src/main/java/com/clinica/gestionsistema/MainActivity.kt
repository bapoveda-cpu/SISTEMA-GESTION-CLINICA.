package com.clinica.gestionsistema

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.clinica.gestionsistema.ui.screens.*

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF01579B),
                    background = Color.White,
                    surface = Color.White
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ClinicApp()
                }
            }
        }
    }
}

@Composable
fun ClinicApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("dashboard") { DashboardScreen(navController) }
        composable("patients") { PatientScreen(navController) }
        composable("appointments") { AppointmentScreen(navController) }
        composable("doctors") { DoctorScreen(navController) }
        composable("prescriptions") { PrescriptionScreen(navController) }
        composable("reports") { ReportScreen(navController) }
        composable("attendance") { AttendanceScreen(navController) }
        composable("info") { InfoScreen(navController) }
    }
}
