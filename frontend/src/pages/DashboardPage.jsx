import { useEffect, useState } from "react";
import { Bar, BarChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis } from "recharts";
import api from "../api/client";

export default function DashboardPage() {
  const [report, setReport] = useState({ totalPatients: 0, totalAppointments: 0, revenue: 0, lowStockItems: 0 });

  useEffect(() => {
    api.get("/admin/reports/daily")
      .then(({ data }) => setReport(data))
      .catch(() => {
      });
  }, []);

  const chartData = [
    { name: "Patients", value: Number(report.totalPatients) || 0 },
    { name: "Appointments", value: Number(report.totalAppointments) || 0 },
    { name: "Revenue", value: Number(report.revenue) || 0 },
    { name: "Low Stock", value: Number(report.lowStockItems) || 0 },
  ];

  return (
    <div className="space-y-6">
      <h2 className="text-2xl font-semibold">Admin Dashboard</h2>
      <div className="grid md:grid-cols-4 gap-4">
        {chartData.map((item) => (
          <div key={item.name} className="bg-white p-4 rounded shadow">
            <p className="text-sm text-slate-500">{item.name}</p>
            <p className="text-2xl font-bold">{item.value}</p>
          </div>
        ))}
      </div>
      <div className="bg-white p-4 rounded shadow h-80">
        <ResponsiveContainer width="100%" height="100%">
          <BarChart data={chartData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="value" fill="#2563eb" />
          </BarChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
}
