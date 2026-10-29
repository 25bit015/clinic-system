import { create } from "zustand";
import api from "../api/client";

export const useAuthStore = create((set, get) => ({
  token: localStorage.getItem("clinic_token") || "",
  user: localStorage.getItem("clinic_user") || "",
  roles: JSON.parse(localStorage.getItem("clinic_roles") || "[]"),
  loading: false,
  error: "",

  login: async (username, password) => {
    set({ loading: true, error: "" });
    try {
      const response = await api.post("/auth/login", { username, password });
      const { token, username: user, roles } = response.data;
      localStorage.setItem("clinic_token", token);
      localStorage.setItem("clinic_user", user);
      localStorage.setItem("clinic_roles", JSON.stringify(roles));
      set({ token, user, roles, loading: false });
      return true;
    } catch (error) {
      set({ loading: false, error: error.response?.data?.message || "Login failed" });
      return false;
    }
  },

  logout: () => {
    localStorage.removeItem("clinic_token");
    localStorage.removeItem("clinic_user");
    localStorage.removeItem("clinic_roles");
    set({ token: "", user: "", roles: [], error: "" });
  },

  hasAnyRole: (acceptedRoles) => {
    const { roles } = get();
    return roles.some((role) => acceptedRoles.includes(role));
  },
}));
