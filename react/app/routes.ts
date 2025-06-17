import { type RouteConfig, index, route } from "@react-router/dev/routes";

export default [
  index("routes/home.tsx"),
  route("/login", "routes/login/Login.tsx"),
  route("/settings", "routes/settings/Settings.tsx"),
] satisfies RouteConfig;
