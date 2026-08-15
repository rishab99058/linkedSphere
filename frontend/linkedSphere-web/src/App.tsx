import {
  BrowserRouter,
  Navigate,
  Route,
  Routes,
} from "react-router-dom";

import LoginPage from "./features/auth/pages/login-page";
import RegisterPage from "./features/auth/pages/register-page";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={<Navigate to="/auth/login" replace />}
        />

        <Route
          path="/auth/login"
          element={<LoginPage />}
        />

        <Route
          path="/auth/register"
          element={<RegisterPage />}
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;