package com.myclinic.clinic;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class ClinicRepository {

    private final JdbcTemplate db;
    private final ClinicMapper clinicMapper;

    ClinicRepository(JdbcTemplate db, ClinicMapper clinicMapper) {
        this.db = db;
        this.clinicMapper = clinicMapper;
    }

    List<Clinic> getAllClinics() {
        String query = """
                SELECT
                    clinic_id as id,
                    name,
                    phone,
                    location
                FROM clinics
                """;

        return db.query(query, clinicMapper);
    }

    List<Clinic> getClinicsByLocation(String location) {
        String query = """
                SELECT
                    clinic_id as id,
                    name,
                    phone,
                    location
                FROM clinics
                WHERE location = ?
                """;
        return db.query(query, clinicMapper, location);
    }

    List<Clinic> getClinicsBySpeciality(int speciality) {
        String query = """
                SELECT
                    clinic_id as id,
                    clinics.name,
                    phone,
                    location
                FROM clinics
                    INNER JOIN clinic_specialities using(clinic_id)
                    INNER JOIN specialities USING(speciality_id)
                WHERE
                    speciality_id = ?
                """;
        return db.query(query, clinicMapper, speciality);
    }

    List<Clinic> getClinicsByLocationAndSpeciality(String location, int speciality) {
        String query = """
                SELECT
                    clinic_id as id,
                    clinics.name,
                    phone,
                    location
                FROM clinics
                    INNER JOIN clinic_specialities using(clinic_id)
                    INNER JOIN specialities USING(speciality_id)
                WHERE
                    location = ?
                    AND speciality_id = ?
                """;
        return db.query(query, clinicMapper, location, speciality);
    }


}
