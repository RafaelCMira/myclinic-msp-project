-- Drops
DROP TABLE IF EXISTS prescription_drugs;
DROP TABLE IF EXISTS doctor_specialities;
DROP TABLE IF EXISTS clinic_doctors;
DROP TABLE IF EXISTS clinic_specialities;
DROP TABLE IF EXISTS drugs;
DROP TABLE IF EXISTS specialities;
DROP TABLE IF EXISTS prescriptions;
DROP TABLE IF EXISTS exams;
DROP TABLE IF EXISTS equipments;
DROP TABLE IF EXISTS online_appointments;
DROP TABLE IF EXISTS presential_appointments;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS doctors;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS clinics;

DROP PROCEDURE insert_patient;
DROP PROCEDURE delete_patient;
DROP PROCEDURE insert_doctor;
DROP PROCEDURE delete_doctor;


-- Tables
CREATE TABLE drugs
(
    drug_id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(100) NOT NULL
);

CREATE TABLE users
(
    user_id     SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(40)        NOT NULL,
    birth_date  DATE               NOT NULL,
    email       VARCHAR(50) UNIQUE NOT NULL,
    password    VARCHAR(50)        NOT NULL,
    phone       VARCHAR(20)        NOT NULL,
    register_ts TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE doctors
(
    doctor_id SMALLINT UNSIGNED PRIMARY KEY
);

CREATE TABLE specialities
(
    speciality_id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(40) NOT NULL
);

CREATE TABLE patients
(
    patient_id SMALLINT UNSIGNED PRIMARY KEY
);

CREATE TABLE equipments
(
    equipment_id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(40) NOT NULL
);

CREATE TABLE clinics
(
    clinic_id   SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(40) NOT NULL,
    phone       VARCHAR(20) NOT NULL,
    location    VARCHAR(30) NOT NULL,
    register_ts TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE prescriptions
(
    patient_id SMALLINT UNSIGNED NOT NULL,
    doctor_id  SMALLINT UNSIGNED NOT NULL,
    date       DATE              NOT NULL,
    hour       TIME              NOT NULL,
    PRIMARY KEY (patient_id, doctor_id, date, hour)
);

CREATE TABLE exams
(
    patient_id   SMALLINT UNSIGNED NOT NULL,
    date         DATE              NOT NULL,
    hour         TIME              NOT NULL,
    description  TEXT              NOT NULL,
    result       VARCHAR(50)       NOT NULL DEFAULT 'unrealized',
    clinic_id    SMALLINT UNSIGNED NOT NULL,
    equipment_id SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY (patient_id, date, hour)
);

CREATE TABLE online_appointments
(
    patient_id SMALLINT UNSIGNED NOT NULL,
    doctor_id  SMALLINT UNSIGNED NOT NULL,
    date       DATE              NOT NULL,
    hour       TIME              NOT NULL,
    duration   TIME              NOT NULL,
    rating     TINYINT           NOT NULL DEFAULT -1,
    review     TEXT,
    UNIQUE (patient_id, date, hour),
    UNIQUE (doctor_id, date, hour),
    PRIMARY KEY (patient_id, doctor_id, date, hour)
);

CREATE TABLE presential_appointments
(
    patient_id SMALLINT UNSIGNED NOT NULL,
    doctor_id  SMALLINT UNSIGNED NOT NULL,
    date       DATE              NOT NULL,
    hour       TIME              NOT NULL,
    duration   TIME              NOT NULL,
    rating     TINYINT           NOT NULL DEFAULT -1,
    review     TEXT,
    clinic_id  SMALLINT UNSIGNED NOT NULL,
    UNIQUE (patient_id, date, hour),
    UNIQUE (doctor_id, date, hour),
    PRIMARY KEY (patient_id, doctor_id, date, hour)
);

CREATE TABLE prescription_drugs
(
    patient_id SMALLINT UNSIGNED NOT NULL,
    doctor_id  SMALLINT UNSIGNED NOT NULL,
    drug_id    SMALLINT UNSIGNED NOT NULL,
    date       DATE              NOT NULL,
    hour       TIME              NOT NULL,
    PRIMARY KEY (patient_id, doctor_id, drug_id, date, hour)
);

CREATE TABLE doctor_specialities
(
    doctor_id     SMALLINT UNSIGNED NOT NULL,
    speciality_id SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY (doctor_id, speciality_id)
);

CREATE TABLE clinic_doctors
(
    doctor_id SMALLINT UNSIGNED NOT NULL,
    clinic_id SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY (doctor_id, clinic_id)
);

CREATE TABLE clinic_specialities
(
    clinic_id     SMALLINT UNSIGNED NOT NULL,
    speciality_id SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY (clinic_id, speciality_id)
);

-- Constraints - Foreign Keys

ALTER TABLE doctors
    ADD CONSTRAINT fk_doctor_users FOREIGN KEY (doctor_id) REFERENCES users (user_id) ON DELETE CASCADE;

ALTER TABLE patients
    ADD CONSTRAINT fk_patient_users FOREIGN KEY (patient_id) REFERENCES users (user_id) ON DELETE CASCADE;

ALTER TABLE prescriptions
    ADD CONSTRAINT fk_prescription_patients FOREIGN KEY (patient_id) REFERENCES patients (patient_id) ON DELETE CASCADE;
ALTER TABLE prescriptions
    ADD CONSTRAINT fk_prescription_doctors FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id) ON DELETE CASCADE;

ALTER TABLE exams
    ADD CONSTRAINT fk_exam_patients FOREIGN KEY (patient_id) REFERENCES patients (patient_id) ON DELETE CASCADE;
ALTER TABLE exams
    ADD CONSTRAINT fk_exam_clinics FOREIGN KEY (clinic_id) REFERENCES clinics (clinic_id) ON DELETE CASCADE;
ALTER TABLE exams
    ADD CONSTRAINT fk_exam_equipments FOREIGN KEY (equipment_id) REFERENCES equipments (equipment_id) ON DELETE CASCADE;

ALTER TABLE online_appointments
    ADD CONSTRAINT fk_online_appointment_patients FOREIGN KEY (patient_id) REFERENCES patients (patient_id) ON DELETE CASCADE;
ALTER TABLE online_appointments
    ADD CONSTRAINT fk_online_appointment_doctors FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id) ON DELETE CASCADE;

ALTER TABLE presential_appointments
    ADD CONSTRAINT fk_presential_appointment_patients FOREIGN KEY (patient_id) REFERENCES patients (patient_id) ON DELETE CASCADE;
ALTER TABLE presential_appointments
    ADD CONSTRAINT fk_presential_appointment_doctors FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id) ON DELETE CASCADE;
ALTER TABLE presential_appointments
    ADD CONSTRAINT fk_presential_appointment_clinics FOREIGN KEY (clinic_id) REFERENCES clinics (clinic_id) ON DELETE CASCADE;

ALTER TABLE prescription_drugs
    ADD CONSTRAINT fk_prescription_drugs_prescription FOREIGN KEY (patient_id, doctor_id, date, hour)
        REFERENCES prescriptions (patient_id, doctor_id, date, hour) ON DELETE CASCADE;
ALTER TABLE prescription_drugs
    ADD CONSTRAINT fk_prescription_drugs_drugs FOREIGN KEY (drug_id) REFERENCES drugs (drug_id) ON DELETE CASCADE;

ALTER TABLE doctor_specialities
    ADD CONSTRAINT fk_doctor_specialities_doctors FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id) ON DELETE CASCADE;
ALTER TABLE doctor_specialities
    ADD CONSTRAINT fk_doctor_specialities_specialities FOREIGN KEY (speciality_id) REFERENCES specialities (speciality_id) ON DELETE CASCADE;

ALTER TABLE clinic_doctors
    ADD CONSTRAINT fk_clinic_doctors_doctors FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id) ON DELETE CASCADE;
ALTER TABLE clinic_doctors
    ADD CONSTRAINT fk_clinic_doctors_clinics FOREIGN KEY (clinic_id) REFERENCES clinics (clinic_id) ON DELETE CASCADE;

ALTER TABLE clinic_specialities
    ADD CONSTRAINT fk_clinic_specialities_clinics FOREIGN KEY (clinic_id) REFERENCES clinics (clinic_id) ON DELETE CASCADE;
ALTER TABLE clinic_specialities
    ADD CONSTRAINT fk_clinic_specialities_specialities FOREIGN KEY (speciality_id) REFERENCES specialities (speciality_id) ON DELETE CASCADE;

-- Procedures

DELIMITER //
CREATE PROCEDURE insert_patient(
    IN name VARCHAR(40),
    IN birth_date DATE,
    IN email VARCHAR(50),
    IN password VARCHAR(50),
    IN phone VARCHAR(20),
    OUT id SMALLINT UNSIGNED
)
BEGIN
    INSERT INTO users (name, birth_date, email, password, phone)
    VALUES (name, birth_date, email, password, phone);

    SET id = LAST_INSERT_ID();

    INSERT INTO patients (patient_id)
    VALUES (id);
END;
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE delete_patient(
    IN id SMALLINT UNSIGNED
)
BEGIN
    DELETE FROM patients WHERE patient_id = id;

    IF ROW_COUNT() > 0 THEN
        DELETE FROM users WHERE user_id = id;
    END IF;
END;
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE insert_doctor(
    IN name VARCHAR(40),
    IN birth_date DATE,
    IN email VARCHAR(50),
    IN password VARCHAR(50),
    IN phone VARCHAR(20),
    OUT id SMALLINT UNSIGNED
)
BEGIN
    INSERT INTO users (name, birth_date, email, password, phone)
    VALUES (name, birth_date, email, password, phone);

    SET id = LAST_INSERT_ID();

    INSERT INTO doctors (doctor_id)
    VALUES (id);
END;
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE delete_doctor(
    IN id SMALLINT UNSIGNED
)
BEGIN
    DELETE FROM doctors WHERE doctor_id = id;

    IF ROW_COUNT() > 0 THEN
        DELETE FROM users WHERE user_id = id;
    END IF;
END;
//
DELIMITER ;
