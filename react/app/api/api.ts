export const api = async (
  path: string,
  options: RequestInit = {},
  clientRequest?: Request
): Promise<any> => {
  const base = import.meta.env.VITE_API_BASE_URL;

  const cookie = clientRequest ? clientRequest.headers.get("cookie") ?? "" : "";
  const headers = {
    "Content-Type": "application/json",
    ...(cookie ? { cookie } : {}),
    ...(options.headers ?? {}),
  };

  const fetchOptions: RequestInit = {
    ...options,
    headers,
    credentials: "include",
  };

  const response = await fetch(`${base}${path}`, fetchOptions);
  const text = await response.text();
  let data;
  try {
    data = text ? JSON.parse(text) : {};
  } catch (e) {
    console.error("Failed to parse response:", e);
    throw new Error("Failed to parse response JSON");
  }

  if (!response.ok) {
    console.error("API error:", {
      status: response.status,
      statusText: response.status === 403 ? "Forbidden" : response.status,
      data,
    });
    throw new Error(
      data?.message || response.statusText || "API request failed"
    );
  }

  return data;
};
