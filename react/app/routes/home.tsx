import { Flex } from "@radix-ui/themes";
import { Outlet, redirect, useLoaderData } from "react-router";
import Navbar from "~/components/navbar";
import { toData } from "~/helpers/toData";
import type { UserPresenter } from "~/types/user/user-presenter";

export async function clientLoader() {
  const response = await fetch("http://localhost:8080/user/me", {
    method: "GET",
    headers: { "Content-Type": "application/json" },
    credentials: "include"
  });

  if (!response.ok)
    return redirect("/auth/signin?msg='Session expired!'");

  return toData<UserPresenter>(response);
}

export default function Home() {
  const userInfo = useLoaderData<UserPresenter>();
  return (
    <>
      <div className="h-screen">
        <Flex gap="3">
          <Navbar user={userInfo}></Navbar>
          <Outlet />
        </Flex>
      </div>
    </>
  )
}
