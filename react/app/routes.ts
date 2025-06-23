import { type RouteConfig, index, layout, prefix, route } from "@react-router/dev/routes";

export default [
  ...prefix("/auth", [
    route("/login", "routes/auth/login/Login.tsx"),
    route("/register", "routes/auth/register/Register.tsx"),
    route("/onlyAuth", "routes/auth/OnlyAuth.tsx"),
  ]),

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
