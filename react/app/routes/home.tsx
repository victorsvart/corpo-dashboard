import { Flex } from "@radix-ui/themes";
import { Outlet, redirect } from "react-router";
import Navbar from "~/components/navbar";

export async function clientLoader() {
  const response = await fetch("http://localhost:8080/user/me", {
    method: "GET",
    headers: { "Content-Type": "application/json" },
    credentials: "include"
  });

  if (!response.ok)
    return redirect("/auth/signin?msg='Session expired!'");
}

export default function Home() {
  return (
    <>
      <div className="h-screen">
        <Flex gap="3">
          <Navbar></Navbar>
          <Outlet />
        </Flex>
      </div>
    </>
  )
}
