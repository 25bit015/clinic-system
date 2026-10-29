CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL REFERENCES users(id),
    role_id BIGINT NOT NULL REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE patients (
    id BIGSERIAL PRIMARY KEY,
    file_number VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(20) NOT NULL,
    phone VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    emergency_contact VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE appointments (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL REFERENCES patients(id),
    doctor_id BIGINT REFERENCES users(id),
    appointment_time TIMESTAMP NOT NULL,
    department VARCHAR(150) NOT NULL,
    reason VARCHAR(500) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE triage_vitals (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL REFERENCES patients(id),
    appointment_id BIGINT NOT NULL UNIQUE REFERENCES appointments(id),
    blood_pressure VARCHAR(50) NOT NULL,
    temperature NUMERIC(5,2) NOT NULL,
    weight NUMERIC(6,2) NOT NULL,
    height NUMERIC(6,2) NOT NULL,
    pulse INT NOT NULL,
    spo2 INT NOT NULL,
    chief_complaint TEXT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE consultations (
    id BIGSERIAL PRIMARY KEY,
    appointment_id BIGINT NOT NULL UNIQUE REFERENCES appointments(id),
    patient_id BIGINT NOT NULL REFERENCES patients(id),
    doctor_id BIGINT NOT NULL REFERENCES users(id),
    clinical_notes TEXT NOT NULL,
    consultation_date TIMESTAMP NOT NULL,
    follow_up_date TIMESTAMP,
    referral_specialist VARCHAR(255),
    admitted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE diagnosis (
    id BIGSERIAL PRIMARY KEY,
    consultation_id BIGINT NOT NULL REFERENCES consultations(id),
    icd10_code VARCHAR(30) NOT NULL,
    description TEXT NOT NULL,
    primary_diagnosis BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE lab_test_catalog (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    price NUMERIC(12,2) NOT NULL,
    description VARCHAR(500),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE lab_orders (
    id BIGSERIAL PRIMARY KEY,
    consultation_id BIGINT NOT NULL REFERENCES consultations(id),
    patient_id BIGINT NOT NULL REFERENCES patients(id),
    lab_test_id BIGINT NOT NULL REFERENCES lab_test_catalog(id),
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE lab_results (
    id BIGSERIAL PRIMARY KEY,
    lab_order_id BIGINT NOT NULL UNIQUE REFERENCES lab_orders(id),
    result_notes TEXT NOT NULL,
    report_url VARCHAR(500),
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE drugs_inventory (
    id BIGSERIAL PRIMARY KEY,
    drug_name VARCHAR(255) NOT NULL UNIQUE,
    batch_number VARCHAR(100) NOT NULL,
    stock_quantity INT NOT NULL,
    reorder_level INT NOT NULL,
    unit_price NUMERIC(12,2) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE prescriptions (
    id BIGSERIAL PRIMARY KEY,
    consultation_id BIGINT NOT NULL REFERENCES consultations(id),
    patient_id BIGINT NOT NULL REFERENCES patients(id),
    drug_id BIGINT NOT NULL REFERENCES drugs_inventory(id),
    dosage VARCHAR(150) NOT NULL,
    frequency VARCHAR(150) NOT NULL,
    duration VARCHAR(150) NOT NULL,
    quantity INT NOT NULL,
    instructions TEXT NOT NULL,
    dispensed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE invoices (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL REFERENCES patients(id),
    appointment_id BIGINT NOT NULL UNIQUE REFERENCES appointments(id),
    payment_status VARCHAR(50) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    total_amount NUMERIC(12,2) NOT NULL,
    paid_amount NUMERIC(12,2) NOT NULL,
    insurance_provider VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE invoice_items (
    id BIGSERIAL PRIMARY KEY,
    invoice_id BIGINT NOT NULL REFERENCES invoices(id),
    item_type VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    amount NUMERIC(12,2) NOT NULL,
    reference_id BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
