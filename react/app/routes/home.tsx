import { data } from "react-router";
import type { Route } from "./+types/home";

export async function loader() {
  return data({
    stats: [
      { label: "Total Users", value: "1,249" },
      { label: "Active Projects", value: "45" },
      { label: "Revenue", value: "$12,350" },
    ],
  });
}

export default function Home({ loaderData }: Route.ComponentProps) {
  const { stats } = loaderData;
  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold">Dashboard Overview</h1>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        {stats.map((stat) => (
          <div className="p-6 bg-gray-800  rounded-lg shadow">
            <h3 className="text-gray-300 text-sm">{stat.label}</h3>
            <p className="text-2xl font-bold">{stat.value}</p>
          </div>
        ))}
      </div>

      <div className="p-6 rounded-lg shadow">
        <h2 className="text-xl font-semibold mb-4">Recent Activity</h2>
        <div className="space-y-4">
          <div className="flex items-center justify-between">
            <div>
              <p className="font-medium">New user registration</p>
              <p className="text-sm text-gray-500">2 minutes ago</p>
            </div>
            <span className="text-green-500">+1</span>
          </div>
          <div className="flex items-center justify-between">
            <div>
              <p className="font-medium">Project update</p>
              <p className="text-sm text-gray-500">1 hour ago</p>
            </div>
            <span className="text-blue-500">Updated</span>
          </div>
        </div>
      </div>
    </div>
  );
}
