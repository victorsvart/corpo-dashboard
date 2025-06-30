import {
  LockClosedIcon,
  LockOpenIcon,
  PencilSquareIcon,
  UserIcon,
  ExclamationTriangleIcon,
} from "@heroicons/react/16/solid";

import {
  Box,
  Button,
  Card,
  Flex,
  Code,
  Spinner,
  TextField,
  Callout,
  Separator,
  Container,
  Text,
} from "@radix-ui/themes";

import { useEffect, useState } from "react";
import {
  Form,
  redirect,
  useActionData,
  useNavigation,
  data,
  NavLink,
  Link,
} from "react-router";

import type { Route } from "./+types/login";

export async function clientLoader() {
  const response = await fetch("http://localhost:8080/user/me", {
    method: "GET",
    headers: { "Content-Type": "application/json" },
    credentials: "include",
  });

  if (response.status !== 403) {
    return redirect("/home");
  }
}

export async function clientAction({ request }: Route.ClientActionArgs) {
  const form = await request.formData();
  const username = form.get("username");
  const password = form.get("password");

  if (typeof username !== "string" || typeof password !== "string") {
    throw new Error("Invalid form input");
  }

  const response = await fetch("http://localhost:8080/user/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    credentials: "include",
    body: JSON.stringify({ username, password }),
  });

  if (!response.ok) {
    const res = await response.json();
    return data(
      { error: res.message ?? "Login failed" },
      { status: response.status }
    );
  }

  return redirect("/dashboard");
}

export default function Login() {
  const actionData = useActionData<{ error?: string }>();
  const [error, setError] = useState<string | null>(null);
  const navigation = useNavigation();
  const loading = navigation.state === "submitting";

  useEffect(() => {
    if (actionData?.error) {
      setError(actionData.error);
    }
  }, [actionData]);

  return (
    <Form method="POST">
      <div className="h-screen w-screen bg-gradient-to-br from-gray-900 via-gray-950 to-black">
        <Flex
          direction="column"
          justify="center"
          align="center"
          gap="3"
          className="h-full w-full px-4"
        >
          <Box width="100%" maxWidth="420px">
            <Card className="shadow-lg p-4">
              <Flex direction="column" gap="4" align="center">
                <Container mb="3">
                  <Code size="7">CORPO_DASHBOARD</Code>
                </Container>

                <TextField.Root
                  name="username"
                  placeholder="Username"
                  radius="large"
                  className="transition-colors duration-200 focus-within:ring-2 focus-within:ring-blue-500"
                >
                  <TextField.Slot>
                    <UserIcon height="16" width="16" />
                  </TextField.Slot>
                </TextField.Root>

                <TextField.Root
                  name="password"
                  placeholder="Password"
                  type="password"
                  radius="large"
                  className="transition-colors duration-200 focus-within:ring-2 focus-within:ring-blue-500"
                >
                  <TextField.Slot>
                    <LockClosedIcon height="16" width="16" />
                  </TextField.Slot>
                </TextField.Root>

                <Flex direction="column" gap="2" className="w-full">
                  <Flex direction="row" gap="3" justify="center" className="w-full">
                    <Link to="/auth/signup">
                      <Button
                        disabled={loading}
                        variant="outline"
                        className="flex-1"
                      >
                        <Spinner loading={loading}>
                          <PencilSquareIcon width="16" height="16" />
                        </Spinner>
                        Sign Up
                      </Button>
                    </Link>

                    <Button
                      disabled={loading}
                      variant="solid"
                      type="submit"
                      className="flex-1"
                    >
                      <Spinner loading={loading}>
                        <LockOpenIcon width="16" height="16" />
                      </Spinner>
                      Sign in
                    </Button>
                  </Flex>

                  <Separator my="3" size="4" />

                  <Text size="2" color="gray" align="center">
                    Are you a recruiter? Dive right in below.
                  </Text>

                  <Button
                    disabled={loading}
                    variant="soft"
                    type="submit"
                    className="w-full"
                  >
                    <Spinner loading={loading}>
                      <LockOpenIcon width="16" height="16" />
                    </Spinner>
                    Sign in as recruiter
                  </Button>
                </Flex>

                {error && (
                  <Container mt="3" className="w-full">
                    <Callout.Root color="red" size="1">
                      <Callout.Icon>
                        <ExclamationTriangleIcon width="16" height="16" />
                      </Callout.Icon>
                      <Callout.Text>{error}</Callout.Text>
                    </Callout.Root>
                  </Container>
                )}
              </Flex>
            </Card>
          </Box>
        </Flex>
      </div>
    </Form>
  );
}
