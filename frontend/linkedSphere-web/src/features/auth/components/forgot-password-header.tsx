function ForgotPasswordHeader() {
  return (
    <div className="space-y-2 text-center">
      <h1 className="text-2xl font-bold">
        Forgot Password?
      </h1>

      <p className="text-sm text-slate-500">
        Enter your registered email and we'll send you an OTP
        to reset your password.
      </p>
    </div>
  );
}

export default ForgotPasswordHeader;