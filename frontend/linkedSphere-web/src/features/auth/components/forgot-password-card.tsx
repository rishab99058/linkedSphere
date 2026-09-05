import { Card, CardContent } from "@/components/ui/card";
import ForgotPasswordHeader from "./forgot-password-header";
import ForgotPasswordForm from "./forgot-password-form";

function ForgotPasswordCard() {
  return (
    <Card className="w-full max-w-md shadow-xl">
      <CardContent className="space-y-8 p-8">
        <ForgotPasswordHeader />
        <ForgotPasswordForm />
      </CardContent>
    </Card>
  );
}

export default ForgotPasswordCard;