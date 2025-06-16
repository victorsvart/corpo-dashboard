import { redirect } from "react-router";

export async function loader() {
  // Here you would typically handle the logout logic
  // For example, clearing cookies, session, etc.
  
  // Redirect to login page
  return redirect("/login");
}

export default function Logout() {
  return (
    <div className="flex items-center justify-center min-h-screen">
      <div className="text-center">
        <h1 className="text-2xl font-bold mb-4">Logging out...</h1>
        <p className="text-gray-600">Please wait while we log you out.</p>
      </div>
    </div>
  );
} 