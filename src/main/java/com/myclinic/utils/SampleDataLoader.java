package com.myclinic.utils;

import com.github.javafaker.Faker;
import com.myclinic.doctor.Doctor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        bulkInsert(5);
    }

    public void bulkInsert(int numberOfDoctors) {
        String sql = """
                INSERT INTO doctor (name, birth_date, email, password, phone)
                VALUES (?, ?, ?, ?, ?)
                """;

        List<Doctor> doctors = new ArrayList<>(numberOfDoctors);
        for (int i = 0; i < numberOfDoctors; i++) {
            String name = faker.name().fullName();
            String email = faker.internet().emailAddress();
            String password = faker.internet().password();
            String phone = faker.phoneNumber().cellPhone();
            LocalDate birthDate = faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            doctors.add(new Doctor(i, name, birthDate, email, password, phone));
        }

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Doctor doctor = doctors.get(i);
                ps.setString(1, doctor.getName());
                ps.setString(2, doctor.getEmail());
                ps.setString(3, doctor.getPhone());
                ps.setDate(4, java.sql.Date.valueOf(doctor.getBirthDate()));
            }

            @Override
            public int getBatchSize() {
                return doctors.size();
            }
        });
    }


}
