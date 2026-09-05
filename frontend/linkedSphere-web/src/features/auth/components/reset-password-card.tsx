import { Card, CardContent } from "@/components/ui/card";
import ResetPasswordHeader from "./reset-password-header";
import ResetPasswordForm from "./reset-password-form";

function ResetPasswordCard() {
  return (
    <Card className="w-full max-w-md shadow-xl">
      <CardContent className="space-y-8 p-8">
        <ResetPasswordHeader />
        <ResetPasswordForm />
      </CardContent>
    </Card>
  );
}

export default ResetPasswordCard;