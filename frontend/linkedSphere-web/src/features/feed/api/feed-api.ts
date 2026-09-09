import apiClient from "@/lib/axios";

export async function getFeed() {
  return apiClient.get("/feed/api/v1/posts");
}