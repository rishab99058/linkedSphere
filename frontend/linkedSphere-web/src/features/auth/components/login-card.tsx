import { Card, CardContent } from "@/components/ui/card";

import BrandSection from "./brand-section";
import LoginHeader from "./login-header";
import LoginForm from "./login-form";
// import SocialLogin from "./login-social";
import RegisterSection from "./register-section";

function LoginCard() {
  return (
    <Card className="w-full border-slate-200/80 bg-white shadow-[0_20px_60px_-20px_rgba(15,23,42,0.18)]">
      <CardContent className="p-7 sm:p-8">
        <div className="space-y-7">
          <BrandSection />
          <LoginHeader />
          <LoginForm />
          {/* <SocialLogin /> */}
          <RegisterSection />
        </div>
      </CardContent>
    </Card>
  );
}

export default LoginCard;