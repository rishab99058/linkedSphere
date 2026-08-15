import { useState } from "react";
import { z } from "zod";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

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
  const [formData, setFormData] = useState<RegisterFormValues>({
    email: "",
    phoneNumber: "",
    password: "",
    confirmPassword: "",
  });
  const [errors, setErrors] = useState<RegisterFormErrors>({});

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

  function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const result = registerSchema.safeParse(formData);

    if (!result.success) {
      const fieldErrors = result.error.flatten().fieldErrors;
      const nextErrors: RegisterFormErrors = {};

      (Object.keys(formData) as Array<keyof RegisterFormValues>).forEach((field) => {
        const message = fieldErrors[field]?.[0];

        if (message) {
          nextErrors[field] = message;
        }
      });

      setErrors(nextErrors);
      return;
    }

    setErrors({});
    console.log("Registration validation successful", result.data);
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

        <Input
          id="password"
          type="password"
          value={formData.password}
          onChange={(event) => handleChange("password", event.target.value)}
          aria-invalid={Boolean(errors.password)}
        />
        {errors.password ? <p className="text-sm text-red-500">{errors.password}</p> : null}
      </div>

      <div className="space-y-2">
        <Label htmlFor="confirmPassword">Confirm Password</Label>

        <Input
          id="confirmPassword"
          type="password"
          value={formData.confirmPassword}
          onChange={(event) => handleChange("confirmPassword", event.target.value)}
          aria-invalid={Boolean(errors.confirmPassword)}
        />
        {errors.confirmPassword ? (
          <p className="text-sm text-red-500">{errors.confirmPassword}</p>
        ) : null}
      </div>

      <Button type="submit" className="w-full">
        Create Account
      </Button>
    </form>
  );
}

export default RegisterForm;