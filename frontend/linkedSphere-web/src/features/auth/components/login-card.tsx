import { Card, CardContent } from "@/components/ui/card";

import BrandSection from "./brand-section";
import LoginForm from "./login-form";
// import SocialLogin from "./login-social";
import LoginHeader from "./login-header";
import RegisterSection from "./register-section";

function LoginCard() {
  return (
    <Card className="relative w-full overflow-hidden rounded-3xl border border-white/60 bg-white/95 shadow-[0_25px_80px_rgba(2,6,23,0.28)] backdrop-blur-xl">
      {/* Subtle top accent */}
      <div className="absolute inset-x-0 top-0 h-px bg-gradient-to-r from-transparent via-blue-500/50 to-transparent" />

      <CardContent className="relative space-y-6 p-6 sm:p-7">
        <BrandSection />

        <LoginHeader />

        <LoginForm />

        {/* Social login can be enabled when Google auth UI is ready */}
        {/* <SocialLogin /> */}

        <RegisterSection />
      </CardContent>
    </Card>
  );
}

export default LoginCard;