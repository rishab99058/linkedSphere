import { useState } from "react";
import { Eye, EyeOff } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { z } from "zod";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

import { registerUser } from "../api/auth-api";

const registerSchema = z
  .object({
    email: z
      .string()
      .min(1, "Email is required")
      .email("Enter a valid email address"),

    phoneNumber: z
      .string()
      .min(1, "Phone number is required")
      .regex(
        /^\+?[0-9\s-]{7,15}$/,
        "Enter a valid phone number",
      ),

    password: z
      .string()
      .min(8, "Password must be at least 8 characters"),

    confirmPassword: z
      .string()
      .min(1, "Please confirm your password"),
  })
  .refine(
    (data) => data.password === data.confirmPassword,
    {
      message: "Passwords do not match",
      path: ["confirmPassword"],
    },
  );

type RegisterFormData = z.infer<typeof registerSchema>;

function RegisterForm() {
  const navigate = useNavigate();

  const [formData, setFormData] =
    useState<RegisterFormData>({
      email: "",
      phoneNumber: "",
      password: "",
      confirmPassword: "",
    });

  const [errors, setErrors] = useState<
    Partial<Record<keyof RegisterFormData, string>>
  >({});

  const [serverError, setServerError] = useState("");

  const [isLoading, setIsLoading] = useState(false);

  const [showPassword, setShowPassword] =
    useState(false);

  const [showConfirmPassword, setShowConfirmPassword] =
    useState(false);

  function updateField(
    field: keyof RegisterFormData,
    value: string,
  ) {
    setFormData((previous) => ({
      ...previous,
      [field]: value,
    }));

    setErrors((previous) => ({
      ...previous,
      [field]: undefined,
    }));

    setServerError("");
  }

  async function handleSubmit(
    event: React.FormEvent<HTMLFormElement>,
  ) {
    event.preventDefault();

    const result = registerSchema.safeParse(formData);

    if (!result.success) {
      const fieldErrors: Partial<
        Record<keyof RegisterFormData, string>
      > = {};

      result.error.issues.forEach((issue) => {
        const field = issue.path[0] as keyof RegisterFormData;

        if (!fieldErrors[field]) {
          fieldErrors[field] = issue.message;
        }
      });

      setErrors(fieldErrors);
      return;
    }

    setErrors({});
    setServerError("");
    setIsLoading(true);

    try {
      await registerUser({
        email: formData.email,
        password: formData.password,
        phoneNumber: formData.phoneNumber,
      });

      navigate("/auth/login", {
        replace: true,
        state: {
          registered: true,
        },
      });
    } catch (error: any) {
      const message =
        error?.response?.data?.message ??
        "Unable to create your account. Please try again.";

      setServerError(message);
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <form
      onSubmit={handleSubmit}
      className="space-y-5"
      noValidate
    >
      <div className="space-y-2">
        <Label htmlFor="register-email">
          Email
        </Label>

        <Input
          id="register-email"
          type="email"
          autoComplete="email"
          placeholder="you@example.com"
          value={formData.email}
          onChange={(event) =>
            updateField("email", event.target.value)
          }
          disabled={isLoading}
        />

        {errors.email && (
          <p className="text-xs text-destructive">
            {errors.email}
          </p>
        )}
      </div>

      <div className="space-y-2">
        <Label htmlFor="register-phone">
          Phone number
        </Label>

        <Input
          id="register-phone"
          type="tel"
          autoComplete="tel"
          placeholder="+91 9876543210"
          value={formData.phoneNumber}
          onChange={(event) =>
            updateField(
              "phoneNumber",
              event.target.value,
            )
          }
          disabled={isLoading}
        />

        {errors.phoneNumber && (
          <p className="text-xs text-destructive">
            {errors.phoneNumber}
          </p>
        )}
      </div>

      <PasswordField
        id="register-password"
        label="Password"
        autoComplete="new-password"
        value={formData.password}
        visible={showPassword}
        disabled={isLoading}
        error={errors.password}
        onToggle={() =>
          setShowPassword((previous) => !previous)
        }
        onChange={(value) =>
          updateField("password", value)
        }
      />

      <PasswordField
        id="register-confirm-password"
        label="Confirm password"
        autoComplete="new-password"
        value={formData.confirmPassword}
        visible={showConfirmPassword}
        disabled={isLoading}
        error={errors.confirmPassword}
        onToggle={() =>
          setShowConfirmPassword(
            (previous) => !previous,
          )
        }
        onChange={(value) =>
          updateField("confirmPassword", value)
        }
      />

      {serverError && (
        <p className="text-sm text-destructive">
          {serverError}
        </p>
      )}

      <Button
        type="submit"
        className="h-11 w-full"
        disabled={isLoading}
      >
        {isLoading
          ? "Creating account..."
          : "Create account"}
      </Button>
    </form>
  );
}

function PasswordField({
  id,
  label,
  autoComplete,
  value,
  visible,
  disabled,
  error,
  onToggle,
  onChange,
}: {
  id: string;
  label: string;
  autoComplete: string;
  value: string;
  visible: boolean;
  disabled: boolean;
  error?: string;
  onToggle: () => void;
  onChange: (value: string) => void;
}) {
  return (
    <div className="space-y-2">
      <Label htmlFor={id}>{label}</Label>

      <div className="relative">
        <Input
          id={id}
          type={visible ? "text" : "password"}
          autoComplete={autoComplete}
          placeholder="••••••••"
          value={value}
          onChange={(event) =>
            onChange(event.target.value)
          }
          disabled={disabled}
          className="pr-11"
        />

        <button
          type="button"
          onClick={onToggle}
          className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground"
          aria-label={
            visible
              ? "Hide password"
              : "Show password"
          }
        >
          {visible ? (
            <EyeOff size={18} />
          ) : (
            <Eye size={18} />
          )}
        </button>
      </div>

      {error && (
        <p className="text-xs text-destructive">
          {error}
        </p>
      )}
    </div>
  );
}

export default RegisterForm;