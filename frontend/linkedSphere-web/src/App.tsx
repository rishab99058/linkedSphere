import * as React from "react";
import {
  BrowserRouter,
  Route,
  Routes,
} from "react-router-dom";
import ProtectedRoute from "./routes/protected-route";

import LoginPage from "./features/auth/pages/login-page";
import RegisterPage from "./features/auth/pages/register-page";
import HomePage from "./features/feed/pages/home-page";
import ForgotPasswordPage from "./features/auth/pages/forgot-password-page";
import ResetPasswordPage from "./features/auth/pages/reset-password-page";
import LandingPage from "./features/landing/pages/landing-page";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={<LandingPage />}
        />

        <Route
          path="/auth/login"
          element={<LoginPage />}
        />

        <Route
          path="/auth/register"
          element={<RegisterPage />}
        />

        <Route
          path="/auth/forgot-password"
          element={<ForgotPasswordPage />}
        />

        <Route
          path="/auth/reset-password"
          element={<ResetPasswordPage />}
        />
        
        <Route element={<ProtectedRoute />}>
          <Route
            path="/home"
            element={<HomePage />}
          />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;