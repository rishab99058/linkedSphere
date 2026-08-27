import axios from "axios";
import { getAccessToken } from "./auth-storage";

const apiClient = axios.create({
  baseURL: "http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
});

apiClient.interceptors.request.use(
  (config) => {
    const accessToken = getAccessToken();

    const publicEndpoints = [
      "/auth/api/v1/auth/login",
      "/auth/api/v1/auth/register",
      "/auth/api/v1/auth/refresh",
    ];

    const isPublicEndpoint = publicEndpoints.some((endpoint) =>
      config.url?.includes(endpoint),
    );

    if (!isPublicEndpoint && accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }

    console.log(
      "Axios:",
      config.method?.toUpperCase(),
      config.url,
      "| Token attached:",
      !isPublicEndpoint && Boolean(accessToken),
    );

    return config;
  },
  (error) => Promise.reject(error),
);

export default apiClient;