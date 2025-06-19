import { type RouteConfig, index, layout, prefix, route } from "@react-router/dev/routes";

export default [
  index("routes/home.tsx"),
  route("/login", "routes/login/Login.tsx"),

  ...prefix("settings", [
    layout("routes/settings/Layout.tsx", [
      index("routes/settings/general.tsx"),
      route("username", "routes/settings/username.tsx"),
      route("password", "routes/settings/password.tsx"),
    ]),
  ])
] satisfies RouteConfig;
