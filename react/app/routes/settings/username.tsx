import { Field, Label, Input, Button } from "@headlessui/react";
import clsx from "clsx";
import type { Route } from "./+types/username";
import { Form, redirect } from "react-router";

export async function action({ request }: Route.ActionArgs) {
  const form = await request.formData();
  const username = form.get("username");
  return redirect("/settings")
  // Fetch to your API to update username...

}

export default function ChangeUsername() {
  return (
    <Form method="post" className="space-y-6">
      <Field>
        <Label className="block text-sm font-medium text-indigo-200 mb-1">
          New Username
        </Label>
        <Input
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
