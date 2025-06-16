interface DefaultInputProps {
  name?: string;
  placeholder?: string;
  type?: string;
  readonly?: boolean;
  defaultValue?: string;
  label?: string | null;
}

export default function DefaultInput({
  name = "search",
  placeholder = "Search...",
  type = "text",
  readonly = false,
  defaultValue = "",
  label = null,
}: DefaultInputProps) {
  const baseClasses =
    "w-full mt-2 px-3 py-2 font-semibold placeholder-gray-400 text-white rounded-xl border border-gray-600 focus:outline-none";

  const readOnlyClasses = "bg-gray-900";
  const editableClasses =
    "bg-gray-600 focus:ring-2 focus:ring-blue-400 transition";

  return (
    <div>
      {label && (
        <label
          htmlFor={name}
          className="block text-sm font-medium text-gray-400"
        >
          {label}
        </label>
      )}
      <input
        id={name}
        type={type}
        name={name}
        placeholder={placeholder}
        readOnly={readonly}
        defaultValue={defaultValue}
        autoComplete="off"
        aria-label={placeholder}
        className={`${baseClasses} ${
          readonly ? readOnlyClasses : editableClasses
        }`}
      />
    </div>
  );
}
