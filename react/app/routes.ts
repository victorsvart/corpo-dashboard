import { type RouteConfig, index, layout, prefix, route } from "@react-router/dev/routes";

export default [
  ...prefix("auth", [
    route("signin", "routes/auth/login.tsx"),
    route("signup", "routes/auth/signup.tsx")
  ]),


  layout("routes/home.tsx", [
    ...prefix("dashboard", [
      index("routes/dashboard/dashboard.tsx"),

    ]),

    ...prefix("settings", [
      index("routes/user/settings/userSettings.tsx")
    ]),
  ])
] satisfies RouteConfig;
