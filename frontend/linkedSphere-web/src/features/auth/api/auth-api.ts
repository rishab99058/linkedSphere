import apiClient from "@/lib/axios";

export interface RegisterRequest {
  email: string;
  password: string;
  phoneNumber: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LogoutRequest {
  refreshToken: string;
}

export interface ForgotPasswordRequest {
  email: string;
}

export interface ResetPasswordRequest {
  email: string;
  otp: string;
  password: string;
}

// export interface GoogleLoginRequest {
//   // Isko Social Login API ke baad exact karenge.
// }


// Register API
export async function registerUser(request: RegisterRequest) {
  return apiClient.post(
    "/auth/api/v1/auth/register",
    request,
  );
}

// Login API
export async function loginUser(request: LoginRequest) {
  return apiClient.post(
    "/auth/api/v1/auth/login",
    request,
  );
}

// Get User Profile API
export async function getUserProfile() {
  return apiClient.get(
    "/auth/api/v1/profile/me",
  );
}

// Logout API
export async function logoutUser(
  request: LogoutRequest,
) {
  return apiClient.post(
    "/auth/api/v1/auth/logout",
    request,
  );
}

// Forgot Password API
export async function forgotPassword(
  request: ForgotPasswordRequest
) {
  return apiClient.post(
    "/auth/api/v1/auth/forgot-password",
    request
  );
}

// Reset Password API
export async function resetPassword(
  request: ResetPasswordRequest
) {
  return apiClient.post(
    "/auth/api/v1/auth/reset-password",
    request
  );
}