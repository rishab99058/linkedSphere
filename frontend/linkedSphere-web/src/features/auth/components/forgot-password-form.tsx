import { useState } from "react";
import { ArrowLeft, Mail } from "lucide-react";
import { Link, useNavigate } from "react-router-dom";
import { z } from "zod";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

import { forgotPassword } from "../api/auth-api";

const schema = z.object({
  email: z
    .string()
    .min(1, "Email is required")
    .email("Enter a valid email address"),
});

function ForgotPasswordForm() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [error, setError] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  async function handleSubmit(
    event: React.FormEvent<HTMLFormElement>,
  ) {
    event.preventDefault();

    const result = schema.safeParse({ email });

    if (!result.success) {
      setError(result.error.issues[0].message);
      return;
    }

    setError("");
    setIsLoading(true);

    try {
      await forgotPassword({ email });

      navigate("/auth/reset-password", {
        state: {
          email,
        },
      });
    } catch (error: any) {
      setError(
        error?.response?.data?.message ??
          "Unable to process your request. Please try again.",
      );
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <form
      onSubmit={handleSubmit}
      className="space-y-4"
      noValidate
    >
      {/* Email */}
      <div className="space-y-1.5">
        <Label
          htmlFor="forgot-email"
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
            id="forgot-email"
            type="email"
            autoComplete="email"
            placeholder="you@example.com"
            value={email}
            onChange={(event) => {
              setEmail(event.target.value);
              setError("");
            }}
            disabled={isLoading}
            className={`h-11 rounded-xl border-slate-200 bg-white pl-10 text-sm shadow-sm transition-all placeholder:text-slate-400 focus:border-blue-500 focus:ring-2 focus:ring-blue-500/10 ${
              error
                ? "border-red-300 focus:border-red-400"
                : ""
            }`}
          />
        </div>

        {error && (
          <p className="text-xs font-medium text-destructive">
            {error}
          </p>
        )}
      </div>

      {/* Submit */}
      <Button
        type="submit"
        disabled={isLoading}
        className="group relative h-11 w-full overflow-hidden rounded-xl bg-slate-950 text-sm font-semibold text-white shadow-lg shadow-slate-900/15 transition-all duration-200 hover:-translate-y-0.5 hover:bg-blue-600 hover:shadow-xl hover:shadow-blue-600/20 disabled:translate-y-0 disabled:opacity-70"
      >
        <span className="relative z-10">
          {isLoading ? "Sending OTP..." : "Send OTP"}
        </span>
      </Button>

      {/* Back to login */}
      <div className="flex justify-center pt-1">
        <Link
          to="/auth/login"
          className="group inline-flex items-center gap-1.5 text-sm font-semibold text-slate-500 transition-colors hover:text-blue-600"
        >
          <ArrowLeft
            size={14}
            className="transition-transform duration-200 group-hover:-translate-x-0.5"
          />

          Back to sign in
        </Link>
      </div>
    </form>
  );
}

export default ForgotPasswordForm;