import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { z } from "zod";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

import { forgotPassword } from "../api/auth-api";

const forgotPasswordSchema = z.object({
  email: z
    .string()
    .trim()
    .min(1, "Email is required")
    .email("Enter a valid email"),
});

type ForgotPasswordFormValues = z.infer<
  typeof forgotPasswordSchema
>;

type ForgotPasswordFormErrors = Partial<
  Record<keyof ForgotPasswordFormValues, string>
>;

function ForgotPasswordForm() {
  const navigate = useNavigate();

  const [formData, setFormData] =
    useState<ForgotPasswordFormValues>({
      email: "",
    });

  const [errors, setErrors] =
    useState<ForgotPasswordFormErrors>({});

  const [isLoading, setIsLoading] =
    useState(false);

  const [apiError, setApiError] =
    useState("");

  const [isSuccess, setIsSuccess] =
    useState(false);

  const [successMessage, setSuccessMessage] =
    useState("");

  function handleChange(value: string) {
    setFormData({
      email: value,
    });

    setErrors({});

    setApiError("");
    setIsSuccess(false);
    setSuccessMessage("");
  }

  async function handleSubmit(
    event: React.FormEvent<HTMLFormElement>
  ) {
    event.preventDefault();

    setApiError("");
    setSuccessMessage("");
    setIsSuccess(false);

    // Frontend validation
    const result =
      forgotPasswordSchema.safeParse(formData);

    if (!result.success) {
      const fieldErrors =
        result.error.flatten().fieldErrors;

      setErrors({
        email: fieldErrors.email?.[0],
      });

      return;
    }

    setErrors({});
    setIsLoading(true);

    try {
      // Request going to backend
      const response = await forgotPassword({
        email: result.data.email,
      });

      console.log(
        "Forgot password status:",
        response.status
      );

      console.log(
        "Forgot password response:",
        response.data
      );

      setIsSuccess(true);

      setSuccessMessage(
        response.data?.message ||
          "OTP has been sent to your email."
      );

      // Move to reset password page
      setTimeout(() => {
        navigate("/auth/reset-password", {
          state: {
            email: result.data.email,
          },
        });
      }, 1500);

    } catch (error) {
      console.error(
        "Forgot password failed:",
        error
      );

      setApiError(
        "Unable to send OTP. Please check your email and try again."
      );
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <div className="w-full rounded-xl bg-white p-8 shadow-xl">

      {/* HEADER */}
      <div className="mb-8 text-center">
        <h1 className="text-2xl font-bold text-slate-900">
          Forgot Password?
        </h1>

        <p className="mt-2 text-sm text-slate-500">
          Enter your registered email and we'll
          send you an OTP to reset your password.
        </p>
      </div>

      {/* FORM */}
      <form
        onSubmit={handleSubmit}
        className="space-y-5"
      >

        {/* EMAIL */}
        <div className="space-y-2">
          <Label htmlFor="forgot-email">
            Email
          </Label>

          <Input
            id="forgot-email"
            name="email"
            type="email"
            placeholder="Enter your email"
            autoComplete="email"
            value={formData.email}
            onChange={(event) =>
              handleChange(event.target.value)
            }
            aria-invalid={Boolean(errors.email)}
          />

          {errors.email ? (
            <p className="text-sm text-red-500">
              {errors.email}
            </p>
          ) : null}
        </div>

        {/* API ERROR */}
        {apiError ? (
          <p className="text-center text-sm text-red-500">
            {apiError}
          </p>
        ) : null}

        {/* SUCCESS */}
        {isSuccess ? (
          <div className="rounded-lg bg-green-50 p-3 text-center">
            <p className="text-sm font-medium text-green-600">
              {successMessage}
            </p>

            <p className="mt-1 text-xs text-green-600">
              Redirecting to password reset...
            </p>
          </div>
        ) : null}

        {/* SEND OTP */}
        <Button
          type="submit"
          className="w-full"
          disabled={isLoading || isSuccess}
        >
          {isLoading
            ? "Sending OTP..."
            : "Send OTP"}
        </Button>

        {/* BACK TO LOGIN */}
        <button
          type="button"
          onClick={() =>
            navigate("/auth/login")
          }
          className="w-full text-sm text-slate-500 hover:text-slate-800 hover:underline"
        >
          ← Back to Login
        </button>

      </form>
    </div>
  );
}

export default ForgotPasswordForm;