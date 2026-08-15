import {
  Card,
  CardContent,
} from "@/components/ui/card";

import BrandSection from "./brand-section";
import LoginHeader from "./login-header";
import LoginForm from "./login-form";
import SocialLogin from "./social-login";
import RegisterSection from "./register-section";

function LoginCard() {
  return (
    <Card className="w-full max-w-md shadow-xl">
      <CardContent className="space-y-8 p-8">
        <BrandSection />
        <LoginHeader />
        <LoginForm />
        <SocialLogin />
        <RegisterSection />
      </CardContent>
    </Card>
  );
}

export default LoginCard;