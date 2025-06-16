interface DefaultInputProps {
  name?: string;
  placeholder?: string;
  type?: string;
}

export default function DefaultInput({
  name = "search",
  placeholder = "Search...",
  type = "text",
}: DefaultInputProps) {
  return (
    <input
      type={type}
      name={name}
      placeholder={placeholder}
      autoComplete="off"
      aria-label={placeholder}
      className="w-full px-3 py-2 font-semibold placeholder-gray-400 text-white bg-gray-700 rounded-xl border border-gray-600 focus:outline-none focus:ring-2 focus:ring-blue-400 transition"
    />
  );
}
