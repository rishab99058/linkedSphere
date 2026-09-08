import { Card, CardContent } from "@/components/ui/card";

import BrandSection from "./brand-section";
import ForgotPasswordForm from "./forgot-password-form";

function ForgotPasswordCard() {
  return (
    <Card className="relative w-full overflow-hidden rounded-3xl border border-white/60 bg-white/95 shadow-[0_25px_80px_rgba(2,6,23,0.28)] backdrop-blur-xl">
      {/* Subtle top accent */}
      <div className="absolute inset-x-0 top-0 h-px bg-gradient-to-r from-transparent via-blue-500/50 to-transparent" />

      <CardContent className="relative space-y-6 p-6 sm:p-7">
        <BrandSection />

        <div className="space-y-3">
          <div className="flex items-center gap-2">
            <span className="h-1.5 w-1.5 rounded-full bg-blue-600 shadow-[0_0_8px_rgba(37,99,235,0.55)]" />

            <span className="text-xs font-semibold uppercase tracking-[0.16em] text-blue-700">
              Account recovery
            </span>
          </div>

          <div>
            <h1 className="text-[2rem] font-bold leading-tight tracking-[-0.025em] text-slate-950 sm:text-3xl">
              Forgot your password?
            </h1>

            <p className="mt-3 max-w-sm text-sm leading-6 text-slate-500">
              Enter your email address and we'll send you an OTP to securely
              reset your password.
            </p>
          </div>
        </div>

        <ForgotPasswordForm />
      </CardContent>
    </Card>
  );
}

export default ForgotPasswordCard;