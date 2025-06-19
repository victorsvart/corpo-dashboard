import clsx from "clsx";
import { NavLink, Outlet, useLocation } from "react-router";
import { AnimatePresence, motion } from "framer-motion";

const tabs = [
  { to: "settings", label: "General Settings" },
  { to: "settings/username", label: "Change Username" },
  { to: "settings/password", label: "Change Password" },
];

const pageVariants = {
  initial: { opacity: 0, x: 20 },
  animate: { opacity: 1, x: 0 },
  exit: { opacity: 0, x: -20 },
};

export default function SettingsLayout() {
  const location = useLocation();

  return (
    <div className="min-h-screen text-white py-12 px-6">
      <div className="flex justify-center mb-6">
        {tabs.map(({ to, label }) => (
          <NavLink
            key={to}
            to={to}
            end={to === "settings"}
            className={({ isActive }) =>
              clsx(
                "rounded-full px-4 py-2 text-sm font-semibold text-white transition",
                isActive ? "bg-indigo-600" : "hover:bg-white/10"
              )
            }
          >
            {label}
          </NavLink>
        ))}
      </div>

      <div className="relative min-h-screen">
        <AnimatePresence mode="wait">
          <motion.div
            key={location.pathname}
            variants={pageVariants}
            initial="initial"
            animate="animate"
            exit="exit"
            transition={{ duration: 0.2 }}
            className="absolute top-0 left-0 w-full bg-white/4 backdrop-blur-sm rounded-2xl shadow-lg p-8 space-y-8 border border-stone-800"
          >
            <Outlet />
          </motion.div>
        </AnimatePresence>
      </div>
    </div>
  );
}
