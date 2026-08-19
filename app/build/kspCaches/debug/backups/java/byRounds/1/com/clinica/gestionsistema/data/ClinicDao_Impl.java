package com.clinica.gestionsistema.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.clinica.gestionsistema.model.Appointment;
import com.clinica.gestionsistema.model.Attendance;
import com.clinica.gestionsistema.model.Doctor;
import com.clinica.gestionsistema.model.MedicalReport;
import com.clinica.gestionsistema.model.Patient;
import com.clinica.gestionsistema.model.Prescription;
import com.clinica.gestionsistema.model.User;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ClinicDao_Impl implements ClinicDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final EntityInsertionAdapter<Doctor> __insertionAdapterOfDoctor;

  private final EntityInsertionAdapter<Patient> __insertionAdapterOfPatient;

  private final EntityInsertionAdapter<Appointment> __insertionAdapterOfAppointment;

  private final EntityInsertionAdapter<Prescription> __insertionAdapterOfPrescription;

  private final EntityInsertionAdapter<MedicalReport> __insertionAdapterOfMedicalReport;

  private final EntityInsertionAdapter<Attendance> __insertionAdapterOfAttendance;

  public ClinicDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `users` (`id`,`username`,`password`,`role`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUsername());
        statement.bindString(3, entity.getPassword());
        statement.bindString(4, entity.getRole());
      }
    };
    this.__insertionAdapterOfDoctor = new EntityInsertionAdapter<Doctor>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `doctors` (`id`,`name`,`specialty`,`phone`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Doctor entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getSpecialty());
        statement.bindString(4, entity.getPhone());
      }
    };
    this.__insertionAdapterOfPatient = new EntityInsertionAdapter<Patient>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `patients` (`id`,`name`,`dni`,`age`,`gender`,`phone`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Patient entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getDni());
        statement.bindLong(4, entity.getAge());
        statement.bindString(5, entity.getGender());
        statement.bindString(6, entity.getPhone());
      }
    };
    this.__insertionAdapterOfAppointment = new EntityInsertionAdapter<Appointment>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `appointments` (`id`,`patientId`,`doctorId`,`date`,`time`,`status`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Appointment entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPatientId());
        statement.bindLong(3, entity.getDoctorId());
        statement.bindString(4, entity.getDate());
        statement.bindString(5, entity.getTime());
        statement.bindString(6, entity.getStatus());
      }
    };
    this.__insertionAdapterOfPrescription = new EntityInsertionAdapter<Prescription>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `prescriptions` (`id`,`appointmentId`,`medicine`,`dosage`,`instructions`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Prescription entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getAppointmentId());
        statement.bindString(3, entity.getMedicine());
        statement.bindString(4, entity.getDosage());
        statement.bindString(5, entity.getInstructions());
      }
    };
    this.__insertionAdapterOfMedicalReport = new EntityInsertionAdapter<MedicalReport>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `medical_reports` (`id`,`patientId`,`doctorId`,`content`,`date`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MedicalReport entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPatientId());
        statement.bindLong(3, entity.getDoctorId());
        statement.bindString(4, entity.getContent());
        statement.bindString(5, entity.getDate());
      }
    };
    this.__insertionAdapterOfAttendance = new EntityInsertionAdapter<Attendance>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `attendance` (`id`,`doctorId`,`timestamp`,`type`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Attendance entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDoctorId());
        statement.bindLong(3, entity.getTimestamp());
        statement.bindString(4, entity.getType());
      }
    };
  }

  @Override
  public Object insertUser(final User user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUser.insert(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertDoctor(final Doctor doctor, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDoctor.insert(doctor);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPatient(final Patient patient, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPatient.insert(patient);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAppointment(final Appointment appointment,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAppointment.insert(appointment);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPrescription(final Prescription prescription,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPrescription.insert(prescription);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertMedicalReport(final MedicalReport report,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMedicalReport.insert(report);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAttendance(final Attendance attendance,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAttendance.insert(attendance);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object login(final String username, final String password,
      final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM users WHERE username = ? AND password = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, username);
    _argIndex = 2;
    _statement.bindString(_argIndex, password);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final User _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpPassword;
            _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
            final String _tmpRole;
            _tmpRole = _cursor.getString(_cursorIndexOfRole);
            _result = new User(_tmpId,_tmpUsername,_tmpPassword,_tmpRole);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Doctor>> getAllDoctors() {
    final String _sql = "SELECT * FROM doctors";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"doctors"}, new Callable<List<Doctor>>() {
      @Override
      @NonNull
      public List<Doctor> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSpecialty = CursorUtil.getColumnIndexOrThrow(_cursor, "specialty");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final List<Doctor> _result = new ArrayList<Doctor>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Doctor _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpSpecialty;
            _tmpSpecialty = _cursor.getString(_cursorIndexOfSpecialty);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            _item = new Doctor(_tmpId,_tmpName,_tmpSpecialty,_tmpPhone);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Patient>> getAllPatients() {
    final String _sql = "SELECT * FROM patients";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"patients"}, new Callable<List<Patient>>() {
      @Override
      @NonNull
      public List<Patient> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDni = CursorUtil.getColumnIndexOrThrow(_cursor, "dni");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final List<Patient> _result = new ArrayList<Patient>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Patient _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDni;
            _tmpDni = _cursor.getString(_cursorIndexOfDni);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpGender;
            _tmpGender = _cursor.getString(_cursorIndexOfGender);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            _item = new Patient(_tmpId,_tmpName,_tmpDni,_tmpAge,_tmpGender,_tmpPhone);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Appointment>> getAllAppointments() {
    final String _sql = "SELECT * FROM appointments";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"appointments"}, new Callable<List<Appointment>>() {
      @Override
      @NonNull
      public List<Appointment> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPatientId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientId");
          final int _cursorIndexOfDoctorId = CursorUtil.getColumnIndexOrThrow(_cursor, "doctorId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<Appointment> _result = new ArrayList<Appointment>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Appointment _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPatientId;
            _tmpPatientId = _cursor.getInt(_cursorIndexOfPatientId);
            final int _tmpDoctorId;
            _tmpDoctorId = _cursor.getInt(_cursorIndexOfDoctorId);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpTime;
            _tmpTime = _cursor.getString(_cursorIndexOfTime);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            _item = new Appointment(_tmpId,_tmpPatientId,_tmpDoctorId,_tmpDate,_tmpTime,_tmpStatus);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Prescription>> getAllPrescriptions() {
    final String _sql = "SELECT * FROM prescriptions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"prescriptions"}, new Callable<List<Prescription>>() {
      @Override
      @NonNull
      public List<Prescription> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppointmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "appointmentId");
          final int _cursorIndexOfMedicine = CursorUtil.getColumnIndexOrThrow(_cursor, "medicine");
          final int _cursorIndexOfDosage = CursorUtil.getColumnIndexOrThrow(_cursor, "dosage");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final List<Prescription> _result = new ArrayList<Prescription>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Prescription _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpAppointmentId;
            _tmpAppointmentId = _cursor.getInt(_cursorIndexOfAppointmentId);
            final String _tmpMedicine;
            _tmpMedicine = _cursor.getString(_cursorIndexOfMedicine);
            final String _tmpDosage;
            _tmpDosage = _cursor.getString(_cursorIndexOfDosage);
            final String _tmpInstructions;
            _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
            _item = new Prescription(_tmpId,_tmpAppointmentId,_tmpMedicine,_tmpDosage,_tmpInstructions);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<MedicalReport>> getAllMedicalReports() {
    final String _sql = "SELECT * FROM medical_reports";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medical_reports"}, new Callable<List<MedicalReport>>() {
      @Override
      @NonNull
      public List<MedicalReport> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPatientId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientId");
          final int _cursorIndexOfDoctorId = CursorUtil.getColumnIndexOrThrow(_cursor, "doctorId");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final List<MedicalReport> _result = new ArrayList<MedicalReport>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MedicalReport _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPatientId;
            _tmpPatientId = _cursor.getInt(_cursorIndexOfPatientId);
            final int _tmpDoctorId;
            _tmpDoctorId = _cursor.getInt(_cursorIndexOfDoctorId);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item = new MedicalReport(_tmpId,_tmpPatientId,_tmpDoctorId,_tmpContent,_tmpDate);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Attendance>> getAllAttendance() {
    final String _sql = "SELECT * FROM attendance ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"attendance"}, new Callable<List<Attendance>>() {
      @Override
      @NonNull
      public List<Attendance> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDoctorId = CursorUtil.getColumnIndexOrThrow(_cursor, "doctorId");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final List<Attendance> _result = new ArrayList<Attendance>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Attendance _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpDoctorId;
            _tmpDoctorId = _cursor.getInt(_cursorIndexOfDoctorId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _item = new Attendance(_tmpId,_tmpDoctorId,_tmpTimestamp,_tmpType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
