import { useState } from "react";
import api from "../api/client";

export default function PatientsPage() {
  const [patientForm, setPatientForm] = useState({ fullName: "", age: 0, gender: "MALE", phone: "", address: "", emergencyContact: "" });
  const [search, setSearch] = useState("");
  const [patients, setPatients] = useState([]);
  const [timeline, setTimeline] = useState(null);

  const registerPatient = async (event) => {
    event.preventDefault();
    await api.post("/reception/patients", patientForm);
    setPatientForm({ fullName: "", age: 0, gender: "MALE", phone: "", address: "", emergencyContact: "" });
  };

  const searchPatients = async () => {
    const { data } = await api.get("/reception/patients/search", { params: { q: search } });
    setPatients(data);
  };

  const loadTimeline = async (patientId) => {
    const { data } = await api.get(`/reception/patients/${patientId}/timeline`);
    setTimeline(data);
  };

  return (
    <div className="space-y-6">
      <h2 className="text-2xl font-semibold">Reception / Patient Registration</h2>
      <form onSubmit={registerPatient} className="bg-white p-4 rounded shadow grid md:grid-cols-2 gap-3">
        <input className="border rounded px-3 py-2" placeholder="Full Name" value={patientForm.fullName} onChange={(e) => setPatientForm({ ...patientForm, fullName: e.target.value })} required />
        <input className="border rounded px-3 py-2" type="number" placeholder="Age" value={patientForm.age} onChange={(e) => setPatientForm({ ...patientForm, age: Number(e.target.value) })} required />
        <select className="border rounded px-3 py-2" value={patientForm.gender} onChange={(e) => setPatientForm({ ...patientForm, gender: e.target.value })}>
          <option value="MALE">Male</option>
          <option value="FEMALE">Female</option>
          <option value="OTHER">Other</option>
        </select>
        <input className="border rounded px-3 py-2" placeholder="Phone" value={patientForm.phone} onChange={(e) => setPatientForm({ ...patientForm, phone: e.target.value })} required />
        <input className="border rounded px-3 py-2" placeholder="Address" value={patientForm.address} onChange={(e) => setPatientForm({ ...patientForm, address: e.target.value })} required />
        <input className="border rounded px-3 py-2" placeholder="Emergency Contact" value={patientForm.emergencyContact} onChange={(e) => setPatientForm({ ...patientForm, emergencyContact: e.target.value })} required />
        <button className="md:col-span-2 bg-blue-600 text-white px-4 py-2 rounded">Register Patient</button>
      </form>

      <div className="bg-white p-4 rounded shadow">
        <div className="flex gap-2 mb-3">
          <input className="flex-1 border rounded px-3 py-2" placeholder="Search by name or file number" value={search} onChange={(e) => setSearch(e.target.value)} />
          <button onClick={searchPatients} className="bg-slate-800 text-white px-4 py-2 rounded">Search</button>
        </div>
        <ul className="space-y-2">
          {patients.map((patient) => (
            <li key={patient.id} className="border rounded p-3 flex justify-between items-center">
              <div>
                <p className="font-medium">{patient.fullName} ({patient.fileNumber})</p>
                <p className="text-sm text-slate-500">{patient.phone}</p>
              </div>
              <button onClick={() => loadTimeline(patient.id)} className="bg-emerald-600 text-white px-3 py-1 rounded">Timeline</button>
            </li>
          ))}
        </ul>
      </div>

      {timeline && (
        <div className="bg-white p-4 rounded shadow">
          <h3 className="font-semibold mb-2">Patient Visit Timeline</h3>
          <p className="text-sm">Appointments: {timeline.appointments.length} | Triage: {timeline.triageVitals.length} | Consultations: {timeline.consultations.length} | Invoices: {timeline.invoices.length}</p>
        </div>
      )}
    </div>
  );
}
