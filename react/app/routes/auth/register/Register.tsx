import { data, Form, NavLink, redirect, useActionData } from "react-router";
import FloatingInput from "~/routes/components/FloatingInput";
import type { Route } from "./+types/Register";

export async function action({ request }: Route.ActionArgs) {
  const form = await request.formData();
  const username = form.get("username")?.toString().trim() ?? "";
  const password = form.get("password")?.toString().trim() ?? "";
  const firstName = form.get("firstName")?.toString().trim() ?? "";
  const lastName = form.get("lastName")?.toString().trim() ?? "";

  const errors: Record<string, string> = {};

  if (!username) errors.username = "Username is required";
  if (!password) errors.password = "Password is required";
  else if (password.length < 6) errors.password = "Password must be at least 6 characters";
  if (!firstName) errors.firstName = "First name is required";
  if (!lastName) errors.lastName = "Last name is required";

  if (Object.keys(errors).length > 0) {
    return data({ errors }, { status: 400 });
  }

  try {
    const cookieHeader = request.headers.get("cookie") ?? "";
    const response = await fetch("http://localhost:8080/user/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Cookie: cookieHeader,
      },
      body: JSON.stringify({ username, password, firstName, lastName }),
      credentials: "include",
    });

    if (!response.ok) {
      const body = await response.json();

      if (body.message?.includes("username")) {
        return data({ errors: { username: body.message } }, { status: 400 });
      }

      return data({ errors: { server: body.message || "Registration failed" } }, { status: 400 });
    }

    return redirect(`/auth/onlyAuth?username=${username}&password=${password}`);
  } catch (error: any) {
    return data({ errors: { server: "Something went wrong." } }, { status: 500 });
  }
}

type RegisterErrors = {
  username?: string;
  password?: string;
  firstName?: string;
  lastName?: string;
  server?: string;
};

export default function Register() {
  const actionData = useActionData() as { errors?: RegisterErrors };

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
          <FloatingInput name="username" labelText="Username" error={actionData?.errors?.username} />
          <FloatingInput name="password" labelText="Password" type="password" error={actionData?.errors?.password} />
          <FloatingInput name="firstName" labelText="First Name" error={actionData?.errors?.firstName} />
          <FloatingInput name="lastName" labelText="Last Name" error={actionData?.errors?.lastName} />
          {actionData?.errors?.server && (
            <p className="text-sm text-red-400 mt-2">{actionData.errors.server}</p>
          )}
          <button
            type="submit"
            className="w-full py-2 mt-5 rounded-xl bg-indigo-600 hover:bg-indigo-700 text-white font-semibold shadow-lg transition-colors duration-200"
          >
            Sign In
          </button>
        </Form>
        <div className="text-center pb-6 text-sm text-white/50">
          <span>Already have an account?</span>{" "}
          <NavLink key="signUp" to="/auth/login">
            <span className="text-indigo-400 hover:underline">
              Sign in
            </span>
          </NavLink>
        </div>
      </div>
    </div>
  );
}
