package com.myclinic.speciality;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class SpecialityRepository {

    private final SpecialityMapper specialityMapper;
    private final JdbcTemplate db;

    SpecialityRepository(SpecialityMapper specialityMapper, JdbcTemplate db) {
        this.specialityMapper = specialityMapper;
        this.db = db;
    }

    List<Speciality> getAll() {
        String query = """
                SELECT
                    speciality_id as id,
                    name
                FROM
                    specialities
                """;

        return db.query(query, specialityMapper);
    }
}
