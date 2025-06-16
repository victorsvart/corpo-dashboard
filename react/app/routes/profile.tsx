import { api } from "~/api/api";
import type { Route } from "./+types/profile";
import { redirect } from "react-router";
import DefaultInput from "~/components/defaultinput/DefaultInput";

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
  const { id, username, fullName, profilePicture } = loaderData;
  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold">Profile</h1>
      <div className="p-6 rounded-lg shadow">
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
          <div>
            <DefaultInput
              name="username"
              placeholder="Username"
              type="text"
              defaultValue={username}
              readonly={true}
              label={"Username"}
            />
          </div>

          <div>
            <DefaultInput
              name="name"
              placeholder="Name"
              type="text"
              defaultValue={fullName}
              readonly={true}
              label={"Full Name"}
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">
              Bio
            </label>
            <textarea
              rows={4}
              defaultValue="Software engineer with 5 years of experience in web development."
              className="bg-gray-900 mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
            />
          </div>

          <div className="pt-4">
            <button className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600">
              Save Changes
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
