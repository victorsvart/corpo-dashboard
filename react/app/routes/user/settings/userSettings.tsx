import {
  Avatar,
  Box,
  Button,
  Card,
  Flex,
  Heading,
  Separator,
  Strong,
  Text,
  TextField,
} from "@radix-ui/themes";
import { data, redirect, useLoaderData } from "react-router";
import { useEffect, useState } from "react";
import { toData } from "~/helpers/toData";
import type { UserPresenter } from "~/types/user/user-presenter";
import type { Route } from "./+types/userSettings";
import { Form } from "react-router";
import { ChangeUsername } from "./changeUsername/changeUsername";

export async function clientLoader() {
  const response = await fetch("http://localhost:8080/user/me", {
    method: "GET",
    headers: { "Content-Type": "application/json" },
    credentials: "include",
  });
  return toData<UserPresenter>(response);
}

export async function clientAction({ request }: Route.ClientActionArgs) {
  const form = await request.formData();
  const firstName = form.get("firstName");
  const lastName = form.get("lastName");

  if (typeof firstName !== "string" || typeof lastName !== "string") {
    throw new Error("Invalid form input");
  }

  const response = await fetch("http://localhost:8080/user/update", {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    credentials: "include",
    body: JSON.stringify({ firstName: firstName, lastName: lastName }),
  });

  if (!response.ok) {
    const res = await response.json();
    return data(
      { error: res.message ?? "Internal server error" },
      { status: response.status }
    );
  }

  return redirect("/settings")
}

export default function UserSettings() {
  const user = useLoaderData() as UserPresenter;
  const [name, setName] = useState(user.name);
  const [lastName, setLastName] = useState(user.lastName);
  const [hasChanges, setHasChanges] = useState(false);
  const [isUsernameDialogOpen, setIsUsernameDialogOpen] = useState(false);

  useEffect(() => {
    setHasChanges(name !== user.name || lastName !== user.lastName);
  }, [name, lastName, user.name, user.lastName]);

  const handleUsernameChange = async (newUsername: string) => {
    const response = await fetch(`http://localhost:8080/user/updateUsername?username=${newUsername}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
    });

    if (!response.ok) {
      const res = await response.json();
      return data(
        { error: res.message ?? "internal server error" },
        { status: response.status }
      );
    }
    //TODO: THIS SHOULD UPDATE THE FUCKING USERNAME IN THE LAYOUT!!!
    //TODO: ALSO THERE SHOULD BE AN ALERT!!!!!!! FOR SUCCESS!!!
  };

  return (
    <>
      <Form method="post">
        <div className="h-screen px-3 py-4">
          <Flex direction="column" align="center" className="h-full">
            <Box width="100%" maxWidth="480px">
              <Card size="3" variant="surface">
                <Flex direction="column" gap="4">
                  <Flex align="center" gap="3">
                    <Avatar
                      src={user.profilePicture || undefined}
                      fallback={user.name.charAt(0).toUpperCase()}
                      size="4"
                      radius="full"
                    />
                    <Box>
                      <Heading size="4">{user.fullName}</Heading>
                      <Text size="2" color="gray">
                        @{user.username}
                      </Text>
                    </Box>
                  </Flex>
                  <Separator size="4" />
                  <Flex direction="column" gap="3">
                    <Box>
                      <Text as="label" size="2" htmlFor="firstName">
                        <Strong>First Name</Strong>
                      </Text>
                      <TextField.Root
                        id="firstName"
                        name="firstName"
                        placeholder="First name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        radius="large"
                        className="transition-colors duration-200 focus-within:ring-2 focus-within:ring-blue-500"
                      />
                    </Box>
                    <Box>
                      <Text as="label" size="2" htmlFor="lastName">
                        <Strong>Last Name</Strong>
                      </Text>
                      <TextField.Root
                        id="lastName"
                        name="lastName"
                        placeholder="Last name"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                        radius="large"
                        className="transition-colors duration-200 focus-within:ring-2 focus-within:ring-blue-500"
                      />
                    </Box>
                  </Flex>
                  <Flex direction="row" justify="between">
                    <Button
                      variant="outline"
                      size="2"
                      type="button"
                      onClick={() => setIsUsernameDialogOpen(true)}
                    >
                      Change username
                    </Button>
                    <Button
                      variant="solid"
                      size="2"
                      type="submit"
                      disabled={!hasChanges}
                    >
                      Save Changes
                    </Button>
                  </Flex>
                </Flex>
              </Card>
            </Box>
          </Flex>
        </div>
      </Form>

      <ChangeUsername
        userName={user.username}
        onUsernameChange={handleUsernameChange}
        open={isUsernameDialogOpen}
        onOpenChange={setIsUsernameDialogOpen}
      />
    </>
  );
}
