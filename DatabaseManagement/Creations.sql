-- Drops
DROP TABLE IF EXISTS prescription_drugs;
DROP TABLE IF EXISTS doctor_specialities;
DROP TABLE IF EXISTS clinic_doctors;
DROP TABLE IF EXISTS clinic_specialities;
DROP TABLE IF EXISTS drug;
DROP TABLE IF EXISTS speciality;
DROP TABLE IF EXISTS prescription;
DROP TABLE IF EXISTS exam;
DROP TABLE IF EXISTS equipment;
DROP TABLE IF EXISTS presential;
DROP TABLE IF EXISTS appointment;
DROP TABLE IF EXISTS patient;
DROP TABLE IF EXISTS doctor;
DROP TABLE IF EXISTS clinic;


-- Tables
CREATE TABLE drug
(
    drug_id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(100) NOT NULL
);

CREATE TABLE doctor
(
    doctor_id   SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(40) NOT NULL,
    birth_date  DATE        NOT NULL,
    email       VARCHAR(50) NOT NULL,
    password    VARCHAR(50) NOT NULL,
    phone       VARCHAR(20) NOT NULL,
    register_ts TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE speciality
(
    speciality_id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(40) NOT NULL
);

CREATE TABLE patient
(
    patient_id  SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(40) NOT NULL,
    birth_date  DATE        NOT NULL,
    email       VARCHAR(50) NOT NULL,
    password    VARCHAR(50) NOT NULL,
    phone       VARCHAR(20) NOT NULL,
    register_ts TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE equipment
(
    equipment_id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(40) NOT NULL
);

CREATE TABLE clinic
(
    clinic_id   SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(40) NOT NULL,
    phone       VARCHAR(20) NOT NULL,
    location    VARCHAR(30) NOT NULL,
    register_ts TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE prescription
(
    patient_id SMALLINT UNSIGNED NOT NULL,
    doctor_id  SMALLINT UNSIGNED NOT NULL,
    date       DATE              NOT NULL,
    hour       TIME              NOT NULL,
    PRIMARY KEY (patient_id, doctor_id, date, hour)
);

CREATE TABLE exam
(
    patient_id   SMALLINT UNSIGNED NOT NULL,
    date         DATE              NOT NULL,
    hour         TIME              NOT NULL,
    motive       TEXT              NOT NULL,
    clinic_id    SMALLINT UNSIGNED NOT NULL,
    equipment_id SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY (patient_id, date, hour)
);

CREATE TABLE appointment
(
    patient_id SMALLINT UNSIGNED NOT NULL,
    doctor_id  SMALLINT UNSIGNED NOT NULL,
    date       DATE              NOT NULL,
    hour       TIME              NOT NULL,
    PRIMARY KEY (patient_id, doctor_id, date, hour)
);

CREATE TABLE presential
(
    patient_id SMALLINT UNSIGNED NOT NULL,
    doctor_id  SMALLINT UNSIGNED NOT NULL,
    clinic_id  SMALLINT UNSIGNED NOT NULL,
    date       DATE              NOT NULL,
    hour       TIME              NOT NULL,
    PRIMARY KEY (patient_id, clinic_id, doctor_id, date, hour)
);

CREATE TABLE prescription_drugs
(
    doctor_id  SMALLINT UNSIGNED NOT NULL,
    patient_id SMALLINT UNSIGNED NOT NULL,
    drug_id    SMALLINT UNSIGNED NOT NULL,
    date       DATE              NOT NULL,
    hour       TIME              NOT NULL,
    PRIMARY KEY (doctor_id, patient_id, drug_id, date, hour)
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

ALTER TABLE prescription
    ADD CONSTRAINT fk_prescription_patient FOREIGN KEY (patient_id) REFERENCES patient (patient_id) ON DELETE CASCADE;
ALTER TABLE prescription
    ADD CONSTRAINT fk_prescription_doctor FOREIGN KEY (doctor_id) REFERENCES doctor (doctor_id) ON DELETE CASCADE;

ALTER TABLE exam
    ADD CONSTRAINT fk_exam_patient FOREIGN KEY (patient_id) REFERENCES patient (patient_id) ON DELETE CASCADE;
ALTER TABLE exam
    ADD CONSTRAINT fk_exam_clinic FOREIGN KEY (clinic_id) REFERENCES clinic (clinic_id) ON DELETE CASCADE;
ALTER TABLE exam
    ADD CONSTRAINT fk_exam_equipment FOREIGN KEY (equipment_id) REFERENCES equipment (equipment_id) ON DELETE CASCADE;

ALTER TABLE appointment
    ADD CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES patient (patient_id) ON DELETE CASCADE;
ALTER TABLE appointment
    ADD CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctor_id) REFERENCES doctor (doctor_id) ON DELETE CASCADE;

ALTER TABLE presential
    ADD CONSTRAINT fk_presential_appointment FOREIGN KEY (patient_id, doctor_id, date, hour)
        REFERENCES appointment (patient_id, doctor_id, date, hour) ON DELETE CASCADE;
ALTER TABLE presential
    ADD CONSTRAINT fk_presential_clinic FOREIGN KEY (clinic_id) REFERENCES clinic (clinic_id) ON DELETE CASCADE;


ALTER TABLE prescription_drugs
    ADD CONSTRAINT fk_prescription_drugs_prescription FOREIGN KEY (patient_id, doctor_id, date, hour)
        REFERENCES prescription (patient_id, doctor_id, date, hour) ON DELETE CASCADE;
ALTER TABLE prescription_drugs
    ADD CONSTRAINT fk_prescription_drugs_drug FOREIGN KEY (drug_id) REFERENCES drug (drug_id) ON DELETE CASCADE;

ALTER TABLE doctor_specialities
    ADD CONSTRAINT fk_doctor_specialities_doctor FOREIGN KEY (doctor_id) REFERENCES doctor (doctor_id) ON DELETE CASCADE;
ALTER TABLE doctor_specialities
    ADD CONSTRAINT fk_doctor_specialities_speciality FOREIGN KEY (speciality_id) REFERENCES speciality (speciality_id) ON DELETE CASCADE;

ALTER TABLE clinic_doctors
    ADD CONSTRAINT fk_clinic_doctors_doctor FOREIGN KEY (doctor_id) REFERENCES doctor (doctor_id) ON DELETE CASCADE;
ALTER TABLE clinic_doctors
    ADD CONSTRAINT fk_clinic_doctors_clinic FOREIGN KEY (clinic_id) REFERENCES clinic (clinic_id) ON DELETE CASCADE;

ALTER TABLE clinic_specialities
    ADD CONSTRAINT fk_clinic_specialities_clinic FOREIGN KEY (clinic_id) REFERENCES clinic (clinic_id) ON DELETE CASCADE;
ALTER TABLE clinic_specialities
    ADD CONSTRAINT fk_clinic_specialities_speciality FOREIGN KEY (speciality_id) REFERENCES speciality (speciality_id) ON DELETE CASCADE;

