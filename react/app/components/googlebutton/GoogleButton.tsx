import { FcGoogle } from "react-icons/fc";

export default function GoogleButton({ onClick }: { onClick?: () => void }) {
  return (
    <button
      onClick={onClick}
      className="flex items-center justify-center gap-3 w-full py-2 px-4 border border-gray-300 rounded-xl bg-white hover:bg-gray-300 transition"
    >
      <FcGoogle className="text-xl" />
      <span className="text-sm font-semibold text-gray-700">
        Continue with Google
      </span>
    </button>
  );
}
