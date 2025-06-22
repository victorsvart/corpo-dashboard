import { type RouteConfig, index, layout, prefix, route } from "@react-router/dev/routes";
import { replace } from "react-router";

export default [
  // route("/", <Navigate replace to = "/dashboard/home" />),
  route("/login", "routes/login/Login.tsx"),

  layout("routes/dashboard.tsx", [
    index("routes/home/home.tsx"),
    route("team", "routes/teams/team.tsx"),
    ...prefix("settings", [
      layout("routes/settings/Layout.tsx", [
        index("routes/settings/general.tsx"),
        route("username", "routes/settings/username.tsx"),
        route("password", "routes/settings/password.tsx"),
      ]),
    ])
  ]),
] satisfies RouteConfig;
