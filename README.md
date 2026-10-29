# Clinic Management System

Full-stack clinic workflow system with React frontend and Spring Boot backend.

## Tech Stack

- **Frontend:** React, React Router, Tailwind CSS, Axios, Zustand, Recharts
- **Backend:** Spring Boot 3, Spring Security (JWT), Spring Data JPA, Hibernate
- **Database:** PostgreSQL or MySQL (configurable)
- **Build:** Maven (backend), npm/Vite (frontend)

## Project Structure

- `/backend` - Spring Boot API with RBAC and clinic workflow modules
- `/frontend` - React UI with role-based pages
- `/database/schema.sql` - SQL schema for required entities

## Backend Features

- JWT authentication (`/api/auth/login`, `/api/auth/register`)
- Role-based access modules:
  - Reception
  - Triage
  - Doctor
  - Lab
  - Pharmacy
  - Billing
  - Admin
- Required entities implemented:
  - Users, Roles, Patients, Appointments, Triage_Vitals, Consultations, Diagnosis,
    Lab_Test_Catalog, Lab_Orders, Lab_Results, Prescriptions, Drugs_Inventory,
    Invoices, Invoice_Items
- Patient visit timeline endpoint (`GET /api/reception/patients/{id}/timeline`)

## Frontend Routes

- `/login`
- `/dashboard`
- `/patients`
- `/triage`
- `/doctor`
- `/lab`
- `/pharmacy`
- `/billing`
- `/admin`

## Run Backend

```bash
cd /home/runner/work/clinic-system/clinic-system/backend
mvn spring-boot:run
```

Default local settings are in:

- `backend/src/main/resources/application.yml`

Set environment variables as needed:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION_MS`

A default admin user is seeded:

- username: `admin`
- password: `Admin@123`

## Run Frontend

```bash
cd /home/runner/work/clinic-system/clinic-system/frontend
npm install
npm run dev
```

Optional env:

- `VITE_API_URL` (default: `http://localhost:8080/api`)

## Build

Backend:

```bash
cd /home/runner/work/clinic-system/clinic-system/backend
mvn clean verify
```

Frontend:

```bash
cd /home/runner/work/clinic-system/clinic-system/frontend
npm run build
```
