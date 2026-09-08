import axios from "axios";

import { getAccessToken } from "./auth-storage";

const apiClient = axios.create({
  baseURL: "https://linksphere-gateway-service-190262577212.asia-south2.run.app",
  headers: {
    "Content-Type": "application/json",
  },
});

const PUBLIC_ENDPOINTS = [
  "/auth/api/v1/auth/login",
  "/auth/api/v1/auth/register",
  "/auth/api/v1/auth/refresh",
  "/auth/api/v1/auth/forgot-password",
  "/auth/api/v1/auth/reset-password",
  "/auth/api/v1/auth/google",
];

apiClient.interceptors.request.use(
  (config) => {
    const requestUrl = config.url ?? "";

    const isPublicEndpoint = PUBLIC_ENDPOINTS.some((endpoint) =>
      requestUrl.includes(endpoint),
    );

    if (!isPublicEndpoint) {
      const accessToken = getAccessToken();

      if (accessToken) {
        config.headers.Authorization = `Bearer ${accessToken}`;
      }
    }

    return config;
  },
  (error) => Promise.reject(error),
);

export default apiClient;