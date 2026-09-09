import { useRef, useState } from "react";
import {
  ArrowLeft,
  Eye,
  EyeOff,
  LockKeyhole,
  Mail,
} from "lucide-react";
import {
  Link,
  useLocation,
  useNavigate,
} from "react-router-dom";
import { z } from "zod";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

import { resetPassword } from "../api/auth-api";

const schema = z
  .object({
    email: z
      .string()
      .min(1, "Email is required")
      .email("Enter a valid email address"),

    otp: z
      .string()
      .length(6, "Enter the complete 6-digit OTP"),

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

function ResetPasswordForm() {
  const navigate = useNavigate();
  const location = useLocation();

  const email = location.state?.email ?? "";

  const [otp, setOtp] = useState<string[]>(
    Array(6).fill(""),
  );

  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] =
    useState("");

  const [showPassword, setShowPassword] =
    useState(false);

  const [
    showConfirmPassword,
    setShowConfirmPassword,
  ] = useState(false);

  const [error, setError] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const otpRefs = useRef<
    Array<HTMLInputElement | null>
  >([]);

  function getOtpValue() {
    return otp.join("");
  }

  function handleOtpChange(
    index: number,
    value: string,
  ) {
    const digits = value.replace(/\D/g, "");

    if (!digits) {
      const nextOtp = [...otp];
      nextOtp[index] = "";

      setOtp(nextOtp);
      setError("");
      return;
    }

    const nextOtp = [...otp];

    digits
      .slice(0, 6 - index)
      .split("")
      .forEach((digit, offset) => {
        nextOtp[index + offset] = digit;
      });

    setOtp(nextOtp);
    setError("");

    const nextIndex = Math.min(
      index + digits.length,
      5,
    );

    otpRefs.current[nextIndex]?.focus();
  }

  function handleOtpKeyDown(
    index: number,
    event: React.KeyboardEvent<HTMLInputElement>,
  ) {
    if (
      event.key === "Backspace" &&
      !otp[index] &&
      index > 0
    ) {
      otpRefs.current[index - 1]?.focus();
    }
  }

  function handleOtpPaste(
    event: React.ClipboardEvent<HTMLInputElement>,
  ) {
    event.preventDefault();

    const pastedValue = event.clipboardData
      .getData("text")
      .replace(/\D/g, "")
      .slice(0, 6);

    if (!pastedValue) {
      return;
    }

    const nextOtp = Array(6).fill("");

    pastedValue
      .split("")
      .forEach((digit, index) => {
        nextOtp[index] = digit;
      });

    setOtp(nextOtp);
    setError("");

    otpRefs.current[
      Math.min(pastedValue.length, 5)
    ]?.focus();
  }

  async function handleSubmit(
    event: React.FormEvent<HTMLFormElement>,
  ) {
    event.preventDefault();

    const currentOtp = getOtpValue();

    const result = schema.safeParse({
      email,
      otp: currentOtp,
      password,
      confirmPassword,
    });

    if (!result.success) {
      setError(result.error.issues[0].message);
      return;
    }

    setError("");
    setIsLoading(true);

    try {
      await resetPassword({
        email,
        otp: currentOtp,
        password,
      });

      navigate("/auth/login", {
        replace: true,
        state: {
          resetSuccess: true,
        },
      });
    } catch (error: any) {
      const message =
        error?.response?.data?.message ??
        "Unable to reset your password. Please try again.";

      setError(message);

      setOtp(Array(6).fill(""));
      setPassword("");
      setConfirmPassword("");

      otpRefs.current[0]?.focus();
    } finally {
      setIsLoading(false);
    }
  }

  if (!email) {
    return (
      <div className="space-y-3">
        <div className="rounded-xl border border-red-200 bg-red-50/80 px-4 py-3">
          <p className="text-sm font-medium leading-5 text-red-700">
            We couldn't find the email associated with this password reset
            request.
          </p>
        </div>

        <Link
          to="/auth/forgot-password"
          className="group flex items-center justify-center gap-1.5 text-sm font-semibold text-slate-500 transition-colors hover:text-blue-600"
        >
          <ArrowLeft
            size={14}
            className="transition-transform duration-200 group-hover:-translate-x-0.5"
          />
          Request a new OTP
        </Link>
      </div>
    );
  }

  return (
    <form
      onSubmit={handleSubmit}
      className="space-y-3.5"
      noValidate
    >
      {/* Email */}
      <div className="space-y-1.5">
        <Label
          htmlFor="reset-email"
          className="text-xs font-semibold text-slate-700"
        >
          Email address
        </Label>

        <div className="relative">
          <Mail
            size={16}
            className="pointer-events-none absolute left-3 top-1/2 -translate-y-1/2 text-slate-400"
          />

          <Input
            id="reset-email"
            type="email"
            value={email}
            readOnly
            aria-readonly="true"
            className="h-10 rounded-xl border-slate-200 bg-slate-50 pl-9 text-sm text-slate-600 shadow-sm focus:border-slate-200 focus:ring-0"
          />
        </div>
      </div>

      {/* OTP */}
      <div className="space-y-1.5">
        <div className="flex items-center justify-between">
          <Label className="text-xs font-semibold text-slate-700">
            Verification code
          </Label>

          <span className="text-[11px] font-medium text-slate-400">
            6 digits
          </span>
        </div>

        <div className="flex gap-1.5 sm:gap-2">
          {otp.map((digit, index) => (
            <Input
              key={index}
              ref={(element) => {
                otpRefs.current[index] = element;
              }}
              value={digit}
              maxLength={1}
              inputMode="numeric"
              autoComplete={
                index === 0
                  ? "one-time-code"
                  : "off"
              }
              aria-label={`OTP digit ${index + 1}`}
              onChange={(event) =>
                handleOtpChange(
                  index,
                  event.target.value,
                )
              }
              onKeyDown={(event) =>
                handleOtpKeyDown(
                  index,
                  event,
                )
              }
              onPaste={handleOtpPaste}
              disabled={isLoading}
              className={`h-10 min-w-0 flex-1 rounded-lg bg-white p-0 text-center text-base font-semibold text-slate-950 shadow-sm transition-all focus:border-blue-500 focus:ring-2 focus:ring-blue-500/10 ${
                error
                  ? "border-red-300 focus:border-red-400"
                  : "border-slate-200"
              }`}
            />
          ))}
        </div>

        {error && (
          <p className="text-xs font-medium text-destructive">
            {error}
          </p>
        )}
      </div>

      {/* New password */}
      <PasswordInput
        id="reset-password"
        label="New password"
        value={password}
        visible={showPassword}
        onToggle={() =>
          setShowPassword(
            (previous) => !previous,
          )
        }
        onChange={(value) => {
          setPassword(value);
          setError("");
        }}
        disabled={isLoading}
      />

      {/* Confirm password */}
      <PasswordInput
        id="reset-confirm-password"
        label="Confirm password"
        value={confirmPassword}
        visible={showConfirmPassword}
        onToggle={() =>
          setShowConfirmPassword(
            (previous) => !previous,
          )
        }
        onChange={(value) => {
          setConfirmPassword(value);
          setError("");
        }}
        disabled={isLoading}
      />

      {/* Submit */}
      <Button
        type="submit"
        disabled={isLoading}
        className="group relative h-10 w-full overflow-hidden rounded-xl bg-slate-950 text-sm font-semibold text-white shadow-lg shadow-slate-900/15 transition-all duration-200 hover:-translate-y-0.5 hover:bg-blue-600 hover:shadow-xl hover:shadow-blue-600/20 disabled:translate-y-0 disabled:opacity-70"
      >
        <span className="relative z-10">
          {isLoading
            ? "Resetting password..."
            : "Reset password"}
        </span>
      </Button>

      {/* Back */}
      <div className="flex justify-center pt-0.5">
        <Link
          to="/auth/login"
          className="group inline-flex items-center gap-1.5 text-xs font-semibold text-slate-500 transition-colors hover:text-blue-600"
        >
          <ArrowLeft
            size={13}
            className="transition-transform duration-200 group-hover:-translate-x-0.5"
          />

          Back to sign in
        </Link>
      </div>
    </form>
  );
}

function PasswordInput({
  id,
  label,
  value,
  visible,
  onToggle,
  onChange,
  disabled,
}: {
  id: string;
  label: string;
  value: string;
  visible: boolean;
  onToggle: () => void;
  onChange: (value: string) => void;
  disabled: boolean;
}) {
  return (
    <div className="space-y-1.5">
      <Label
        htmlFor={id}
        className="text-xs font-semibold text-slate-700"
      >
        {label}
      </Label>

      <div className="relative">
        <LockKeyhole
          size={16}
          className="pointer-events-none absolute left-3 top-1/2 -translate-y-1/2 text-slate-400"
        />

        <Input
          id={id}
          type={visible ? "text" : "password"}
          autoComplete="new-password"
          placeholder="Enter your password"
          value={value}
          onChange={(event) =>
            onChange(event.target.value)
          }
          disabled={disabled}
          className="h-10 rounded-xl border-slate-200 bg-white pl-9 pr-10 text-sm shadow-sm transition-all placeholder:text-slate-400 focus:border-blue-500 focus:ring-2 focus:ring-blue-500/10"
        />

        <button
          type="button"
          onClick={onToggle}
          disabled={disabled}
          className="absolute right-3 top-1/2 -translate-y-1/2 rounded-md p-1 text-slate-400 transition-colors hover:text-slate-700 disabled:pointer-events-none disabled:opacity-50"
          aria-label={
            visible
              ? "Hide password"
              : "Show password"
          }
        >
          {visible ? (
            <EyeOff size={16} />
          ) : (
            <Eye size={16} />
          )}
        </button>
      </div>
    </div>
  );
}

export default ResetPasswordForm;