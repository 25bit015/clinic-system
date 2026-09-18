import { Link, Outlet } from "react-router-dom";
import { useAuthStore } from "../store/authStore";

const links = [
  { path: "/dashboard", label: "Dashboard", roles: ["ADMIN", "RECEPTIONIST", "DOCTOR", "NURSE", "LAB_TECH", "PHARMACIST", "CASHIER"] },
  { path: "/patients", label: "Reception", roles: ["ADMIN", "RECEPTIONIST"] },
  { path: "/triage", label: "Triage", roles: ["ADMIN", "NURSE"] },
  { path: "/doctor", label: "Doctor", roles: ["ADMIN", "DOCTOR"] },
  { path: "/lab", label: "Laboratory", roles: ["ADMIN", "LAB_TECH", "DOCTOR"] },
  { path: "/pharmacy", label: "Pharmacy", roles: ["ADMIN", "PHARMACIST"] },
  { path: "/billing", label: "Billing", roles: ["ADMIN", "CASHIER"] },
  { path: "/admin", label: "Admin", roles: ["ADMIN"] },
];

export default function Layout() {
  const logout = useAuthStore((state) => state.logout);
  const user = useAuthStore((state) => state.user);
  const roles = useAuthStore((state) => state.roles);

  return (
    <div className="min-h-screen grid grid-cols-12">
      <aside className="col-span-12 md:col-span-3 lg:col-span-2 bg-slate-900 text-white p-4">
        <h1 className="font-bold text-lg mb-4">Clinic System</h1>
        <p className="text-sm mb-3">{user}</p>
        <p className="text-xs mb-4 text-slate-300">{roles.join(", ")}</p>
        <nav className="space-y-2">
          {links
            .filter((link) => link.roles.some((role) => roles.includes(role)))
            .map((link) => (
              <Link key={link.path} to={link.path} className="block px-3 py-2 rounded bg-slate-800 hover:bg-slate-700">
                {link.label}
              </Link>
            ))}
        </nav>
        <button onClick={logout} className="mt-5 w-full bg-rose-600 px-3 py-2 rounded">Logout</button>
      </aside>
      <main className="col-span-12 md:col-span-9 lg:col-span-10 p-6">
        <Outlet />
      </main>
    </div>
  );
}
