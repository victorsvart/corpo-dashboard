import { redirect } from 'react-router';
import type { Route } from './+types/OnlyAuth';

export async function clientLoader({ request }: Route.ClientLoaderArgs) {
  const url = new URL(request.url);
  const searchParams = url.searchParams;
  const username = searchParams.get("username");
  const password = searchParams.get("password");

  try {
    const res = await fetch("http://localhost:8080/user/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
      credentials: "include",
    });

    if (!res.ok) {
      throw new Error("Login failed");
    }

    return redirect("/?rsr=true");
  } catch (err) {
    console.error(err);
    return redirect("/auth/register?error=auth_failed");
  }
};

export default function OnlyAuth() {
  return (
    <div className="text-white min-h-screen">
      Authenticating...
    </div>
  );
}
