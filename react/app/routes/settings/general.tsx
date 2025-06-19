import { Form, useLoaderData, redirect } from "react-router";
import {
  Field,
  Label,
  Input,
  Button,
  Transition,
  Dialog,
  TransitionChild,
  DialogPanel,
  DialogTitle,
} from "@headlessui/react";
import clsx from "clsx";
import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import type { Route } from "./+types";

interface UserPresenter {
  id: number;
  username: string;
  name: string;
  lastName: string;
  fullName: string;
  profilePicture: string;
}

export const loader = async ({ request }: Route.LoaderArgs) => {
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

    const json = await response.json();
    return json.data as UserPresenter;
  } catch {
    throw redirect("/login");
  }
};

export async function action({ request }: Route.ActionArgs) {
  const cookieHeader = request.headers.get("cookie") ?? "";
  const form = await request.formData();
  const firstName = form.get("firstName");
  const lastName = form.get("lastName");

  try {
    await fetch("http://localhost:8080/user/update", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Cookie: cookieHeader,
      },
      body: JSON.stringify({ firstName, lastName }),
      credentials: "include",
    });

    return redirect("/settings");
  } catch (error) {
    console.error(error);
  }
}

export default function GeneralSettings() {
  const user = useLoaderData<typeof loader>();
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  return (
    <>
      <div className="flex items-center gap-6">
        <img src={user.profilePicture} alt="Profile" className="h-20 w-20 rounded-full object-cover" />
        <div>
          <h1 className="text-xl font-bold">@{user.username}</h1>
          <Button className="mt-1 inline-flex items-center gap-2 rounded-md bg-indigo-700 hover:bg-indigo-600 px-4 py-1.5 text-sm font-medium text-white shadow transition duration-200" onClick={() => setIsDialogOpen(true)}>Change Profile Picture</Button>
        </div>
      </div>

      <Form method="post" className="space-y-6">
        <Field>
          <Label className="block text-sm font-medium text-indigo-200 mb-1">First Name</Label>
          <Input
            name="firstName"
            defaultValue={user.name}
            className={clsx(
              "block w-full rounded-md bg-stone-800 border border-stone-700 px-4 py-2 text-sm text-white",
              "focus:border-indigo-500 focus:ring focus:ring-indigo-500 focus:ring-opacity-50 transition"
            )}
          />
        </Field>

        <Field>
          <Label className="block text-sm font-medium text-indigo-200 mb-1">Last Name</Label>
          <Input
            name="lastName"
            defaultValue={user.lastName}
            className={clsx(
              "block w-full rounded-md bg-stone-800 border border-stone-700 px-4 py-2 text-sm text-white",
              "focus:border-indigo-500 focus:ring focus:ring-indigo-500 focus:ring-opacity-50 transition"
            )}
          />
        </Field>

        <div className="pt-6 flex justify-end">
          <button
            type="submit"
            className="inline-flex items-center justify-center rounded-md bg-indigo-600 hover:bg-indigo-500 px-4 py-2 text-sm font-semibold text-white shadow-md transition"
          >
            Save Changes
          </button>
        </div>
      </Form>

      <Transition show={isDialogOpen}>
        <Dialog as="div" className="relative z-10" onClose={() => setIsDialogOpen(false)}>
          <div className="fixed inset-0 z-10">
            <TransitionChild
              enter="ease-out duration-200"
              enterFrom="opacity-0"
              enterTo="opacity-100"
              leave="ease-in duration-150"
              leaveFrom="opacity-100"
              leaveTo="opacity-0"
            >
              <div className="fixed inset-0 bg-black/40 backdrop-blur-sm" />
            </TransitionChild>

            <div className="fixed inset-0 flex items-center justify-center p-6">
              <TransitionChild
                enter="ease-out duration-200"
                enterFrom="opacity-0 scale-95"
                enterTo="opacity-100 scale-100"
                leave="ease-in duration-150"
                leaveFrom="opacity-100 scale-100"
                leaveTo="opacity-0 scale-95"
              >
                <DialogPanel className="w-full max-w-2xl rounded-2xl bg-stone-900 p-8 border border-stone-700 shadow-xl">
                  <DialogTitle className="text-2xl font-bold text-indigo-400 mb-4">
                    Change Profile Picture
                  </DialogTitle>

                  <p className="text-sm text-stone-400 mb-6">
                    Paste the URL of your new profile picture. This feature is not yet functional.
                  </p>

                  <div className="space-y-4">
                    <Field>
                      <Label className="block text-sm font-medium text-indigo-200 mb-1">Image URL</Label>
                      <Input
                        type="url"
                        placeholder="https://example.com/profile.jpg"
                        className={clsx(
                          "block w-full rounded-md bg-stone-800 border border-stone-700 px-4 py-2 text-sm text-white",
                          "focus:border-indigo-500 focus:ring focus:ring-indigo-500 focus:ring-opacity-50 transition"
                        )}
                      />
                    </Field>

                    <div className="flex justify-end gap-2 pt-4">
                      <Button
                        className="rounded-md bg-gray-700 px-4 py-2 text-sm font-semibold text-white hover:bg-gray-600 transition"
                        onClick={() => setIsDialogOpen(false)}
                      >
                        Cancel
                      </Button>
                      <Button
                        className="rounded-md bg-indigo-600 px-4 py-2 text-sm font-semibold text-white hover:bg-indigo-500 transition"
                        onClick={() => setIsDialogOpen(false)}
                      >
                        Save Image
                      </Button>
                    </div>
                  </div>
                </DialogPanel>
              </TransitionChild>
            </div>
          </div>
        </Dialog>
      </Transition>
    </>
  );
}
