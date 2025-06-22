import { Transition } from "@headlessui/react";
import { Fragment, useEffect } from "react";
import { XMarkIcon } from "@heroicons/react/24/solid";

interface AlertProps {
  isOpen: boolean;
  onClose: () => void;
  message: string;
  type?: "success" | "error" | "info" | "warning";
  autoCloseAfterMs?: number;
  position?: "top-right" | "bottom-center";
}

const typeStyles = {
  success: "bg-green-600 text-white",
  error: "bg-red-600 text-white",
  info: "bg-blue-600 text-white",
  warning: "bg-yellow-500 text-black",
};

export default function Alert({
  isOpen,
  onClose,
  message,
  type = "info",
  autoCloseAfterMs = 3000,
  position = "top-right",
}: AlertProps) {
  useEffect(() => {
    if (isOpen && autoCloseAfterMs > 0) {
      const timer = setTimeout(onClose, autoCloseAfterMs);
      return () => clearTimeout(timer);
    }
  }, [isOpen, autoCloseAfterMs, onClose]);

  const positionClasses =
    position === "top-right"
      ? "top-4 right-4"
      : "bottom-6 left-1/2 -translate-x-1/2";

  return (
    <div className="fixed z-50">
      <Transition
        show={isOpen}
        as={Fragment}
        enter="transform transition duration-300"
        enterFrom="opacity-0 translate-y-2"
        enterTo="opacity-100 translate-y-0"
        leave="transform transition duration-200"
        leaveFrom="opacity-100 translate-y-0"
        leaveTo="opacity-0 translate-y-2"
      >
        <div
          className={`absolute ${positionClasses} max-w-2xl w-fit rounded-xl shadow-xl px-6 py-4 flex items-center gap-4 ${typeStyles[type]}`}
        >
          <span className="text-sm font-medium">{message}</span>
          <button onClick={onClose} className="ml-2 hover:opacity-80">
            <XMarkIcon className="h-4 w-4" />
          </button>
        </div>
      </Transition>
    </div>
  );
}
