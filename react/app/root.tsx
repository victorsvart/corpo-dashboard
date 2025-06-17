import {
  isRouteErrorResponse,
  Links,
  Meta,
  NavLink,
  Outlet,
  Scripts,
  ScrollRestoration,
} from "react-router";

import type { Route } from "./+types/root";
import "./app.css";
import { useState } from "react";
import { Button } from "@headlessui/react";
import clsx from "clsx";
import {
  ChartBarIcon,
  Cog6ToothIcon,
  HomeIcon,
} from "@heroicons/react/24/solid";

export const links: Route.LinksFunction = () => [
  { rel: "preconnect", href: "https://fonts.googleapis.com" },
  {
    rel: "preconnect",
    href: "https://fonts.gstatic.com",
    crossOrigin: "anonymous",
  },
  {
    rel: "stylesheet",
    href: "https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap",
  },
];

export function Layout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en" className="font-sans">
      <head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <Meta />
        <Links />
      </head>
      <body className="bg-gray-50 text-gray-900">
        {children}
        <ScrollRestoration />
        <Scripts />
      </body>
    </html>
  );
}

const navLinks = [
  { path: "/", icon: <HomeIcon className="size-5" />, text: "Home" },
  {
    path: "/dashboard",
    icon: <ChartBarIcon className="size-5" />,
    text: "Dashboard",
  },
  {
    path: "/settings",
    icon: <Cog6ToothIcon className="size-5" />,
    text: "Settings",
  },
];

export default function App() {
  const [menuVisible, setMenuVisible] = useState(false);
  const sidebarWidth = menuVisible ? "w-full md:w-64" : "w-0 md:w-16";

  return (
    <>
      <button
        hidden={menuVisible}
        onClick={() => setMenuVisible(true)}
        className="md:hidden fixed top-4 left-4 z-50 bg-white text-black px-4 py-2 rounded-xl shadow-md hover:bg-gray-100 transition"
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
          "fixed top-0 left-0 h-full z-40 bg-white/5 backdrop-blur-sm text-white shadow-xl transition-all duration-300 ease-in-out overflow-hidden",
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
            className="bg-white text-black w-7 h-7 ml-3 rounded-full shadow-md hover:bg-gray-300 transition"
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
              onClick={() => setMenuVisible(false)}
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

export function ErrorBoundary({ error }: Route.ErrorBoundaryProps) {
  let message = "Oops!";
  let details = "An unexpected error occurred.";
  let stack: string | undefined;

  if (isRouteErrorResponse(error)) {
    message = error.status === 404 ? "404" : "Error";
    details =
      error.status === 404
        ? "The requested page could not be found."
        : error.statusText || details;
  } else if (import.meta.env.DEV && error && error instanceof Error) {
    details = error.message;
    stack = error.stack;
  }

  return (
    <main className="pt-16 p-4 container mx-auto">
      <h1 className="text-2xl font-bold mb-2">{message}</h1>
      <p className="mb-4">{details}</p>
      {stack && (
        <pre className="w-full p-4 overflow-x-auto bg-gray-100 rounded">
          <code>{stack}</code>
        </pre>
      )}
    </main>
  );
}
