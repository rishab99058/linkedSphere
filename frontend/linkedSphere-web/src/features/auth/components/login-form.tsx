import { useState } from "react";
import { Eye, EyeOff, LockKeyhole, Mail } from "lucide-react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { z } from "zod";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

import { getUserProfile, loginUser } from "../api/auth-api";

import {
  removeAccessToken,
  setAccessToken,
  setRefreshToken,
} from "@/lib/auth-storage";

const loginSchema = z.object({
  email: z
    .string()
    .min(1, "Email is required")
    .email("Enter a valid email address"),

  password: z.string().min(1, "Password is required"),
});

function LoginForm() {
  const navigate = useNavigate();
  const location = useLocation();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [showPassword, setShowPassword] = useState(false);

  const [errors, setErrors] = useState<{
    email?: string;
    password?: string;
  }>({});

  const [serverError, setServerError] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const registrationSuccess = location.state?.registered === true;

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const result = loginSchema.safeParse({
      email,
      password,
    });

    if (!result.success) {
      const nextErrors: {
        email?: string;
        password?: string;
      } = {};

      result.error.issues.forEach((issue) => {
        const field = issue.path[0] as "email" | "password";

        if (!nextErrors[field]) {
          nextErrors[field] = issue.message;
        }
      });

      setErrors(nextErrors);
      return;
    }

    setErrors({});
    setServerError("");
    setIsLoading(true);

    try {
      removeAccessToken();

      const response = await loginUser({
        email,
        password,
      });

      const { accessToken, refreshToken } = response.data;

      setAccessToken(accessToken);
      setRefreshToken(refreshToken);

      await getUserProfile();

      navigate("/home", {
        replace: true,
      });
    } catch (error: any) {
      removeAccessToken();

      const status = error?.response?.status;

      if (status === 401 || status === 403) {
        setServerError("Invalid email or password.");
      } else {
        setServerError(
          error?.response?.data?.message ??
            "Unable to sign in. Please try again.",
        );
      }
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-5" noValidate>
      {/* Registration success */}
      {registrationSuccess && (
        <div className="flex items-start gap-3 rounded-xl border border-emerald-200 bg-emerald-50/80 px-4 py-3">
          <div className="mt-0.5 flex h-5 w-5 shrink-0 items-center justify-center rounded-full bg-emerald-500 text-[11px] font-bold text-white">
            ✓
          </div>

          <div>
            <p className="text-sm font-semibold text-emerald-800">
              Account created successfully
            </p>

            <p className="mt-0.5 text-xs leading-5 text-emerald-700">
              Sign in to continue to LinkedSphere.
            </p>
          </div>
        </div>
      )}

      {/* Email */}
      <div className="space-y-2">
        <Label
          htmlFor="login-email"
          className="text-sm font-semibold text-slate-700"
        >
          Email address
        </Label>

        <div className="relative">
          <Mail
            size={17}
            className="pointer-events-none absolute left-3.5 top-1/2 -translate-y-1/2 text-slate-400"
          />

          <Input
            id="login-email"
            type="email"
            autoComplete="email"
            placeholder="you@example.com"
            value={email}
            onChange={(event) => {
              setEmail(event.target.value);
              setServerError("");
            }}
            disabled={isLoading}
            className={`h-12 rounded-xl border-slate-200 bg-white pl-10 text-sm shadow-sm transition-all placeholder:text-slate-400 focus:border-blue-500 focus:ring-2 focus:ring-blue-500/10 ${
              errors.email ? "border-red-300 focus:border-red-400" : ""
            }`}
          />
        </div>

        {errors.email && (
          <p className="text-xs font-medium text-destructive">
            {errors.email}
          </p>
        )}
      </div>

      {/* Password */}
      <div className="space-y-2">
        <div className="flex items-center justify-between">
          <Label
            htmlFor="login-password"
            className="text-sm font-semibold text-slate-700"
          >
            Password
          </Label>

          <Link
            to="/auth/forgot-password"
            className="text-xs font-semibold text-blue-600 transition-colors hover:text-blue-700 hover:underline"
          >
            Forgot password?
          </Link>
        </div>

        <div className="relative">
          <LockKeyhole
            size={17}
            className="pointer-events-none absolute left-3.5 top-1/2 -translate-y-1/2 text-slate-400"
          />

          <Input
            id="login-password"
            type={showPassword ? "text" : "password"}
            autoComplete="current-password"
            placeholder="Enter your password"
            value={password}
            onChange={(event) => {
              setPassword(event.target.value);
              setServerError("");
            }}
            disabled={isLoading}
            className={`h-12 rounded-xl border-slate-200 bg-white pl-10 pr-11 text-sm shadow-sm transition-all placeholder:text-slate-400 focus:border-blue-500 focus:ring-2 focus:ring-blue-500/10 ${
              errors.password ? "border-red-300 focus:border-red-400" : ""
            }`}
          />

          <button
            type="button"
            onClick={() => setShowPassword((previous) => !previous)}
            disabled={isLoading}
            className="absolute right-3.5 top-1/2 -translate-y-1/2 rounded-md p-1 text-slate-400 transition-colors hover:text-slate-700 disabled:pointer-events-none disabled:opacity-50"
            aria-label={showPassword ? "Hide password" : "Show password"}
          >
            {showPassword ? <EyeOff size={17} /> : <Eye size={17} />}
          </button>
        </div>

        {errors.password && (
          <p className="text-xs font-medium text-destructive">
            {errors.password}
          </p>
        )}
      </div>

      {/* Server error */}
      {serverError && (
        <div className="rounded-xl border border-red-200 bg-red-50/80 px-4 py-3">
          <p className="text-sm font-medium leading-5 text-red-700">
            {serverError}
          </p>
        </div>
      )}

      {/* Submit */}
      <Button
        type="submit"
        disabled={isLoading}
        className="group relative h-12 w-full overflow-hidden rounded-xl bg-slate-950 text-sm font-semibold text-white shadow-lg shadow-slate-900/15 transition-all duration-200 hover:-translate-y-0.5 hover:bg-blue-600 hover:shadow-xl hover:shadow-blue-600/20 disabled:translate-y-0 disabled:opacity-70"
      >
        <span className="relative z-10">
          {isLoading ? "Signing in..." : "Sign in to LinkedSphere"}
        </span>
      </Button>
    </form>
  );
}

export default LoginForm;