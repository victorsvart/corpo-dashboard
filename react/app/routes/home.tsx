import { api } from "~/api/api";
import type { Route } from "./+types/home";
import { redirect } from "react-router";

interface UserPresenter {
  id: number;
  username: string;
}

export async function loader({ request }: Route.LoaderArgs) {
  try {
    const response = await api("/user/me", { method: "GET" }, request);
    return response.data as UserPresenter;
  } catch (error) {
    return redirect("/login?redirect=true");
  }
}

export function meta({}: Route.MetaArgs) {
  return [
    { title: "New React Router App" },
    { name: "description", content: "Welcome to React Router!" },
  ];
}

export default function Home({ loaderData }: Route.ComponentProps) {
  const user = loaderData;
  return <h1>Welcome, {user.username}!</h1>;
}
