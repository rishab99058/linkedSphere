import { Card, CardContent } from "@/components/ui/card";

import BrandSection from "./brand-section";
import ResetPasswordForm from "./reset-password-form";

function ResetPasswordCard() {
  return (
    <Card className="relative w-full overflow-hidden rounded-3xl border border-white/60 bg-white/95 shadow-[0_25px_80px_rgba(2,6,23,0.28)] backdrop-blur-xl">
      {/* Subtle top accent */}
      <div className="absolute inset-x-0 top-0 h-px bg-gradient-to-r from-transparent via-blue-500/50 to-transparent" />

      <CardContent className="relative space-y-5 p-5 sm:p-6">
        <BrandSection />

        <div className="space-y-2.5">
          <div className="flex items-center gap-2">
            <span className="h-1.5 w-1.5 rounded-full bg-blue-600 shadow-[0_0_8px_rgba(37,99,235,0.55)]" />

            <span className="text-[11px] font-semibold uppercase tracking-[0.16em] text-blue-700">
              Account recovery
            </span>
          </div>

          <div>
            <h1 className="text-[1.8rem] font-bold leading-tight tracking-[-0.025em] text-slate-950 sm:text-3xl">
              Reset your password
            </h1>

            <p className="mt-2.5 max-w-sm text-sm leading-5.5 text-slate-500">
              Enter the verification code sent to your email and create a new
              password for your account.
            </p>
          </div>
        </div>

        <ResetPasswordForm />
      </CardContent>
    </Card>
  );
}

export default ResetPasswordCard;