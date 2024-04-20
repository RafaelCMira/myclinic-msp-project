package com.myclinic.utils;

import com.github.javafaker.Faker;
import com.myclinic.clinic.Clinic;
import com.myclinic.doctor.Doctor;
import com.myclinic.patient.Patient;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

//@Component // Uncomment this line to enable the data loader
public class SampleDataLoader implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final Faker faker;

    public SampleDataLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) throws Exception {
        var doctors = insertDoctors(5);
        // var drugs = insertDrugs(30);
        // var specialities = insertSpecialities();
        var patients = insertPatients(5);
        var equipments = insertEquipments();
        var clinics = insertClinics(2);
    }

    public List<Doctor> insertDoctors(int amount) {
        List<Doctor> doctors = new ArrayList<>(amount);
        for (int i = 1; i < amount + 1; i++) {
            String name = faker.name().fullName();
            LocalDate birthDate = faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String email = faker.internet().emailAddress();
            String password = faker.internet().password();
            String phone = faker.phoneNumber().cellPhone();
            doctors.add(new Doctor(i, name, birthDate, email, password, phone));
        }

        for (Doctor doctor : doctors) {
            String procedureCall = "{call insert_doctor(?, ?, ?, ?, ?, ?)}";

            jdbcTemplate.execute(procedureCall, (CallableStatementCallback<Integer>) cs -> {
                cs.setString(1, doctor.getName());
                cs.setDate(2, java.sql.Date.valueOf(doctor.getBirthDate()));
                cs.setString(3, doctor.getEmail());
                cs.setString(4, doctor.getPassword());
                cs.setString(5, doctor.getPhone());
                cs.registerOutParameter(6, Types.INTEGER);
                cs.execute();
                return 0;
            });
        }

        return doctors;
    }

    public List<Pair<Integer, String>> insertDrugs(int amount) {
        String sql = """
                INSERT INTO drugs (name)
                VALUES (?)
                """;

        List<Pair<Integer, String>> drugs = new ArrayList<>(amount);
        for (int i = 1; i < amount + 1; i++) {
            String name = faker.medical().medicineName().length() > 100 ? faker.medical().medicineName().substring(0, 100) :
                    faker.medical().medicineName();
            drugs.add(Pair.of(i, name));
        }

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                String drug = drugs.get(i).getRight();
                ps.setString(1, drug);
            }

            @Override
            public int getBatchSize() {
                return drugs.size();
            }
        });

        return drugs;
    }

    public List<Pair<Integer, String>> insertSpecialities() {
        String sql = """
                INSERT INTO specialities (name)
                VALUES (?)
                """;

        List<Pair<Integer, String>> specialities = new ArrayList<>();

        specialities.add(Pair.of(1, "Cardiologist"));
        specialities.add(Pair.of(2, "Dermatologist"));
        specialities.add(Pair.of(3, "Endocrinologist"));
        specialities.add(Pair.of(4, "Gastroenterologist"));
        specialities.add(Pair.of(5, "Neurologist"));
        specialities.add(Pair.of(6, "Oncologist"));
        specialities.add(Pair.of(7, "Pediatrician"));
        specialities.add(Pair.of(8, "Psychiatrist"));

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                String speciality = specialities.get(i).getRight();
                ps.setString(1, speciality);
            }

            @Override
            public int getBatchSize() {
                return specialities.size();
            }
        });

        return specialities;
    }

    public List<Patient> insertPatients(int amount) {
        List<Patient> patients = new ArrayList<>(amount);
        for (int i = 1; i < amount + 1; i++) {
            String name = faker.name().fullName();
            String email = faker.internet().emailAddress();
            String password = faker.internet().password();
            String phone = faker.phoneNumber().cellPhone();
            LocalDate birthDate = faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            patients.add(new Patient(i, name, birthDate, email, password, phone));
        }

        for (Patient patient : patients) {
            String procedureCall = "{call insert_patient(?, ?, ?, ?, ?, ?)}";

            jdbcTemplate.execute(procedureCall, (CallableStatementCallback<Integer>) cs -> {
                cs.setString(1, patient.getName());
                cs.setDate(2, java.sql.Date.valueOf(patient.getBirthDate()));
                cs.setString(3, patient.getEmail());
                cs.setString(4, patient.getPassword());
                cs.setString(5, patient.getPhone());
                cs.registerOutParameter(6, Types.INTEGER);
                cs.execute();
                return 0;
            });
        }

        return patients;
    }

    public List<Pair<Integer, String>> insertEquipments() {
        String sql = """
                INSERT INTO equipments (name)
                VALUES (?)
                """;

        List<Pair<Integer, String>> equipments = new ArrayList<>();

        equipments.add(Pair.of(1, "Electrocardiogram"));
        equipments.add(Pair.of(2, "X-ray machine"));
        equipments.add(Pair.of(3, "Ultrasound machine"));
        equipments.add(Pair.of(4, "MRI machine"));
        equipments.add(Pair.of(5, "CT scanner"));
        equipments.add(Pair.of(6, "Blood pressure monitor"));
        equipments.add(Pair.of(7, "Stethoscope"));
        equipments.add(Pair.of(8, "Thermometer"));

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                String equipment = equipments.get(i).getRight();
                ps.setString(1, equipment);
            }

            @Override
            public int getBatchSize() {
                return equipments.size();
            }
        });

        return equipments;
    }

    public List<Clinic> insertClinics(int amount) {
        String sql = """
                INSERT INTO clinics (name, phone, location)
                VALUES (?, ?, ?)
                """;

        List<Clinic> clinics = new ArrayList<>(amount);
        for (int i = 1; i < amount + 1; i++) {
            String name = faker.name().fullName();
            String phone = faker.phoneNumber().cellPhone();
            String location = faker.address().city();
            clinics.add(new Clinic(i, name, phone, location));
        }

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Clinic clinic = clinics.get(i);
                ps.setString(1, clinic.getName());
                ps.setString(2, clinic.getPhone());
                ps.setString(3, clinic.getLocation());
            }

            @Override
            public int getBatchSize() {
                return clinics.size();
            }
        });

        return clinics;
    }

}
