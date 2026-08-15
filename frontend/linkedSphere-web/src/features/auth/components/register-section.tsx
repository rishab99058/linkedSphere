import { Link } from "react-router-dom";

function RegisterSection() {
  return (
    <div className="text-center text-sm">
      <span className="text-muted-foreground">
        New to LinkedSphere?{" "}
      </span>

      <Link
        to="/auth/register"
        className="font-medium text-primary hover:underline"
      >
        Create an account
      </Link>
    </div>
  );
}

export default RegisterSection;