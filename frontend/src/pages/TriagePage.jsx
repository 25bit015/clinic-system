import { useState } from "react";
import api from "../api/client";

export default function TriagePage() {
  const [form, setForm] = useState({ appointmentId: "", patientId: "", bloodPressure: "", temperature: 36.5, weight: 70, height: 170, pulse: 80, spo2: 98, chiefComplaint: "" });

  const submit = async (event) => {
    event.preventDefault();
    await api.post("/triage/vitals", {
      ...form,
      appointmentId: Number(form.appointmentId),
      patientId: Number(form.patientId),
      pulse: Number(form.pulse),
      spo2: Number(form.spo2),
      temperature: Number(form.temperature),
      weight: Number(form.weight),
      height: Number(form.height),
    });
  };

  return (
    <div className="space-y-4">
      <h2 className="text-2xl font-semibold">Triage / Vital Signs</h2>
      <form onSubmit={submit} className="bg-white p-4 rounded shadow grid md:grid-cols-2 gap-3">
        <input className="border rounded px-3 py-2" placeholder="Appointment ID" value={form.appointmentId} onChange={(e) => setForm({ ...form, appointmentId: e.target.value })} required />
        <input className="border rounded px-3 py-2" placeholder="Patient ID" value={form.patientId} onChange={(e) => setForm({ ...form, patientId: e.target.value })} required />
        <input className="border rounded px-3 py-2" placeholder="Blood Pressure" value={form.bloodPressure} onChange={(e) => setForm({ ...form, bloodPressure: e.target.value })} required />
        <input className="border rounded px-3 py-2" type="number" step="0.1" placeholder="Temperature" value={form.temperature} onChange={(e) => setForm({ ...form, temperature: e.target.value })} required />
        <input className="border rounded px-3 py-2" type="number" step="0.1" placeholder="Weight" value={form.weight} onChange={(e) => setForm({ ...form, weight: e.target.value })} required />
        <input className="border rounded px-3 py-2" type="number" step="0.1" placeholder="Height" value={form.height} onChange={(e) => setForm({ ...form, height: e.target.value })} required />
        <input className="border rounded px-3 py-2" type="number" placeholder="Pulse" value={form.pulse} onChange={(e) => setForm({ ...form, pulse: e.target.value })} required />
        <input className="border rounded px-3 py-2" type="number" placeholder="SPO2" value={form.spo2} onChange={(e) => setForm({ ...form, spo2: e.target.value })} required />
        <textarea className="border rounded px-3 py-2 md:col-span-2" placeholder="Chief Complaint" value={form.chiefComplaint} onChange={(e) => setForm({ ...form, chiefComplaint: e.target.value })} required />
        <button className="md:col-span-2 bg-blue-600 text-white px-4 py-2 rounded">Record Vitals</button>
      </form>
    </div>
  );
}
