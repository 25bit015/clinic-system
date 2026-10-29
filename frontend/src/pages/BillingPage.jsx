import { useState } from "react";
import api from "../api/client";

export default function BillingPage() {
  const [appointmentId, setAppointmentId] = useState("");
  const [invoice, setInvoice] = useState(null);
  const [payment, setPayment] = useState({ amount: "", paymentMethod: "CASH", insuranceProvider: "" });

  const generateInvoice = async () => {
    const { data } = await api.post(`/billing/invoices/generate/${appointmentId}`);
    setInvoice(data);
  };

  const submitPayment = async (event) => {
    event.preventDefault();
    const { data } = await api.post("/billing/payments", {
      invoiceId: invoice.id,
      amount: Number(payment.amount),
      paymentMethod: payment.paymentMethod,
      insuranceProvider: payment.insuranceProvider,
    });
    setInvoice(data);
  };

  return (
    <div className="space-y-6">
      <h2 className="text-2xl font-semibold">Billing & Cashier</h2>
      <div className="bg-white p-4 rounded shadow flex gap-2">
        <input className="flex-1 border rounded px-3 py-2" placeholder="Appointment ID" value={appointmentId} onChange={(e) => setAppointmentId(e.target.value)} />
        <button onClick={generateInvoice} className="bg-blue-600 text-white px-4 py-2 rounded">Generate Invoice</button>
      </div>

      {invoice && (
        <div className="bg-white p-4 rounded shadow space-y-3">
          <h3 className="font-semibold">Invoice #{invoice.id}</h3>
          <p>Total: {invoice.totalAmount} | Paid: {invoice.paidAmount} | Status: {invoice.paymentStatus}</p>
          <ul className="list-disc pl-5 text-sm">
            {invoice.items?.map((item) => (
              <li key={item.id}>{item.description} - {item.amount}</li>
            ))}
          </ul>
          <form onSubmit={submitPayment} className="grid md:grid-cols-3 gap-2">
            <input className="border rounded px-3 py-2" type="number" step="0.01" placeholder="Amount" value={payment.amount} onChange={(e) => setPayment({ ...payment, amount: e.target.value })} required />
            <select className="border rounded px-3 py-2" value={payment.paymentMethod} onChange={(e) => setPayment({ ...payment, paymentMethod: e.target.value })}>
              <option value="CASH">Cash</option>
              <option value="INSURANCE_NHIF">Insurance (NHIF)</option>
              <option value="MOBILE_MONEY">Mobile Money</option>
            </select>
            <input className="border rounded px-3 py-2" placeholder="Insurance Provider" value={payment.insuranceProvider} onChange={(e) => setPayment({ ...payment, insuranceProvider: e.target.value })} />
            <button className="md:col-span-3 bg-emerald-600 text-white px-4 py-2 rounded">Record Payment</button>
          </form>
        </div>
      )}
    </div>
  );
}
