import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import { useState } from "react";
import { z } from "zod";
import { Eye, EyeOff } from "lucide-react";
import {
  setAccessToken,
  removeAccessToken,
  setRefreshToken,
} from "@/lib/auth-storage";
import { loginUser, getUserProfile } from "../api/auth-api";
import { useNavigate } from "react-router-dom";

const loginSchema = z.object({
  email: z
    .string()
    .trim()
    .min(1, "Email is required")
    .email("Enter a valid email"),

  password: z
    .string()
    .min(1, "Password is required"),
});

type LoginFormValues = z.infer<typeof loginSchema>;

type LoginFormErrors = Partial<
  Record<keyof LoginFormValues, string>
>;

function LoginForm() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState<LoginFormValues>({
    email: "",
    password: "",
  });

  const [errors, setErrors] = useState<LoginFormErrors>({});
  const [isLoading, setIsLoading] = useState(false);
  const [apiError, setApiError] = useState("");
  const [isSuccess, setIsSuccess] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  function handleChange(
    field: keyof LoginFormValues,
    value: string,
  ) {
    setFormData((previousData) => ({
      ...previousData,
      [field]: value,
    }));

    setErrors((previousErrors) => ({
      ...previousErrors,
      [field]: undefined,
    }));

    setApiError("");
    setIsSuccess(false);
  }

  async function handleSubmit(
    event: React.FormEvent<HTMLFormElement>,
  ) {
    event.preventDefault();

    setApiError("");
    setIsSuccess(false);

    const result = loginSchema.safeParse(formData);

    if (!result.success) {
      const fieldErrors = result.error.flatten().fieldErrors;

      const nextErrors: LoginFormErrors = {};

      (
        Object.keys(formData) as Array<
          keyof LoginFormValues
        >
      ).forEach((field) => {
        const message = fieldErrors[field]?.[0];

        if (message) {
          nextErrors[field] = message;
        }
      });

      setErrors(nextErrors);
      return;
    }

    setErrors({});
    setIsLoading(true);
    removeAccessToken();

    try {
      // Login
      const loginResponse = await loginUser({
        email: result.data.email,
        password: result.data.password,
      });

      console.log(
        "Login status:",
        loginResponse.status,
      );

      console.log(
        "Login response:",
        loginResponse.data,
      );

      // Get token
      const accessToken =
        loginResponse.data?.accessToken;

      const refreshToken =
        loginResponse.data?.refreshToken;

      if (!accessToken) {
        setApiError(
          "Login succeeded but access token was not returned.",
        );
        return;
      }

      // Store token
      setAccessToken(accessToken);

      if (refreshToken) {
        setRefreshToken(refreshToken);
      }

      // Call protected API
      try {
        const profileResponse =
          await getUserProfile();

        console.log(
          "Profile status:",
          profileResponse.status,
        );

        console.log(
          "Profile response:",
          profileResponse.data,
        );

        navigate("/home", {
          replace: true,
        });

        setIsSuccess(true);
      } catch (profileError) {
        console.error(
          "Profile request failed:",
          profileError,
        );

        setApiError(
          "Login succeeded, but profile could not be loaded.",
        );
      }
    } catch (loginError) {
      console.error(
        "Login failed:",
        loginError,
      );

      setApiError(
        "Invalid email or password. Please try again.",
      );
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <form
      onSubmit={handleSubmit}
      className="w-full space-y-6"
    >
      {/* ==================== EMAIL ==================== */}
      <div className="space-y-2.5">
        <Label
          htmlFor="email"
          className="text-sm font-semibold text-slate-900"
        >
          Email
        </Label>

        <Input
          id="email"
          name="email"
          type="email"
          placeholder="Enter your email"
          autoComplete="off"
          value={formData.email}
          onChange={(event) =>
            handleChange(
              "email",
              event.target.value,
            )
          }
          aria-invalid={Boolean(errors.email)}
          aria-describedby={
            errors.email
              ? "email-error"
              : undefined
          }
          className={`h-11 rounded-lg border-slate-300 bg-white px-3.5 text-sm transition-all placeholder:text-slate-400 focus-visible:border-blue-500 focus-visible:ring-2 focus-visible:ring-blue-500/20 ${
            errors.email
              ? "border-red-500 focus-visible:border-red-500 focus-visible:ring-red-500/20"
              : ""
          }`}
        />

        {errors.email && (
          <p
            id="email-error"
            className="text-xs font-medium text-red-500"
          >
            {errors.email}
          </p>
        )}
      </div>

      {/* ==================== PASSWORD ==================== */}
      <div className="space-y-2.5">
        <Label
          htmlFor="password"
          className="text-sm font-semibold text-slate-900"
        >
          Password
        </Label>

        <div className="relative">
          <Input
            id="password"
            name="password"
            type={
              showPassword
                ? "text"
                : "password"
            }
            placeholder="Enter your password"
            autoComplete="current-password"
            value={formData.password}
            onChange={(event) =>
              handleChange(
                "password",
                event.target.value,
              )
            }
            aria-invalid={Boolean(errors.password)}
            aria-describedby={
              errors.password
                ? "password-error"
                : undefined
            }
            className={`h-11 rounded-lg border-slate-300 bg-white px-3.5 pr-11 text-sm transition-all placeholder:text-slate-400 focus-visible:border-blue-500 focus-visible:ring-2 focus-visible:ring-blue-500/20 ${
              errors.password
                ? "border-red-500 focus-visible:border-red-500 focus-visible:ring-red-500/20"
                : ""
            }`}
          />

          {formData.password.length > 0 && (
            <button
              type="button"
              onClick={() =>
                setShowPassword(
                  (previous) => !previous,
                )
              }
              className="absolute right-3 top-1/2 flex h-7 w-7 -translate-y-1/2 items-center justify-center rounded-md text-slate-400 transition-colors hover:bg-slate-100 hover:text-slate-700 focus:outline-none focus:ring-2 focus:ring-blue-500/30"
              aria-label={
                showPassword
                  ? "Hide password"
                  : "Show password"
              }
            >
              {showPassword ? (
                <EyeOff className="h-4 w-4" />
              ) : (
                <Eye className="h-4 w-4" />
              )}
            </button>
          )}
        </div>

        {errors.password && (
          <p
            id="password-error"
            className="text-xs font-medium text-red-500"
          >
            {errors.password}
          </p>
        )}
      </div>

      {/* ==================== FORGOT PASSWORD ==================== */}
      <div className="-mt-1 flex justify-end">
        <button
          type="button"
          onClick={() =>
            navigate("/auth/forgot-password")
          }
          className="text-sm font-medium text-blue-600 transition-colors hover:text-blue-700 hover:underline focus:outline-none focus:ring-2 focus:ring-blue-500/30"
        >
          Forgot Password?
        </button>
      </div>

      {/* ==================== API ERROR / SUCCESS ==================== */}
      {(apiError || isSuccess) && (
        <div className="min-h-5">
          {apiError && (
            <p className="rounded-md bg-red-50 px-3 py-2 text-center text-sm font-medium text-red-600">
              {apiError}
            </p>
          )}

          {isSuccess && (
            <p className="rounded-md bg-green-50 px-3 py-2 text-center text-sm font-medium text-green-600">
              Login successful!
            </p>
          )}
        </div>
      )}

      {/* ==================== LOGIN BUTTON ==================== */}
      <Button
        type="submit"
        disabled={isLoading}
        className="h-11 w-full rounded-lg bg-blue-600 text-sm font-semibold text-white shadow-sm transition-all hover:bg-blue-700 hover:shadow-md focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-60"
      >
        {isLoading
          ? "Signing in..."
          : "Login"}
      </Button>
    </form>
  );
}

export default LoginForm;