package com.clinica.gestionsistema.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val password: String,
    val role: String // "ADMIN" or "USER"
)

@Entity(tableName = "doctors")
data class Doctor(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val specialty: String,
    val phone: String
)

@Entity(tableName = "patients")
data class Patient(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val dni: String,
    val age: Int,
    val gender: String,
    val phone: String
)

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val patientId: Int,
    val doctorId: Int,
    val date: String,
    val time: String,
    val status: String // "PENDING", "COMPLETED", "CANCELLED"
)

@Entity(tableName = "prescriptions")
data class Prescription(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val appointmentId: Int,
    val medicine: String,
    val dosage: String,
    val instructions: String
)

@Entity(tableName = "medical_reports")
data class MedicalReport(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val patientId: Int,
    val doctorId: Int,
    val content: String,
    val date: String
)

@Entity(tableName = "attendance")
data class Attendance(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val doctorId: Int,
    val timestamp: Long,
    val type: String // "IN" or "OUT"
)
