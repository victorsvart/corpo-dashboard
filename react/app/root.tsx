import {
  isRouteErrorResponse,
  Links,
  Meta,
  Outlet,
  Scripts,
  ScrollRestoration,
} from "react-router";

import type { Route } from "./+types/root";
import "./app.css";
import { useState } from "react";
import { Button } from "@headlessui/react";
import clsx from "clsx";

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

export default function App() {
  const [menuVisible, setMenuVisible] = useState(false);

  const sidebarWidth = menuVisible ? "md:w-64" : "md:w-16";

  return (
    <>
      <button
        onClick={() => setMenuVisible(true)}
        className="md:hidden fixed top-4 left-4 z-50 bg-white text-black px-4 py-2 rounded-xl shadow-md hover:bg-gray-100 transition"
      >
        ☰
      </button>

      <aside
        className={clsx(
          "fixed top-0 left-0 h-full z-40 bg-white/5 backdrop-blur-sm text-white shadow-xl transition-all duration-300 ease-in-out",
          sidebarWidth,
          menuVisible ? "translate-x-0" : "-translate-x-full md:translate-x-0"
        )}
      >
        <button
          onClick={() => setMenuVisible(false)}
          className="md:hidden absolute top-4 left-4 bg-white text-black px-3 py-1 rounded shadow hover:bg-gray-200 transition"
        >
          ✕
        </button>

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
            className="bg-white text-black w-7 h-7 ml-3 rounded-full shadow-md hover:bg-gray-100 transition"
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
          <Button className="px-4 py-2 text-left text-white rounded-lg hover:bg-white/20 transition font-medium">
            Home
          </Button>
          <Button className="px-4 py-2 text-left text-white rounded-lg hover:bg-white/20 transition font-medium">
            Dashboard
          </Button>
          <Button className="px-4 py-2 text-left text-white rounded-lg hover:bg-white/20 transition font-medium">
            Settings
          </Button>
        </nav>
      </aside>

      {/* Main content area */}
      <main
        className={clsx(
          "relative z-10 pt-20 px-6 transition-all duration-300 ease-in-out max-w-6xl mx-auto",
          menuVisible ? "md:pl-72" : "md:pl-20"
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
