import { useEffect, useState } from "react";
import api from "../api/client";

export default function AdminPage() {
  const [users, setUsers] = useState([]);
  const [drugs, setDrugs] = useState([]);

  useEffect(() => {
    Promise.all([api.get("/admin/users"), api.get("/admin/drugs")])
      .then(([usersResponse, drugsResponse]) => {
        setUsers(usersResponse.data);
        setDrugs(drugsResponse.data);
      })
      .catch(() => {
      });
  }, []);

  return (
    <div className="space-y-6">
      <h2 className="text-2xl font-semibold">Admin Module</h2>
      <div className="grid lg:grid-cols-2 gap-4">
        <div className="bg-white p-4 rounded shadow">
          <h3 className="font-semibold mb-3">Users & Roles</h3>
          <ul className="space-y-2 text-sm">
            {users.map((user) => (
              <li key={user.id} className="border rounded p-2">
                {user.fullName} ({user.username}) - {(user.roles || []).map((role) => role.name).join(", ")}
              </li>
            ))}
          </ul>
        </div>
        <div className="bg-white p-4 rounded shadow">
          <h3 className="font-semibold mb-3">Drug Inventory</h3>
          <ul className="space-y-2 text-sm">
            {drugs.map((drug) => (
              <li key={drug.id} className="border rounded p-2">
                {drug.drugName} | Stock: {drug.stockQuantity} | Price: {drug.unitPrice}
              </li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
}
