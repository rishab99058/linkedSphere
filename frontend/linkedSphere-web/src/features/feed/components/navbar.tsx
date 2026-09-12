import {Bell, Home, Menu, Network, User } from "lucide-react";

import { Button } from "@/components/ui/button";
import { useNavigate } from "react-router-dom";
import { clearAuthStorage, getRefreshToken } from "@/lib/auth-storage";
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
    } catch (error) {
        console.error("Logout API failed:", error);
    } finally {
        clearAuthStorage();

        navigate("/auth/login", {
        replace: true,
        });
    }
 }

  return (
    <header className="sticky top-0 z-50 border-b bg-white">
      <div className="mx-auto flex h-16 max-w-6xl items-center justify-between px-4">
        {/* Brand */}
        <div className="flex items-center gap-3">
          <div className="flex h-9 w-9 items-center justify-center rounded-full bg-blue-600 font-bold text-white">
            LS
          </div>

          <span className="hidden text-xl font-bold sm:block">
            LinkedSphere
          </span>
        </div>

        {/* Navigation */}
        <nav className="flex items-center gap-1">
          <Button
            variant="ghost"
            size="icon"
            onClick={() => navigate("/home")}
            title="Home"
          >
            <Home />
          </Button>

          <Button
            variant="ghost"
            size="icon"
            title="Network"
          >
            <Network />
          </Button>

          <Button
            variant="ghost"
            size="icon"
            title="Notifications"
          >
            <Bell />
          </Button>

          <Button
            variant="ghost"
            size="icon"
            title="Profile"
          >
            <User />
          </Button>

          {/* Menu */}
          <div className="relative">
            <details>
              <summary className="flex h-10 w-10 cursor-pointer list-none items-center justify-center rounded-md hover:bg-slate-100">
                <Menu className="h-5 w-5" />
              </summary>

              <div className="absolute right-0 mt-2 w-48 rounded-lg border bg-white p-2 shadow-lg">
                <button
                  className="w-full rounded-md px-3 py-2 text-left text-sm hover:bg-slate-100"
                >
                  Profile
                </button>

                <button
                  className="w-full rounded-md px-3 py-2 text-left text-sm hover:bg-slate-100"
                >
                  Settings
                </button>

                <div className="my-1 border-t" />

                <button
                  onClick={handleLogout}
                  className="w-full rounded-md px-3 py-2 text-left text-sm text-red-600 hover:bg-red-50"
                >
                  Logout
                </button>
              </div>
            </details>
          </div>
        </nav>
      </div>
    </header>
  );
}

export default Navbar;