import { useState } from "react";
import { z } from "zod";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useNavigate } from "react-router-dom";
import { Eye, EyeOff } from "lucide-react";

import { registerUser } from "../api/auth-api";

const registerSchema = z
  .object({
    email: z.string().trim().min(1, "Email is required").email("Enter a valid email"),
    phoneNumber: z
      .string()
      .trim()
      .min(1, "Phone number is required")
      .regex(/^\+?[0-9\s-]{7,15}$/, "Enter a valid phone number"),
    password: z
      .string()
      .min(8, "Password must be at least 8 characters")
      .regex(/[A-Z]/, "Password must contain at least one uppercase letter")
      .regex(/[0-9]/, "Password must contain at least one number"),
    confirmPassword: z.string().min(1, "Please confirm your password"),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"],
  });

type RegisterFormValues = z.infer<typeof registerSchema>;
type RegisterFormErrors = Partial<Record<keyof RegisterFormValues, string>>;

function RegisterForm() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState<RegisterFormValues>({
    email: "",
    phoneNumber: "",
    password: "",
    confirmPassword: "",
  });
  const [errors, setErrors] = useState<RegisterFormErrors>({});
  const [isLoading, setIsLoading] = useState(false);
  const [apiError, setApiError] = useState("");
  const [isSuccess, setIsSuccess] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  function handleChange(field: keyof RegisterFormValues, value: string) {
    setFormData((previousData) => ({
      ...previousData,
      [field]: value,
    }));

    setErrors((previousErrors) => ({
      ...previousErrors,
      [field]: undefined,
    }));
  }

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    setApiError("");
    setIsSuccess(false);

    const result = registerSchema.safeParse(formData);

    if (!result.success) {
      const fieldErrors = result.error.flatten().fieldErrors;
      const nextErrors: RegisterFormErrors = {};

      (
        Object.keys(formData) as Array<keyof RegisterFormValues>
      ).forEach((field) => {
        const message = fieldErrors[field]?.[0];

        if (message) {
          nextErrors[field] = message;
        }
      });

      setErrors(nextErrors);
      return;
    }

    // Validation successful
    setErrors({});

  
    const payload = {
      email: result.data.email,
      password: result.data.password,
      phoneNumber: result.data.phoneNumber,
    };

    console.log("Payload going to backend:", payload);

    try {
      setIsLoading(true);

      const response = await registerUser(payload);

      console.log("Registration status:", response.status);
      console.log("Registration response:", response.data);

      setIsSuccess(true);
      setTimeout(() => {
    navigate("/auth/login");
  }, 2000);

    } catch (error) {
      console.error("Registration failed:", error);

      setApiError(
        "Unable to create your account. Please try again.",
      );
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-5">
      <div className="space-y-2">
        <Label htmlFor="email">Email</Label>

        <Input
          id="email"
          name="email"
          type="email"
          placeholder="Enter your email"
          autoComplete="email"
          value={formData.email}
          onChange={(event) => handleChange("email", event.target.value)}
          aria-invalid={Boolean(errors.email)}
        />
        {errors.email ? <p className="text-sm text-red-500">{errors.email}</p> : null}
      </div>

      <div className="space-y-2">
        <Label htmlFor="phoneNumber">Phone Number</Label>

        <Input
          id="phoneNumber"
          name="phoneNumber"
          type="tel"
          placeholder="Enter your phone number"
          autoComplete="tel"
          value={formData.phoneNumber}
          onChange={(event) => handleChange("phoneNumber", event.target.value)}
          aria-invalid={Boolean(errors.phoneNumber)}
        />
        {errors.phoneNumber ? (
          <p className="text-sm text-red-500">{errors.phoneNumber}</p>
        ) : null}
      </div>

      <div className="space-y-2">
        <Label htmlFor="password">Password</Label>

        <div className="relative">
          <Input
            id="password"
            name="password"
            type={showPassword ? "text" : "password"}
            placeholder="Enter your password"
            autoComplete="new-password"
            value={formData.password}
            onChange={(event) =>
              handleChange("password", event.target.value)
            }
            aria-invalid={Boolean(errors.password)}
            className="pr-10"
          />

          {formData.password.length > 0 && (
            <button
              type="button"
              onClick={() =>
                setShowPassword((previous) => !previous)
              }
              className="absolute right-3 top-1/2 -translate-y-1/2 text-slate-500 hover:text-slate-700"
              aria-label={
                showPassword ? "Hide password" : "Show password"
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

        {errors.password ? (
          <p className="text-sm text-red-500">
            {errors.password}
          </p>
        ) : null}
      </div>

      <div className="space-y-2">
        <Label htmlFor="confirmPassword">
          Confirm Password
        </Label>

        <div className="relative">
          <Input
            id="confirmPassword"
            name="confirmPassword"
            type={showConfirmPassword ? "text" : "password"}
            placeholder="Confirm your password"
            autoComplete="new-password"
            value={formData.confirmPassword}
            onChange={(event) =>
              handleChange("confirmPassword", event.target.value)
            }
            aria-invalid={Boolean(errors.confirmPassword)}
            className="pr-10"
          />

          {formData.confirmPassword.length > 0 && (
            <button
              type="button"
              onClick={() =>
                setShowConfirmPassword((previous) => !previous)
              }
              className="absolute right-3 top-1/2 -translate-y-1/2 text-slate-500 hover:text-slate-700"
              aria-label={
                showConfirmPassword
                  ? "Hide confirm password"
                  : "Show confirm password"
              }
            >
              {showConfirmPassword ? (
                <EyeOff className="h-4 w-4" />
              ) : (
                <Eye className="h-4 w-4" />
              )}
            </button>
          )}
        </div>

        {errors.confirmPassword ? (
          <p className="text-sm text-red-500">
            {errors.confirmPassword}
          </p>
        ) : null}
      </div>


      {apiError ? (
        <p className="text-sm text-red-500">
          {apiError}
        </p>
      ) : null}

      {isSuccess ? (
        <div className="flex flex-col items-center gap-2 py-3 text-center animate-in fade-in zoom-in duration-500">
          <div className="flex h-12 w-12 items-center justify-center rounded-full bg-green-100">
            <span className="text-xl text-green-600">✓</span>
          </div>

          <p className="font-semibold text-green-600">
            Account created successfully!
          </p>

          <p className="text-sm text-slate-500">
            Login now...
          </p>
        </div>
      ) : null}

      <Button
        type="submit"
        className="w-full"
        disabled={isLoading || isSuccess}
      >
        {isLoading ? "Creating Account..." : "Create Account"}
      </Button>
    </form>
  );
}

export default RegisterForm;