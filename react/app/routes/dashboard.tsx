import { NavLink, Outlet, redirect } from "react-router";
import type { Route } from "./+types/home";
import { useEffect, useState } from "react";
import clsx from "clsx";
import { ChartBarIcon, Cog6ToothIcon, HomeIcon } from "@heroicons/react/24/solid";

export async function loader({ request }: Route.LoaderArgs) {
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
  } catch (error) {
    throw redirect("/login");
  }
}


const navLinks = [
  { path: "/", icon: <HomeIcon className="size-5" />, text: "Home" },
  {
    path: "/team",
    icon: <ChartBarIcon className="size-5" />,
    text: "Team",
  },
  {
    path: "/settings",
    icon: <Cog6ToothIcon className="size-5" />,
    text: "Settings",
  },
];

export default function Dashboard() {
  const [width, setWidth] = useState<number | null>(null);
  const [menuVisible, setMenuVisible] = useState<boolean>(false);

  useEffect(() => {
    if (typeof window !== "undefined") {
      const initialWidth = window.innerWidth;
      setWidth(initialWidth);
      setMenuVisible(initialWidth > 768);

      function handleResize() {
        setWidth(window.innerWidth);
      }

      window.addEventListener("resize", handleResize);
      return () => window.removeEventListener("resize", handleResize);
    }
  }, []);

  const isMobile = width !== null && width <= 768;

  useEffect(() => {
    if (width !== null) setMenuVisible(!isMobile);
  }, [width]);

  const sidebarWidth = menuVisible ? "w-full md:w-64" : "w-0 md:w-16";

  async function logout() {
    await fetch("http://localhost:8080/user/logout", {
      headers: {
        "Content-Type": "application/json",
      },
      credentials: 'include'
    })

    redirect("/login")
  }

  return (
    <>
      <button
        hidden={menuVisible}
        onClick={() => setMenuVisible(true)}
        className="md:hidden fixed top-4 left-4 z-50 bg-indigo-600 text-black px-4 py-2 rounded-xl shadow-md hover:bg-indigo-300 transition"
      >
        ☰
      </button>

      {menuVisible && (
        <div
          className="fixed inset-0 z-30 bg-black/30 md:hidden"
          onClick={() => setMenuVisible(false)}
        />
      )}

      <aside
        className={clsx(
          "fixed top-0 left-0 h-full z-40 bg-white/4 backdrop-blur-md text-white shadow-xl transition-all duration-300 ease-in-out overflow-hidden",
          sidebarWidth,
          menuVisible ? "translate-x-0" : "-translate-x-full md:translate-x-0"
        )}
      >
        <div className="flex items-center mt-8 absolute top-0 right-4 z-50">
          <div
            className={clsx(
              "overflow-hidden transition-all duration-300 ease-in-out",
              menuVisible ? "w-auto opacity-100 ml-0" : "w-0 opacity-0 ml-0"
            )}
          >
            <code className="text-3xl tracking-wide block transition-opacity duration-300">
              CORPODASH
            </code>
          </div>
          <button
            onClick={() => setMenuVisible(!menuVisible)}
            className="bg-indigo-600 text-black w-7 h-7 ml-3 rounded-full shadow-md hover:bg-indigo-300 transition"
          >
            {menuVisible ? "←" : "→"}
          </button>
        </div>

        <nav
          className={clsx(
            "mt-24 px-4 flex flex-col gap-2 transform transition-all duration-100 ease-in-out",
            menuVisible
              ? "opacity-100 translate-x-0"
              : "opacity-0 -translate-x-4"
          )}
        >
          {navLinks.map(({ path, icon, text }) => (
            <NavLink
              key={path}
              to={path}
              className={({ isActive }) =>
                clsx(
                  "flex items-center gap-2 px-4 py-2 rounded-lg transition",
                  isActive ? "bg-white/10 font-semibold" : "hover:bg-white/20"
                )
              }
            >
              {icon}
              {menuVisible && <span className="text-white">{text}</span>}
            </NavLink>
          ))}
          <NavLink key="logout" to="login"></NavLink>
          <button
            onClick={logout}
            className="flex items-center gap-2 px-4 py-2 rounded-lg transition hover:bg-red-500 text-white mt-4"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={1.5}
              stroke="currentColor"
              className="w-5 h-5"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M15.75 9V5.25A2.25 2.25 0 0013.5 3H6.75A2.25 2.25 0 004.5 5.25v13.5A2.25 2.25 0 006.75 21h6.75a2.25 2.25 0 002.25-2.25V15M18 12H9m0 0l3-3m-3 3l3 3"
              />
            </svg>
            {menuVisible && <span className="text-white">Logout</span>}
          </button>
        </nav>
      </aside>

      <main
        className={clsx(
          "relative z-10 pt-20 px-6 transition-all duration-300 ease-in-out max-w-6xl mx-auto",
          "md:pl-20",
          menuVisible && "md:pl-72"
        )}
      >
        <Outlet />
      </main>
    </>
  );
}
