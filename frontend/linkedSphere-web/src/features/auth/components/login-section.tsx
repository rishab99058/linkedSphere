import { Link } from "react-router-dom";

function LoginSection() {
  return (
    <div className="text-center text-sm">
      <span className="text-muted-foreground">
        Already have an account?{" "}
      </span>

      <Link
        to="/auth/login"
        className="font-medium text-primary hover:underline"
      >
        Sign in
      </Link>
    </div>
  );
}

export default LoginSection;