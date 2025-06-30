import { type RouteConfig, index, prefix, route } from "@react-router/dev/routes";

export default [
  ...prefix("auth", [
    route("signin", "routes/auth/login.tsx"),
    route("signup", "routes/auth/signup.tsx")
  ]),

  ...prefix("dashboard", [
    index("routes/home.tsx")
  ])
] satisfies RouteConfig;
