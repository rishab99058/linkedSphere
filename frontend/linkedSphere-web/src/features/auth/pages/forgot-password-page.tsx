import AuthLayout from "../components/auth-layout";
import ForgotPasswordForm from "../components/forgot-password-form";

function ForgotPasswordPage() {
  return (
    <AuthLayout>
      <div className="w-full max-w-md">
        <ForgotPasswordForm />
      </div>
    </AuthLayout>
  );
}

export default ForgotPasswordPage;