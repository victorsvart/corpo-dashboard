import { Field, Label, Input } from "@headlessui/react";
import clsx from "clsx";
import type { Route } from "./+types/username";
import { Form, redirect } from "react-router";

export async function clientAction({ request }: Route.ActionArgs) {
  const cookieHeader = request.headers.get("cookie") ?? "";
  try {
    const { username } = await request.formData().then((form) => {
      return { username: form.get("username") }
    });
    const response = await fetch(`http://localhost:8080/user/updateUsername?username=${username}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Cookie: cookieHeader
      },
      credentials: "include",
    });

    if (!response.ok)
      throw new Error(await response.json())

    return redirect("/settings")
  }
  catch (error) {
    console.error(error)
  }
}

export default function ChangeUsername() {
  return (
    <Form method="post" className="space-y-6">
      <Field>
        <Label className="block text-sm font-medium text-indigo-200 mb-1">
          New Username
        </Label>
        <Input
          name="username"
          placeholder="Enter new username"
          className={clsx(
            "block w-full rounded-md bg-stone-800 border border-stone-700 px-4 py-2 text-sm text-white placeholder-gray-500",
            "focus:border-indigo-500 focus:ring focus:ring-indigo-500 focus:ring-opacity-50 transition"
          )}
        />
      </Field>

      <div className="flex justify-end pt-6">
        <button
          type="submit"
          className="inline-flex rounded-md bg-indigo-600 hover:bg-indigo-500 px-4 py-2 text-sm font-semibold text-white shadow-md transition"
        >
          Change Username
        </button>
      </div>
    </Form>
  );
}
