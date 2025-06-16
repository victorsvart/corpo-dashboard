import { api } from "~/api/api";
import type { Route } from "./+types/profile";
import { redirect } from "react-router";
import DefaultInput from "~/components/defaultinput/DefaultInput";
import { useState } from "react";

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

export default function Profile({ loaderData }: Route.ComponentProps) {
  const [canSave, setCanSave] = useState(false);
  const { id, username, fullName, profilePicture } = loaderData;

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold">Profile</h1>
      <div className="bg-gray-900 p-6 rounded-lg shadow">
        <div className="flex items-center space-x-4 mb-6">
          <div className="w-20 h-20 bg-gray-200 rounded-full overflow-hidden flex items-center justify-center">
            {profilePicture ? (
              <img
                src={profilePicture}
                alt="Profile"
                className="object-cover w-full h-full"
              />
            ) : (
              <span className="text-2xl">👤</span>
            )}
          </div>
          <div>
            <h2 className="text-xl font-semibold">@{username}</h2>
          </div>
        </div>

        <div className="space-y-4">
          <DefaultInput
            name="username"
            placeholder="Username"
            type="text"
            defaultValue={username}
            readonly={true}
            label="Username"
          />

          <DefaultInput
            name="name"
            placeholder="Name"
            type="text"
            defaultValue={fullName}
            readonly={true}
            label="Full Name"
          />

          <div>
            <label className="block text-sm font-medium text-gray-700">
              Bio
            </label>
            <textarea
              rows={4}
              defaultValue="Software engineer with 5 years of experience in web development."
              className="w-full mt-1 rounded-xl border-2 border-dotted border-blue-400 bg-gray-900 p-3 text-sm text-white placeholder-gray-400 shadow-inner focus:border-blue-500 focus:ring-2 focus:ring-blue-600 outline-none transition-all"
              onChange={() => setCanSave(true)}
            />
          </div>

          <div className="pt-4">
            <button
              className={`px-4 py-2 rounded-md text-white transition-colors ${
                canSave
                  ? "bg-blue-500 hover:bg-blue-600"
                  : "bg-gray-600 cursor-not-allowed"
              }`}
              disabled={!canSave}
            >
              Save Changes
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
