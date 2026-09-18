import { useEffect, useState } from "react";
import api from "../api/client";

export default function DoctorPage() {
  const [queue, setQueue] = useState([]);
  const [consultation, setConsultation] = useState({ appointmentId: "", patientId: "", doctorId: "", clinicalNotes: "", followUpDate: "", referralSpecialist: "", admitted: false });

  const loadQueue = async () => {
    const { data } = await api.get("/doctor/queue");
    setQueue(data);
  };

  useEffect(() => {
    loadQueue();
  }, []);

  const submitConsultation = async (event) => {
    event.preventDefault();
    await api.post("/doctor/consultations", {
      ...consultation,
      appointmentId: Number(consultation.appointmentId),
      patientId: Number(consultation.patientId),
      doctorId: Number(consultation.doctorId),
      followUpDate: consultation.followUpDate ? `${consultation.followUpDate}T09:00:00` : null,
    });
  };

  return (
    <div className="space-y-6">
      <h2 className="text-2xl font-semibold">Doctor Consultation</h2>
      <div className="bg-white p-4 rounded shadow">
        <div className="flex justify-between items-center mb-3">
          <h3 className="font-semibold">Doctor Queue</h3>
          <button onClick={loadQueue} className="bg-slate-800 text-white px-3 py-1 rounded">Refresh</button>
        </div>
        <ul className="space-y-2 text-sm">
          {queue.map((item) => (
            <li key={item.id} className="border rounded p-2">Appointment #{item.id} - {item.patient?.fullName} - {item.department}</li>
          ))}
        </ul>
      </div>

      <form onSubmit={submitConsultation} className="bg-white p-4 rounded shadow grid md:grid-cols-2 gap-3">
        <input className="border rounded px-3 py-2" placeholder="Appointment ID" value={consultation.appointmentId} onChange={(e) => setConsultation({ ...consultation, appointmentId: e.target.value })} required />
        <input className="border rounded px-3 py-2" placeholder="Patient ID" value={consultation.patientId} onChange={(e) => setConsultation({ ...consultation, patientId: e.target.value })} required />
        <input className="border rounded px-3 py-2" placeholder="Doctor User ID" value={consultation.doctorId} onChange={(e) => setConsultation({ ...consultation, doctorId: e.target.value })} required />
        <input className="border rounded px-3 py-2" type="date" value={consultation.followUpDate} onChange={(e) => setConsultation({ ...consultation, followUpDate: e.target.value })} />
        <input className="border rounded px-3 py-2 md:col-span-2" placeholder="Referral specialist" value={consultation.referralSpecialist} onChange={(e) => setConsultation({ ...consultation, referralSpecialist: e.target.value })} />
        <textarea className="border rounded px-3 py-2 md:col-span-2" placeholder="Clinical notes" value={consultation.clinicalNotes} onChange={(e) => setConsultation({ ...consultation, clinicalNotes: e.target.value })} required />
        <label className="md:col-span-2 text-sm flex items-center gap-2">
          <input type="checkbox" checked={consultation.admitted} onChange={(e) => setConsultation({ ...consultation, admitted: e.target.checked })} />
          Admit patient
        </label>
        <button className="md:col-span-2 bg-blue-600 text-white px-4 py-2 rounded">Save Consultation</button>
      </form>
    </div>
  );
}
