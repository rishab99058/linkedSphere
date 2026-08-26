import { Navigate, Outlet } from "react-router-dom";
import { getAccessToken } from "@/lib/auth-storage";

function ProtectedRoute() {
  const accessToken = getAccessToken();

  if (!accessToken) {
    return <Navigate to="/auth/login" replace />;
  }

  return <Outlet />;
}

export default ProtectedRoute;