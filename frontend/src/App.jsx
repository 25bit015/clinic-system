import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Layout from "./components/Layout";
import ProtectedRoute from "./components/ProtectedRoute";
import AdminPage from "./pages/AdminPage";
import BillingPage from "./pages/BillingPage";
import DashboardPage from "./pages/DashboardPage";
import DoctorPage from "./pages/DoctorPage";
import LabPage from "./pages/LabPage";
import LoginPage from "./pages/LoginPage";
import PatientsPage from "./pages/PatientsPage";
import PharmacyPage from "./pages/PharmacyPage";
import TriagePage from "./pages/TriagePage";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />

        <Route element={<ProtectedRoute />}>
          <Route element={<Layout />}>
            <Route path="/dashboard" element={<DashboardPage />} />
            <Route element={<ProtectedRoute roles={["ADMIN", "RECEPTIONIST"]} />}>
              <Route path="/patients" element={<PatientsPage />} />
            </Route>
            <Route element={<ProtectedRoute roles={["ADMIN", "NURSE"]} />}>
              <Route path="/triage" element={<TriagePage />} />
            </Route>
            <Route element={<ProtectedRoute roles={["ADMIN", "DOCTOR"]} />}>
              <Route path="/doctor" element={<DoctorPage />} />
            </Route>
            <Route element={<ProtectedRoute roles={["ADMIN", "LAB_TECH", "DOCTOR"]} />}>
              <Route path="/lab" element={<LabPage />} />
            </Route>
            <Route element={<ProtectedRoute roles={["ADMIN", "PHARMACIST"]} />}>
              <Route path="/pharmacy" element={<PharmacyPage />} />
            </Route>
            <Route element={<ProtectedRoute roles={["ADMIN", "CASHIER"]} />}>
              <Route path="/billing" element={<BillingPage />} />
            </Route>
            <Route element={<ProtectedRoute roles={["ADMIN"]} />}>
              <Route path="/admin" element={<AdminPage />} />
            </Route>
            <Route path="*" element={<Navigate to="/dashboard" replace />} />
          </Route>
        </Route>

        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </BrowserRouter>
  );
}
