import { useEffect, useState } from "react";
import api from "../api/client";

export default function PharmacyPage() {
  const [prescriptions, setPrescriptions] = useState([]);
  const [lowStock, setLowStock] = useState([]);

  const refresh = async () => {
    const [{ data: pending }, { data: stock }] = await Promise.all([
      api.get("/pharmacy/prescriptions/pending"),
      api.get("/pharmacy/inventory/low-stock"),
    ]);
    setPrescriptions(pending);
    setLowStock(stock);
  };

  useEffect(() => {
    refresh();
  }, []);

  const dispense = async (id) => {
    await api.post(`/pharmacy/prescriptions/${id}/dispense`);
    refresh();
  };

  return (
    <div className="space-y-6">
      <h2 className="text-2xl font-semibold">Pharmacy</h2>
      <div className="bg-white p-4 rounded shadow">
        <h3 className="font-semibold mb-3">Pending Prescriptions</h3>
        <ul className="space-y-2 text-sm">
          {prescriptions.map((prescription) => (
            <li key={prescription.id} className="border rounded p-2 flex justify-between items-center">
              <span>#{prescription.id} - {prescription.patient?.fullName} - {prescription.drug?.drugName}</span>
              <button onClick={() => dispense(prescription.id)} className="bg-emerald-600 text-white px-2 py-1 rounded">Dispense</button>
            </li>
          ))}
        </ul>
      </div>
      <div className="bg-white p-4 rounded shadow">
        <h3 className="font-semibold mb-3">Low Stock Alerts</h3>
        <ul className="space-y-2 text-sm">
          {lowStock.map((drug) => (
            <li key={drug.id} className="border rounded p-2">{drug.drugName} - Stock: {drug.stockQuantity} / Reorder level: {drug.reorderLevel}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}
