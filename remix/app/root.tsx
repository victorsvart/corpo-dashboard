import {
  Links,
  Meta,
  Outlet,
  Scripts,
  ScrollRestoration,
} from "@remix-run/react";
import "./tailwind.css";

export const  loader = () => {
  
}

export default function App() {
  return <Outlet />;
}
