import { api } from "~/api/api";
import type { Route } from "./+types/profile";
import { Form, redirect, useActionData } from "react-router";
import DefaultInput from "~/components/defaultinput/DefaultInput";
import { useState, useEffect } from "react";
import PopUp, { PopType } from "~/components/popup/PopUp";

interface UserPresenter {
  id: number;
  username: string;
  fullName: string;
  profilePicture: string;
}

export async function loader({ request }: Route.LoaderArgs) {
  try {
    const response = await api("/user/me", { method: "GET" }, request);
    return response.data as UserPresenter;
  } catch {
    return redirect("/login");
  }
}
export async function clientAction({ request }: Route.ClientActionArgs) {
  const formData = await request.formData();
  const username = formData.get("username") as string;
  const fullName = formData.get("fullName") as string;
  // const bio = formData.get("bio") as string;

  const [firstName, ...rest] = fullName.trim().split(" ");
  const lastName = rest.join(" ");

  try {
    await api(
      "/user/update",
      {
        method: "PUT",
        body: JSON.stringify({ username, firstName, lastName }),
      },
      request
    );
    return { success: true };
  } catch (err) {
    console.error("Update failed:", err);
    return { success: false, error: "Failed to update profile." };
  }
}

export default function Profile({ loaderData }: Route.ComponentProps) {
  const actionData = useActionData() as { success?: boolean; error?: string };
  const [showSuccessPopup, setShowSuccessPopup] = useState(false);

  useEffect(() => {
    if (actionData?.success) {
      setShowSuccessPopup(true);
      const timer = setTimeout(() => setShowSuccessPopup(false), 3500);
      return () => clearTimeout(timer);
    }
  }, [actionData?.success]);

  return (
    <>
      {showSuccessPopup && (
        <PopUp
          message="Profile updated successfully!"
          type={PopType.SUCCESS}
          position="bottom-center"
        />
      )}

      <div className="space-y-6">
        <h1 className="text-2xl font-bold">Profile</h1>
        <div className="bg-gray-900 p-6 rounded-lg shadow">
          <div className="flex items-center space-x-4 mb-6">
            <div className="w-20 h-20 bg-gray-200 rounded-full overflow-hidden flex items-center justify-center">
              {loaderData.profilePicture ? (
                <img
                  src={loaderData.profilePicture}
                  alt="Profile"
                  className="object-cover w-full h-full"
                />
              ) : (
                <span className="text-2xl">👤</span>
              )}
            </div>
            <div>
              <h2 className="text-xl font-semibold">@{loaderData.username}</h2>
            </div>
          </div>

          <Form method="post" className="space-y-4">
            <DefaultInput
              name="username"
              placeholder="Username"
              type="text"
              defaultValue={loaderData.username}
              label="Username"
            />

            <DefaultInput
              name="fullName"
              placeholder="Full Name"
              type="text"
              defaultValue={loaderData.fullName}
              label="Full Name"
            />

            <div>
              <label className="block text-sm font-medium text-gray-700">
                Bio
              </label>
              <textarea
                name="bio"
                rows={4}
                defaultValue="Software engineer with 5 years of experience in web development."
                className="w-full mt-1 rounded-xl border-2 border-dotted border-blue-400 bg-gray-900 p-3 text-sm text-white placeholder-gray-400 shadow-inner focus:border-blue-500 focus:ring-2 focus:ring-blue-600 outline-none transition-all"
              />
            </div>

            {actionData?.error && (
              <p className="text-red-400">{actionData.error}</p>
            )}

            <div className="pt-4">
              <button
                type="submit"
                className="px-4 py-2 rounded-md text-white bg-blue-500 hover:bg-blue-600 transition-colors"
              >
                Save Changes
              </button>
            </div>
          </Form>
        </div>
      </div>
    </>
  );
}
