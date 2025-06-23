import FloatingInput from "~/routes/components/FloatingInput";
import type { Route } from "./+types/Login";
import {
  Form,
  NavLink,
  redirect,
  useLoaderData,
} from "react-router";
import { useState } from "react";
import Alert from "~/routes/components/Alert";

export async function loader({ request }: Route.LoaderArgs) {
  const url = new URL(request.url);
  const errorParam = url.searchParams.get("error");

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
    if (response.ok) {
      return redirect("/");
    }
  } catch (error) {
    console.error(error);
  }

  let errorMessage = null;
  switch (errorParam) {
    case "auth_failed":
      errorMessage = "Authentication failed";
      break;
    case "session_expired":
      errorMessage = "Session expired";
      break;
  }

  return { errorMessage };
}

export async function clientAction({ request }: Route.ClientActionArgs) {
  const { username, password } = await request.formData().then((form) => ({
    username: String(form.get("username")),
    password: String(form.get("password")),
  }));

  try {
    await fetch("http://localhost:8080/user/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password }),
      credentials: "include",
    });
  } catch (error) {
    console.error(error);
  }
}

export default function Login() {
  const { errorMessage } = useLoaderData() as { errorMessage: string | null };
  const [alertOpen, setAlertOpen] = useState(Boolean(errorMessage));

  return (
    <div className="flex justify-center items-center min-h-screen bg-gradient-to-br from-gray-900 via-indigo-950 to-black px-4">
      <div className="w-full max-w-md rounded-3xl border border-white/20 bg-stone-900/40 shadow-2xl backdrop-blur-md">
        <div className="bg-gradient-to-r from-indigo-700 via-indigo-800 to-indigo-900 border-b-4 border-indigo-950 rounded-t-3xl px-6 py-5 flex items-center justify-center shadow-inner">
          <h1 className="text-3xl text-white tracking-widest drop-shadow-sm">
            <code>CORPODASH</code>
          </h1>
        </div>
        <div className="px-6 pt-6 text-center">
          <h2 className="text-white text-xl font-semibold tracking-wide">
            <code>Welcome</code>
          </h2>
          <p className="text-sm text-white/60 mt-1">
            <code>Please enter your credentials</code>
          </p>
        </div>

        <Form method="post" className="flex flex-col px-6 pb-8 gap-2">
          <FloatingInput name="username" labelText="Username" />
          <FloatingInput name="password" labelText="Password" type="password" />
          <button
            type="submit"
            className="w-full py-2 mt-5 rounded-xl bg-indigo-600 hover:bg-indigo-700 text-white font-semibold shadow-lg transition-colors duration-200"
          >
            Sign In
          </button>
        </Form>
        <div className="text-center pb-6 text-sm text-white/50">
          <span>Don't have an account?</span>{" "}
          <NavLink key="signUp" to="/auth/register">
            <span className="text-indigo-400 hover:underline">Sign up</span>
          </NavLink>
        </div>
      </div>
      <div className="fixed bottom-25 z-50">
        <Alert
          isOpen={alertOpen}
          onClose={() => setAlertOpen(false)}
          message={errorMessage ?? ""}
          type="error"
        />
      </div>
    </div>
  );
}
