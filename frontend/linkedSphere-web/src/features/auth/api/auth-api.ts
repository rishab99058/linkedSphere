import apiClient from "@/lib/axios";

export interface RegisterRequest {
  email: string;
  password: string;
  phoneNumber: string;
}

export async function registerUser(request: RegisterRequest) {
  return apiClient.post(
    "/auth/api/v1/auth/register",
    request,
  );
}

export interface LoginRequest {
  email: string;
  password: string;
}

export async function loginUser(request: LoginRequest) {
  return apiClient.post(
    "/auth/api/v1/auth/login",
    request,
  );
}

export async function getUserProfile() {
  return apiClient.get(
    "/auth/api/v1/profile/me",
  );
}

export interface LogoutRequest {
  refreshToken: string;
}

export async function logoutUser(
  request: LogoutRequest,
) {
  return apiClient.post(
    "/auth/api/v1/auth/logout",
    request,
  );
}