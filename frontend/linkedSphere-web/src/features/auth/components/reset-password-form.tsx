import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { z } from "zod";
import { Eye, EyeOff } from "lucide-react";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

import { resetPassword } from "../api/auth-api";

const resetPasswordSchema = z
  .object({
    otp: z
      .string()
      .trim()
      .min(1, "OTP is required"),

    password: z
      .string()
      .min(8, "Password must be at least 8 characters")
      .regex(
        /[A-Z]/,
        "Password must contain at least one uppercase letter"
      )
      .regex(
        /[0-9]/,
        "Password must contain at least one number"
      ),

    confirmPassword: z
      .string()
      .min(1, "Please confirm your password"),
  })
  .refine(
    (data) => data.password === data.confirmPassword,
    {
      message: "Passwords do not match",
      path: ["confirmPassword"],
    }
  );

type ResetPasswordFormValues = z.infer<
  typeof resetPasswordSchema
>;

type ResetPasswordFormErrors = Partial<
  Record<keyof ResetPasswordFormValues, string>
>;

function ResetPasswordForm() {
  const navigate = useNavigate();
  const location = useLocation();

  /*
   * Email was passed from ForgotPasswordForm
   */
  const email = location.state?.email;

  const [formData, setFormData] =
    useState<ResetPasswordFormValues>({
      otp: "",
      password: "",
      confirmPassword: "",
    });

  const [errors, setErrors] =
    useState<ResetPasswordFormErrors>({});

  const [isLoading, setIsLoading] =
    useState(false);

  const [apiError, setApiError] =
    useState("");

  const [isSuccess, setIsSuccess] =
    useState(false);

  const [showPassword, setShowPassword] =
    useState(false);

  const [showConfirmPassword, setShowConfirmPassword] =
    useState(false);

  function handleChange(
    field: keyof ResetPasswordFormValues,
    value: string
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
  }

  async function handleSubmit(
    event: React.FormEvent<HTMLFormElement>
  ) {
    event.preventDefault();

    setApiError("");

    /*
     * Safety check
     *
     * User should reach this page from Forgot Password page.
     */
    if (!email) {
      setApiError(
        "Email information is missing. Please request a new OTP."
      );
      return;
    }

    /*
     * Frontend validation
     */
    const result =
      resetPasswordSchema.safeParse(formData);

    if (!result.success) {
      const fieldErrors =
        result.error.flatten().fieldErrors;

      const nextErrors: ResetPasswordFormErrors = {};

      (
        Object.keys(formData) as Array<
          keyof ResetPasswordFormValues
        >
      ).forEach((field) => {
        const message =
          fieldErrors[field]?.[0];

        if (message) {
          nextErrors[field] = message;
        }
      });

      setErrors(nextErrors);
      return;
    }

    setErrors({});
    setIsLoading(true);

    try {
      /*
       * Call backend
       */
      const response = await resetPassword({
        email: email,
        otp: result.data.otp,
        password: result.data.password,
      });

      console.log(
        "Reset password status:",
        response.status
      );

      console.log(
        "Reset password response:",
        response.data
      );

      setIsSuccess(true);

      /*
       * Redirect to login after successful reset
       */
      setTimeout(() => {
        navigate("/auth/login");
      }, 2000);

    } catch (error) {
      console.error(
        "Reset password failed:",
        error
      );

      setApiError(
        "Unable to reset password. Please check your OTP and try again."
      );
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <form
      onSubmit={handleSubmit}
      className="space-y-5"
    >
      {/* EMAIL */}
      <div className="space-y-2">
        <Label>
          Email
        </Label>

        <Input
          value={email || ""}
          disabled
        />
      </div>

      {/* OTP */}
      <div className="space-y-2">
        <Label htmlFor="otp">
          OTP
        </Label>

        <Input
          id="otp"
          name="otp"
          type="text"
          placeholder="Enter OTP"
          autoComplete="one-time-code"
          value={formData.otp}
          onChange={(event) =>
            handleChange(
              "otp",
              event.target.value
            )
          }
          aria-invalid={Boolean(errors.otp)}
        />

        {errors.otp ? (
          <p className="text-sm text-red-500">
            {errors.otp}
          </p>
        ) : null}
      </div>

      {/* PASSWORD */}
      <div className="space-y-2">
        <Label htmlFor="password">
          New Password
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
            placeholder="Enter new password"
            autoComplete="new-password"
            value={formData.password}
            onChange={(event) =>
              handleChange(
                "password",
                event.target.value
              )
            }
            aria-invalid={Boolean(errors.password)}
            className="pr-10"
          />

          {formData.password.length > 0 && (
            <button
              type="button"
              onClick={() =>
                setShowPassword(
                  (previous) => !previous
                )
              }
              className="absolute right-3 top-1/2 -translate-y-1/2 text-slate-500 hover:text-slate-700"
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

        {errors.password ? (
          <p className="text-sm text-red-500">
            {errors.password}
          </p>
        ) : null}
      </div>

      {/* CONFIRM PASSWORD */}
      <div className="space-y-2">
        <Label htmlFor="confirmPassword">
          Confirm New Password
        </Label>

        <div className="relative">
          <Input
            id="confirmPassword"
            name="confirmPassword"
            type={
              showConfirmPassword
                ? "text"
                : "password"
            }
            placeholder="Confirm new password"
            autoComplete="new-password"
            value={formData.confirmPassword}
            onChange={(event) =>
              handleChange(
                "confirmPassword",
                event.target.value
              )
            }
            aria-invalid={Boolean(
              errors.confirmPassword
            )}
            className="pr-10"
          />

          {formData.confirmPassword.length > 0 && (
            <button
              type="button"
              onClick={() =>
                setShowConfirmPassword(
                  (previous) => !previous
                )
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
            Password reset successfully!
          </p>

          <p className="mt-1 text-xs text-green-600">
            Redirecting to login...
          </p>
        </div>
      ) : null}

      {/* RESET BUTTON */}
      <Button
        type="submit"
        className="w-full"
        disabled={
          isLoading || isSuccess
        }
      >
        {isLoading
          ? "Resetting Password..."
          : "Reset Password"}
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
  );
}

export default ResetPasswordForm;