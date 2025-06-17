import { Field, Label, Input } from "@headlessui/react";
import clsx from "clsx";
import { useState } from "react";

interface InputProps {
  name: string;
  type?: string;
  defaultValue?: string;
  readonly?: boolean;
  labelText: string;
  extraClass?: string;
  hasError?: boolean;
}

export default function FloatingInput({
  name,
  type = "text",
  defaultValue = "",
  readonly = false,
  labelText,
  extraClass,
  hasError = false,
}: InputProps) {
  const [localError, setLocalError] = useState(hasError);
  const [hasContent, setHasContent] = useState(defaultValue !== "");

  return (
    <Field className={clsx("relative w-full max-w-md pt-5", extraClass)}>
      <Input
        name={name}
        type={type}
        placeholder=" "
        defaultValue={defaultValue}
        readOnly={readonly}
        onChange={(e) => {
          setLocalError(false);
          setHasContent(e.target.value !== "");
        }}
        className={clsx(
          "peer block w-full rounded-lg bg-white/5 px-3 py-2 text-sm text-white",
          localError
            ? "ring-2 ring-red-500"
            : "focus:outline-none focus:ring-2 focus:ring-indigo-500"
        )}
      />
      <Label
        htmlFor={name}
        className={clsx(
          "pointer-events-none absolute left-2 bottom-7 transition-all duration-200 text-xs text-white",
          hasContent
            ? "top-0 translate-y-0 text-indigo-300"
            : "top-1/2 -translate-y-1/2 text-sm text-white/50",
          "peer-focus:top-0 peer-focus:translate-y-0 peer-focus:text-xs peer-focus:text-indigo-300"
        )}
      >
        {labelText}
      </Label>
    </Field>
  );
}
