import { Navigate, Outlet } from "react-router-dom";
import { useAuthStore } from "../store/authStore";

export default function ProtectedRoute({ roles = [] }) {
  const token = useAuthStore((state) => state.token);
  const hasAnyRole = useAuthStore((state) => state.hasAnyRole);

  if (!token) {
    return <Navigate to="/login" replace />;
  }

  if (roles.length > 0 && !hasAnyRole(roles)) {
    return <Navigate to="/dashboard" replace />;
  }

  return <Outlet />;
}
