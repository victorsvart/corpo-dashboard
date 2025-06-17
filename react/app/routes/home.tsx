import { redirect } from "react-router";
import type { Route } from "./+types/home";

export async function loader({ request }: Route.LoaderArgs) {
  const cookieHeader = request.headers.get("cookie") ?? "";
  try {
    const response = await fetch("http://localhost:8080/user/me", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Cookie: cookieHeader,
      },
      credentials: "include",
    });

    if (!response.ok)
      throw new Error(`${response.status} - ${response.statusText}`);
  } catch (error) {
    throw redirect("/login");
  }
}

export default function Home({ loaderData }: Route.ComponentProps) {
  return (
    <div>
      <h1 className="text-white">home</h1>
    </div>
  );
}
