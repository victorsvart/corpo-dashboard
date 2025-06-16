import { data, redirect, useFetcher, useSearchParams } from "react-router";
import { api } from "~/api/api";
import type { Route } from "./+types/login";
import DefaultInput from "~/components/defaultinput/DefaultInput";
import GoogleButton from "~/components/googlebutton/GoogleButton";
import PopUp, { PopType } from "~/components/popup/PopUp";

export async function loader({ request }: Route.LoaderArgs) {
  try {
    await api("/user/me", { method: "GET" }, request);
    return redirect("/");
  } catch (error) {
    return null;
  }
}

export async function clientAction({ request }: Route.ClientActionArgs) {
  const formData = await request.formData();
  const username = String(formData.get("username"));
  const password = String(formData.get("password"));
  const errors: Record<string, string> = {};

  if (!username) errors.username = "Username is required";
  if (!password) errors.password = "Password is required";

  if (Object.keys(errors).length > 0) {
    return data({ errors }, { status: 400 });
  }

  try {
    await api("/user/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    });
    return redirect("/");
  } catch (error: any) {
    errors.general = error.message || "Invalid username or password";
    return data({ errors }, { status: 400 });
  }
}

export default function Login(_: Route.ComponentProps) {
  const [searchParams] = useSearchParams();
  const redirectParam = searchParams.get("redirect");
  const isRedirect = redirectParam === "true";
  const fetcher = useFetcher();
  const errors = fetcher.data?.errors;

  return (
    <div className="flex min-h-screen items-center justify-center bg-gray-900 px-4">
      <div className="flex flex-wrap lg:flex-nowrap items-center gap-8 p-8 rounded-2xl shadow-lg bg-gray-800 w-full max-w-4xl">
        <div className="flex flex-col items-center flex-1 min-w-[250px]">
          <h1 className="text-white font-bold text-2xl mb-4">LOGIN</h1>
          <fetcher.Form method="post" className="flex flex-col gap-4 w-full">
            <DefaultInput name="username" placeholder="Username" type="text" />
            {errors?.username && (
              <em className="text-red-400 text-sm">{errors.username}</em>
            )}
            <DefaultInput
              name="password"
              placeholder="Password"
              type="password"
            />
            {errors?.password && (
              <em className="text-red-400 text-sm">{errors.password}</em>
            )}
            <button
              disabled={fetcher.state !== "idle"}
              type="submit"
              className="mt-2 w-full transition py-2 bg-blue-500 hover:bg-blue-600 text-white font-semibold rounded-xl"
            >
              {fetcher.state === "submitting" ? "Signing in..." : "Sign In"}
            </button>
          </fetcher.Form>

          <div className="my-4 w-full border-t border-gray-600"></div>

          <GoogleButton />
        </div>

        <div className="hidden lg:block h-[250px] w-0.5 bg-white/10"></div>

        <div className="flex flex-col items-center flex-1 min-w-[250px]">
          <h1 className="text-white font-bold text-2xl mb-4 text-center">
            Don't have an account?
          </h1>
          <button
            type="button"
            className="mt-2 w-full py-2 bg-blue-500 hover:bg-blue-600 text-white font-semibold rounded-xl transition"
          >
            Sign Up
          </button>
        </div>
        {isRedirect && (
          <PopUp
            message="Session expired"
            type={PopType.ERROR}
            position="bottom-center"
          />
        )}

        {errors?.general && (
          <PopUp
            message={errors.general}
            type={PopType.ERROR}
            position="bottom-center"
          />
        )}
      </div>
    </div>
  );
}
