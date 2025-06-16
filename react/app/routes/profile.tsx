import { api } from "~/api/api";
import type { Route } from "./+types/profile";
import { redirect } from "react-router";

interface UserPresenter {
  id: number;
  username: string;
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
  const { id, username } = loaderData;
  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold">Profile</h1>
      <div className="p-6 rounded-lg shadow">
        <div className="flex items-center space-x-4 mb-6">
          <div className="w-20 h-20 bg-gray-200 rounded-full flex items-center justify-center">
            <span className="text-2xl">👤</span>
          </div>
          <div>
            <h2 className="text-xl font-semibold">{username}</h2>
          </div>
        </div>

        <div className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Full Name
            </label>
            <input
              type="text"
              defaultValue="John Doe"
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">
              Email
            </label>
            <input
              type="email"
              defaultValue="john.doe@example.com"
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">
              Bio
            </label>
            <textarea
              rows={4}
              defaultValue="Software engineer with 5 years of experience in web development."
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
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
