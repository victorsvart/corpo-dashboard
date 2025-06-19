
import { Field, Label, Input, Button } from "@headlessui/react";
import clsx from "clsx";
import type { Route } from "./+types/password";
import { Form, redirect } from "react-router";

export async function action({ request }: Route.ActionArgs) {
  const form = await request.formData();
  const currentPassword = form.get("currentPassword");
  const newPassword = form.get("newPassword");
  const confirmNewPassword = form.get("confirmNewPassword");
  return redirect("/settings")
}

export default function ChangePassword() {
  return (
    <Form method="post" className="space-y-6">
      <Field>
        <Label className="block text-sm font-medium text-indigo-200 mb-1">
          Current Password
        </Label>
        <Input
          type="password"
          placeholder="Current password"
          className={clsx(
            "block w-full rounded-md bg-stone-800 border border-stone-700 px-4 py-2 text-sm text-white placeholder-gray-500",
            "focus:border-indigo-500 focus:ring focus:ring-indigo-500 focus:ring-opacity-50 transition"
          )}
        />
      </Field>

      <Field>
        <Label className="block text-sm font-medium text-indigo-200 mb-1">
          New Password
        </Label>
        <Input
          type="password"
          placeholder="New password"
          className={clsx(
            "block w-full rounded-md bg-stone-800 border border-stone-700 px-4 py-2 text-sm text-white placeholder-gray-500",
            "focus:border-indigo-500 focus:ring focus:ring-indigo-500 focus:ring-opacity-50 transition"
          )}
        />
      </Field>

      <Field>
        <Label className="block text-sm font-medium text-indigo-200 mb-1">
          Confirm New Password
        </Label>
        <Input
          type="password"
          placeholder="Confirm new password"
          className={clsx(
            "block w-full rounded-md bg-stone-800 border border-stone-700 px-4 py-2 text-sm text-white placeholder-gray-500",
            "focus:border-indigo-500 focus:ring focus:ring-indigo-500 focus:ring-opacity-50 transition"
          )}
        />
      </Field>

      <div className="pt-6 flex justify-end">
        <button
          type="submit"
          className="inline-flex rounded-md bg-indigo-600 hover:bg-indigo-500 px-4 py-2 text-sm font-semibold text-white shadow-md transition"
        >
          Change Password
        </button>
      </div>
    </Form>
  );
}
