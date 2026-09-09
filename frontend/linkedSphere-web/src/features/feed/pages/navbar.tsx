import { LogOut } from "lucide-react";
import { useNavigate } from "react-router-dom";

import {
  clearAuthStorage,
  getRefreshToken,
} from "@/lib/auth-storage";

import { logoutUser } from "@/features/auth/api/auth-api";

function Navbar() {
  const navigate = useNavigate();

  async function handleLogout() {
    const refreshToken = getRefreshToken();

    try {
      if (refreshToken) {
        await logoutUser({
          refreshToken,
        });
      }
    } catch {
      // Logout locally even if server logout fails.
    } finally {
      clearAuthStorage();

      navigate("/auth/login", {
        replace: true,
      });
    }
  }

  return (
    <header className="sticky top-0 z-40 border-b bg-white">
      <div className="mx-auto flex h-16 max-w-6xl items-center justify-between px-4">
        <div className="font-bold text-slate-950">
          LinkedSphere
        </div>

        <button
          type="button"
          onClick={handleLogout}
          className="inline-flex items-center gap-2 rounded-lg px-3 py-2 text-sm font-medium text-slate-600 hover:bg-slate-100 hover:text-slate-950"
        >
          <LogOut size={17} />
          Logout
        </button>
      </div>
    </header>
  );
}

export default Navbar;