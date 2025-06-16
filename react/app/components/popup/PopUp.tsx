export enum PopType {
  WARN = "warn",
  ERROR = "error",
  SUCCESS = "success",
}

type PopUpPosition =
  | "top-left"
  | "top-right"
  | "bottom-left"
  | "bottom-right"
  | "top-center"
  | "bottom-center";

interface PopUpProps {
  message?: string;
  type?: PopType;
  position?: PopUpPosition;
}

const positionClasses: Record<PopUpPosition, string> = {
  "top-left": "top-4 left-4",
  "top-right": "top-4 right-4",
  "bottom-left": "bottom-4 left-4",
  "bottom-right": "bottom-4 right-4",
  "top-center": "top-4 left-1/2 -translate-x-1/2",
  "bottom-center": "bottom-4 left-1/2 -translate-x-1/2",
};

const typeClasses: Record<PopType, string> = {
  [PopType.WARN]: "bg-yellow-100 border-yellow-400 text-yellow-800",
  [PopType.ERROR]: "bg-red-100 border-red-400 text-red-800",
  [PopType.SUCCESS]: "bg-green-100 border-green-400 text-green-800",
};

export default function PopUp({
  message = "default message",
  type = PopType.ERROR,
  position = "top-right",
}: PopUpProps) {
  return (
    <div
      className={`
        fixed z-50 px-4 py-3 rounded-md shadow-lg border
        ${positionClasses[position]} ${typeClasses[type]}
        transition-opacity duration-300 ease-in-out
      `}
    >
      {message}
    </div>
  );
}
