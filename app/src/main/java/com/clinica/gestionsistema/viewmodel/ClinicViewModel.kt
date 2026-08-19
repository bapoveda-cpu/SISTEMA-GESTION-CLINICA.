package com.clinica.gestionsistema.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.clinica.gestionsistema.data.ClinicDatabase
import com.clinica.gestionsistema.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ClinicViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = ClinicDatabase.getDatabase(application).clinicDao()

    val allPatients: Flow<List<Patient>> = dao.getAllPatients()
    val allDoctors: Flow<List<Doctor>> = dao.getAllDoctors()
    val allAppointments: Flow<List<Appointment>> = dao.getAllAppointments()
    val allPrescriptions: Flow<List<Prescription>> = dao.getAllPrescriptions()
    val allMedicalReports: Flow<List<MedicalReport>> = dao.getAllMedicalReports()
    val allAttendance: Flow<List<Attendance>> = dao.getAllAttendance()

    fun addPatient(patient: Patient) = viewModelScope.launch {
        dao.insertPatient(patient)
    }

    fun addDoctor(doctor: Doctor) = viewModelScope.launch {
        dao.insertDoctor(doctor)
    }

    fun addAppointment(appointment: Appointment) = viewModelScope.launch {
        dao.insertAppointment(appointment)
    }
    
    fun addPrescription(prescription: Prescription) = viewModelScope.launch {
        dao.insertPrescription(prescription)
    }

    fun addMedicalReport(report: MedicalReport) = viewModelScope.launch {
        dao.insertMedicalReport(report)
    }

    fun addAttendance(attendance: Attendance) = viewModelScope.launch {
        dao.insertAttendance(attendance)
    }
}
