
import { Field, Label, Input, Button } from "@headlessui/react";
import clsx from "clsx";
import type { Route } from "./+types/password";
import { data, Form, redirect, useActionData } from "react-router";

export interface PasswordChangeErrors {
  currentPassword?: string;
  newPassword?: string;
  confirmNewPassword?: string;
}

export async function clientAction({ request }: Route.ClientActionArgs) {
  const form = await request.formData();
  const currentPassword = form.get("currentPassword");
  const newPassword = form.get("newPassword");
  const confirmNewPassword = form.get("confirmNewPassword");

  const errors: PasswordChangeErrors = {};

  if (!currentPassword) {
    errors.currentPassword = "Current password is required.";
  }

  if (!newPassword) {
    errors.newPassword = "New password is required.";
  }

  if (newPassword !== confirmNewPassword) {
    errors.confirmNewPassword = "Passwords do not match.";
  }

  if (Object.keys(errors).length) {
    return data({ errors }, { status: 400 });
  }

  try {
    const response = await fetch("http://localhost:8080/user/changePassword", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: newPassword,
      credentials: 'include'
    });

    if (!response.ok)
      throw new Error(`${response.status} - ${response.statusText}`);

    return redirect("/settings");
  } catch (error) {
    console.error(error);
    return data(
      { errors: { general: "Unexpected error occurred. Please try again." } },
      { status: 500 }
    );
  }
}

export default function ChangePassword() {
  const actionData = useActionData();

  return (
    <Form method="post" className="space-y-6">
      <Field>
        <Label className="block text-sm font-medium text-indigo-200 mb-1">
          Current Password
        </Label>
        <Input
          type="password"
          name="currentPassword"
          placeholder="Current password"
          className={clsx(
            "block w-full rounded-md bg-stone-800 border border-stone-700 px-4 py-2 text-sm text-white placeholder-gray-500",
            "focus:border-indigo-500 focus:ring focus:ring-indigo-500 focus:ring-opacity-50 transition"
          )}
        />
        {actionData?.errors?.currentPassword && (
          <p className="text-red-500 text-xs mt-1">
            {actionData.errors.currentPassword}
          </p>
        )}
      </Field>

      <Field>
        <Label className="block text-sm font-medium text-indigo-200 mb-1">
          New Password
        </Label>
        <Input
          type="password"
          name="newPassword"
          placeholder="New password"
          className={clsx(
            "block w-full rounded-md bg-stone-800 border border-stone-700 px-4 py-2 text-sm text-white placeholder-gray-500",
            "focus:border-indigo-500 focus:ring focus:ring-indigo-500 focus:ring-opacity-50 transition"
          )}
        />
        {actionData?.errors?.newPassword && (
          <p className="text-red-500 text-xs mt-1">
            {actionData.errors.newPassword}
          </p>
        )}
      </Field>

      <Field>
        <Label className="block text-sm font-medium text-indigo-200 mb-1">
          Confirm New Password
        </Label>
        <Input
          type="password"
          name="confirmNewPassword"
          placeholder="Confirm new password"
          className={clsx(
            "block w-full rounded-md bg-stone-800 border border-stone-700 px-4 py-2 text-sm text-white placeholder-gray-500",
            "focus:border-indigo-500 focus:ring focus:ring-indigo-500 focus:ring-opacity-50 transition"
          )}
        />
        {actionData?.errors?.confirmNewPassword && (
          <p className="text-red-500 text-xs mt-1">
            {actionData.errors.confirmNewPassword}
          </p>
        )}
      </Field>

      <div className="pt-6 flex justify-end">
        <Button
          type="submit"
          className="inline-flex rounded-md bg-indigo-600 hover:bg-indigo-500 px-4 py-2 text-sm font-semibold text-white shadow-md transition"
        >
          Change Password
        </Button>
      </div>
    </Form>
  );
}
