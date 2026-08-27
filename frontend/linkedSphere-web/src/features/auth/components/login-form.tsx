import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import { useState } from "react";
import { z } from "zod";
import { Eye, EyeOff } from "lucide-react";
import { setAccessToken, removeAccessToken, setRefreshToken } from "@/lib/auth-storage";
import { loginUser, getUserProfile } from "../api/auth-api";
import { useNavigate } from "react-router-dom";




const loginSchema = z.object({
  email: z
    .string()
    .trim()
    .min(1, "Email is required")
    .email("Enter a valid email"),

  password: z
    .string()
    .min(1, "Password is required"),
});

type LoginFormValues = z.infer<typeof loginSchema>;
type LoginFormErrors = Partial<
  Record<keyof LoginFormValues, string>
>;

function LoginForm() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState<LoginFormValues>({
    email: "",
    password: "",
  });


  const [errors, setErrors] = useState<LoginFormErrors>({});
  const [isLoading, setIsLoading] = useState(false);
  const [apiError, setApiError] = useState("");
  const [isSuccess, setIsSuccess] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  function handleChange(field: keyof LoginFormValues, value: string) {
    setFormData((previousData) => ({
      ...previousData,
      [field]: value,
    }));
    setErrors((previousErrors) => ({
      ...previousErrors,
      [field]: undefined,
    }));
    setApiError("");
    setIsSuccess(false);
  }

async function handleSubmit(
  event: React.FormEvent<HTMLFormElement>,
) {
  event.preventDefault();

  setApiError("");
  setIsSuccess(false);

  const result = loginSchema.safeParse(formData);

  if (!result.success) {
    const fieldErrors = result.error.flatten().fieldErrors;

    const nextErrors: LoginFormErrors = {};

    (Object.keys(formData) as Array<keyof LoginFormValues>).forEach(
      (field) => {
        const message = fieldErrors[field]?.[0];

        if (message) {
          nextErrors[field] = message;
        }
      },
    );

    setErrors(nextErrors);
    return;
  }

  setErrors({});
  setIsLoading(true);
  removeAccessToken();

  try {
    //Login
    const loginResponse = await loginUser({
      email: result.data.email,
      password: result.data.password,
    });

    console.log(
      "Login status:",
      loginResponse.status,
    );

    console.log(
      "Login response:",
      loginResponse.data,
    );

    //Get token
    const accessToken =
      loginResponse.data?.accessToken;

    const refreshToken =
      loginResponse.data?.refreshToken;

    if (!accessToken) {
      setApiError(
        "Login succeeded but access token was not returned.",
      );
      return;
    }

    //Store token
    setAccessToken(accessToken);

    if (refreshToken) {
      setRefreshToken(refreshToken);
    }

    //Call protected API
    try {
      const profileResponse =
        await getUserProfile();

      console.log(
        "Profile status:",
        profileResponse.status,
      );

      console.log(
        "Profile response:",
        profileResponse.data,
      );

      navigate("/home", {
        replace: true,
      });

      setIsSuccess(true);

    } catch (profileError) {
      console.error(
        "Profile request failed:",
        profileError,
      );

      setApiError(
        "Login succeeded, but profile could not be loaded.",
      );
    }

  } catch (loginError) {
    console.error(
      "Login failed:",
      loginError,
    );

    setApiError(
      "Invalid email or password. Please try again.",
    );
  } finally {
    setIsLoading(false);
  }
}


 return (
    <form onSubmit={handleSubmit} className="space-y-5">
      {/* EMAIL */}
      <div className="space-y-2">
        <Label htmlFor="email">
          Email
        </Label>

        <Input
          id="email"
          name="email"
          type="email"
          placeholder="Enter your email"
          autoComplete="email"
          value={formData.email}
          onChange={(event) =>
            handleChange("email", event.target.value)
          }
          aria-invalid={Boolean(errors.email)}
        />

        {errors.email ? (
          <p className="text-sm text-red-500">
            {errors.email}
          </p>
        ) : null}
      </div>

      {/* PASSWORD */}
      <div className="space-y-2">
        <Label htmlFor="password">
          Password
        </Label>

        <div className="relative">
          <Input
            id="password"
            name="password"
            type={showPassword ? "text" : "password"}
            placeholder="Enter your password"
            autoComplete="current-password"
            value={formData.password}
            onChange={(event) =>
              handleChange("password", event.target.value)
            }
            aria-invalid={Boolean(errors.password)}
            className="pr-10"
          />

          {formData.password.length > 0 && (
            <button
              type="button"
              onClick={() =>
                setShowPassword((previous) => !previous)
              }
              className="absolute right-3 top-1/2 -translate-y-1/2 text-slate-500 hover:text-slate-700"
              aria-label={
                showPassword
                  ? "Hide password"
                  : "Show password"
              }
            >
              {showPassword ? (
                <EyeOff className="h-4 w-4" />
              ) : (
                <Eye className="h-4 w-4" />
              )}
            </button>
          )}
        </div>

        {errors.password ? (
          <p className="text-sm text-red-500">
            {errors.password}
          </p>
        ) : null}
      </div>

      {/* API ERROR */}
      {apiError ? (
        <p className="text-center text-sm text-red-500">
          {apiError}
        </p>
      ) : null}

      {/* SUCCESS */}
      {isSuccess ? (
        <p className="text-center text-sm font-medium text-green-600">
          Login successful!
        </p>
      ) : null}

      {/* LOGIN BUTTON */}
      <Button
        type="submit"
        className="w-full"
        disabled={isLoading}
      >
        {isLoading ? "Signing in..." : "Login"}
      </Button>
    </form>
  );
}

export default LoginForm;