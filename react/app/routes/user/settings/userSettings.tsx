import { Box, Card, Flex, Strong, Text } from "@radix-ui/themes";
import { useLoaderData } from "react-router";
import { toData } from "~/helpers/toData";
import type { UserPresenter } from "~/types/user/user-presenter";

export async function clientLoader() {
  const response = await fetch("http://localhost:8080/user/me", {
    method: "GET",
    headers: { "Content-Type": "application/json" },
    credentials: "include"
  });

  return toData<UserPresenter>(response)
}

export default function UserSettings() {
  const userInfo = useLoaderData<UserPresenter>();
  return (
    <div className="h-screen">
      <Flex direction="column" justify="center" align="center">
        <Box width="100%" maxWidth="420px">
          <Card size="2">
            <Text as="p" size="3">
              <Strong>Typography</Strong>
            </Text>
          </Card>
        </Box>
      </Flex>
    </div>
  )
}
