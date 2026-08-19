package com.clinica.gestionsistema.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.clinica.gestionsistema.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClinicDao {
    @Insert
    suspend fun insertUser(user: User)
    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): User?

    @Insert
    suspend fun insertDoctor(doctor: Doctor)
    @Query("SELECT * FROM doctors")
    fun getAllDoctors(): Flow<List<Doctor>>

    @Insert
    suspend fun insertPatient(patient: Patient)
    @Query("SELECT * FROM patients")
    fun getAllPatients(): Flow<List<Patient>>

    @Insert
    suspend fun insertAppointment(appointment: Appointment)
    @Query("SELECT * FROM appointments")
    fun getAllAppointments(): Flow<List<Appointment>>

    @Insert
    suspend fun insertPrescription(prescription: Prescription)
    @Query("SELECT * FROM prescriptions")
    fun getAllPrescriptions(): Flow<List<Prescription>>

    @Insert
    suspend fun insertMedicalReport(report: MedicalReport)
    @Query("SELECT * FROM medical_reports")
    fun getAllMedicalReports(): Flow<List<MedicalReport>>

    @Insert
    suspend fun insertAttendance(attendance: Attendance)
    @Query("SELECT * FROM attendance ORDER BY timestamp DESC")
    fun getAllAttendance(): Flow<List<Attendance>>
}

@Database(entities = [User::class, Doctor::class, Patient::class, Appointment::class, Prescription::class, MedicalReport::class, Attendance::class], version = 1, exportSchema = false)
abstract class ClinicDatabase : RoomDatabase() {
    abstract fun clinicDao(): ClinicDao

    companion object {
        @Volatile
        private var INSTANCE: ClinicDatabase? = null

        fun getDatabase(context: Context): ClinicDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ClinicDatabase::class.java,
                    "clinic_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
