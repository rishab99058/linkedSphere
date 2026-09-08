import apiClient from "@/lib/axios";

export interface RegisterRequest {
  email: string;
  password: string;
  phoneNumber: string;
}

export async function registerUser(request: RegisterRequest) {
  return apiClient.post("/auth/api/v1/auth/register", request);
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
  expiresIn: number;
}

export async function loginUser(request: LoginRequest) {
  return apiClient.post<LoginResponse>(
    "/auth/api/v1/auth/login",
    request,
  );
}

export async function getUserProfile() {
  return apiClient.get("/auth/api/v1/profile/me");
}

export interface RefreshTokenRequest {
  refreshToken: string;
}

export async function refreshAccessToken(
  request: RefreshTokenRequest,
) {
  return apiClient.post<LoginResponse>(
    "/auth/api/v1/auth/refresh",
    request,
  );
}

export interface LogoutRequest {
  refreshToken: string;
}

export async function logoutUser(request: LogoutRequest) {
  return apiClient.post(
    "/auth/api/v1/auth/logout",
    request,
  );
}

export interface ForgotPasswordRequest {
  email: string;
}

export async function forgotPassword(
  request: ForgotPasswordRequest,
) {
  return apiClient.post(
    "/auth/api/v1/auth/forgot-password",
    request,
  );
}

export interface ResetPasswordRequest {
  email: string;
  otp: string;
  password: string;
}

export async function resetPassword(
  request: ResetPasswordRequest,
) {
  return apiClient.post(
    "/auth/api/v1/auth/reset-password",
    request,
  );
}