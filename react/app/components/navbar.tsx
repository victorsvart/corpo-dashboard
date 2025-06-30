import * as DropdownMenu from "@radix-ui/react-dropdown-menu";
import { BellIcon, Bars3Icon, XMarkIcon } from "@heroicons/react/24/outline";
import { useState } from "react";
import { NavLink } from "react-router";
import { Avatar, Flex } from "@radix-ui/themes";

const links = [
  { to: "/dashboard", name: "Dashboard" },
  { to: "/team", name: "Team" },
  { to: "/projects", name: "Projects" },
  { to: "/servers", name: "Servers" },
]


export default function Navbar() {
  const [menuOpen, setMenuOpen] = useState(false);

  return (
    <nav className="w-screen bg-gray-900">
      <div className="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
        <div className="relative flex h-16 items-center justify-between">
          {/* Mobile menu button */}
          <div className="absolute inset-y-0 left-0 flex items-center sm:hidden">
            <button
              type="button"
              className="inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-gray-700 hover:text-white focus:outline-none focus:ring-2 focus:ring-white"
              aria-controls="mobile-menu"
              aria-expanded={menuOpen}
              onClick={() => setMenuOpen(!menuOpen)}
            >
              {menuOpen ? (
                <XMarkIcon className="h-6 w-6" aria-hidden="true" />
              ) : (
                <Bars3Icon className="h-6 w-6" aria-hidden="true" />
              )}
              <span className="sr-only">Toggle menu</span>
            </button>
          </div>

          {/* Logo + Nav Links */}
          <div className="flex flex-1 items-center justify-center sm:items-stretch sm:justify-start">
            <div className="flex shrink-0 items-center">
              <img
                className="h-8 w-auto"
                src="https://tailwindcss.com/plus-assets/img/logos/mark.svg?color=indigo&shade=500"
                alt="Your Company"
              />
            </div>
            <div className="hidden sm:ml-6 sm:block">
              <div className="flex space-x-4">
                {links.map((item, idx) => (
                  <NavLink
                    key={item.name}
                    to={item.to}
                    className={({ isActive, isPending }) =>
                      [
                        "px-3 py-2 rounded-md text-sm font-medium transition-colors",
                        isActive
                          ? "bg-gray-900 text-white"
                          : isPending
                            ? "bg-gray-200 text-gray-800"
                            : "text-gray-300 hover:bg-gray-700 hover:text-white",
                      ].join(" ")
                    }
                    aria-current={idx === 0 ? "page" : undefined}
                  >
                    {item.name}
                  </NavLink>
                ))}
              </div>
            </div>
          </div>

          {/* Notification + Profile */}
          <div className="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">
            <button
              type="button"
              className="rounded-full bg-gray-800 p-1 text-gray-400 hover:text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800"
            >
              <BellIcon className="h-6 w-6" />
              <span className="sr-only">View notifications</span>
            </button>

            {/* Profile Dropdown */}
            <DropdownMenu.Root>
              <DropdownMenu.Trigger asChild>
                <button className="ml-3 rounded-full bg-gray-800 text-sm focus:outline-none">
                  <Flex>
                    <Avatar src="https://images.unsplash.com/photo-1502823403499-6ccfcf4fb453?&w=256&h=256&q=70&crop=focalpoint&fp-x=0.5&fp-y=0.3&fp-z=1&fit=crop"
                      fallback="A">
                    </Avatar>
                  </Flex>
                  <span className="sr-only">Open user menu</span>
                </button>
              </DropdownMenu.Trigger>

              <DropdownMenu.Portal>
                <DropdownMenu.Content
                  className="z-50 mt-2 w-48 origin-top-right rounded-md bg-gray-800 py-1 shadow-lg ring-1 ring-black/5"
                  align="end"
                >
                  <DropdownMenu.Item
                    className="block px-4 py-2 text-sm text-gray-300 transition hover:bg-gray-700"
                  >
                    Your Profile
                  </DropdownMenu.Item>
                  <DropdownMenu.Item
                    className="block px-4 py-2 text-sm text-gray-300 transition hover:bg-gray-700"
                  >
                    Settings
                  </DropdownMenu.Item>
                  <DropdownMenu.Item
                    className="block px-4 py-2 text-sm text-gray-300 transition hover:bg-gray-700"
                  >
                    Sign out
                  </DropdownMenu.Item>
                </DropdownMenu.Content>
              </DropdownMenu.Portal>
            </DropdownMenu.Root>
          </div>
        </div>
      </div>

      {/* Mobile Menu */}
      {
        menuOpen && (
          <div className="sm:hidden" id="mobile-menu">
            <div className="space-y-1 px-2 pt-2 pb-3">
              {["Dashboard", "Team", "Projects", "Calendar"].map((item, idx) => (
                <a
                  key={item}
                  href="#"
                  className={`block rounded-md px-3 py-2 text-base font-medium ${idx === 0
                    ? "bg-gray-900 text-white"
                    : "text-gray-300 hover:bg-gray-700 hover:text-white"
                    }`}
                  aria-current={idx === 0 ? "page" : undefined}
                >
                  {item}
                </a>
              ))}
            </div>
          </div>
        )
      }
    </nav >
  );
}
