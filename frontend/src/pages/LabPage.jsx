import { useEffect, useState } from "react";
import api from "../api/client";

export default function LabPage() {
  const [orders, setOrders] = useState([]);
  const [form, setForm] = useState({ labOrderId: "", resultNotes: "", reportUrl: "", status: "COMPLETED" });

  const loadOrders = async () => {
    const { data } = await api.get("/lab/orders/pending");
    setOrders(data);
  };

  useEffect(() => {
    loadOrders();
  }, []);

  const submitResult = async (event) => {
    event.preventDefault();
    await api.post("/lab/results", { ...form, labOrderId: Number(form.labOrderId) });
    loadOrders();
  };

  return (
    <div className="space-y-6">
      <h2 className="text-2xl font-semibold">Laboratory</h2>
      <div className="bg-white p-4 rounded shadow">
        <h3 className="font-semibold mb-3">Pending Lab Orders</h3>
        <ul className="space-y-2 text-sm">
          {orders.map((order) => (
            <li key={order.id} className="border rounded p-2">Order #{order.id} - {order.patient?.fullName} - {order.labTest?.name}</li>
          ))}
        </ul>
      </div>
      <form onSubmit={submitResult} className="bg-white p-4 rounded shadow grid md:grid-cols-2 gap-3">
        <input className="border rounded px-3 py-2" placeholder="Lab Order ID" value={form.labOrderId} onChange={(e) => setForm({ ...form, labOrderId: e.target.value })} required />
        <select className="border rounded px-3 py-2" value={form.status} onChange={(e) => setForm({ ...form, status: e.target.value })}>
          <option value="COMPLETED">Completed</option>
          <option value="VERIFIED">Verified</option>
        </select>
        <textarea className="border rounded px-3 py-2 md:col-span-2" placeholder="Result notes" value={form.resultNotes} onChange={(e) => setForm({ ...form, resultNotes: e.target.value })} required />
        <input className="border rounded px-3 py-2 md:col-span-2" placeholder="Report URL" value={form.reportUrl} onChange={(e) => setForm({ ...form, reportUrl: e.target.value })} />
        <button className="md:col-span-2 bg-blue-600 text-white px-4 py-2 rounded">Submit Result</button>
      </form>
    </div>
  );
}
