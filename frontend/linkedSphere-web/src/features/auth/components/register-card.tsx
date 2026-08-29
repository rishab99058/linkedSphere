import {
  Card,
  CardContent,
} from "@/components/ui/card";

import BrandSection from "./brand-section";
import RegisterHeader from "./register-header";
import RegisterForm from "./register-form";
import SocialRegister from "./social-register";
import LoginSection from "./login-section";

function RegisterCard() {
  return (
    <Card className="w-full max-w-md shadow-xl">
      <CardContent className="space-y-8 p-8">
        <BrandSection />
        <RegisterHeader />
        <RegisterForm />
        <SocialRegister />
        <LoginSection />
      </CardContent>
    </Card>
  );
}

export default RegisterCard;