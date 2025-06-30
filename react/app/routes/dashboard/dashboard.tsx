import {
  Box,
  Card,
  Flex,
  Tabs,
  Text,
  Heading,
  Badge,
  Avatar,
  Button,
  Separator,
  Container,
  Grid,
  IconButton
} from "@radix-ui/themes";
import { data } from "react-router";
import { toData } from "~/helpers/toData";
import type { ProjectPresenter } from "~/types/project/project-presenter";
import UserProjects from "./userProjects/userProjects";
import { useLoaderData } from "react-router";

export async function clientLoader() {
  const response = await fetch("http://localhost:8080/project/getAll", {
    method: "GET",
    headers: {
      "Content-Type": "application/json"
    },
    credentials: "include"
  });

  if (!response.ok) {
    const res = await response.json();
    return data(
      { error: res.message ?? "Login failed" },
      { status: response.status }
    );
  }

  return toData<ProjectPresenter[]>(response);
}

export default function Dashboard() {
  const projectData = useLoaderData<ProjectPresenter[]>();

  return (
    <Container size="4" className="min-h-screen py-6">
      {/* Header Section */}
      <Box mb="6">
        <Flex justify="between" align="center" mb="4">
          <Box>
            <Heading size="8" weight="bold" mb="2">
              Dashboard
            </Heading>
            <Text size="4" color="gray">
              Welcome back! Here's what's happening with your projects.
            </Text>
          </Box>
        </Flex>

        <Separator size="4" />
      </Box>

      {/* Main Content */}
      <Card size="4" className="shadow-lg">
        <Tabs.Root defaultValue="projects" orientation="vertical">
          <Flex direction={{ initial: "column", lg: "row" }} gap="6">
            {/* Sidebar Navigation */}
            <Box className="lg:min-w-[200px]">
              <Tabs.List className="flex flex-row lg:flex-col w-full lg:w-auto gap-2">
                <Tabs.Trigger
                  value="projects"
                  className="flex-1 lg:flex-none lg:justify-start px-4 py-3 text-left"
                >
                  <Flex align="center" gap="2">
                    <Box className="w-2 h-2 bg-blue-500 rounded-full" />
                    <Text weight="medium">Your Projects</Text>
                  </Flex>
                </Tabs.Trigger>

                <Tabs.Trigger
                  value="documents"
                  className="flex-1 lg:flex-none lg:justify-start px-4 py-3 text-left"
                >
                  <Flex align="center" gap="2">
                    <Box className="w-2 h-2 bg-purple-500 rounded-full" />
                    <Text weight="medium">Documents</Text>
                  </Flex>
                </Tabs.Trigger>

                <Tabs.Trigger
                  value="settings"
                  className="flex-1 lg:flex-none lg:justify-start px-4 py-3 text-left"
                >
                  <Flex align="center" gap="2">
                    <Box className="w-2 h-2 bg-gray-500 rounded-full" />
                    <Text weight="medium">Settings</Text>
                  </Flex>
                </Tabs.Trigger>
              </Tabs.List>
            </Box>

            <Separator orientation="vertical" className="hidden lg:block" />

            {/* Content Area */}
            <Box className="flex-1 min-w-0">
              <Tabs.Content value="projects">
                <Box>
                  <Flex justify="between" align="center" mb="4">
                    <Heading size="6" weight="medium">
                      Your Projects
                    </Heading>
                    <Button size="2" variant="soft">
                      New Project
                    </Button>
                  </Flex>

                  <UserProjects projects={projectData} />
                </Box>
              </Tabs.Content>

              <Tabs.Content value="documents">
                <Box>
                  <Heading size="6" weight="medium" mb="4">
                    Documents
                  </Heading>

                  <Card variant="surface" className="p-8 text-center">
                    <Box className="w-16 h-16 bg-purple-100 rounded-full mx-auto mb-4 flex items-center justify-center">
                      <Box className="w-8 h-8 bg-purple-500 rounded" />
                    </Box>

                    <Heading size="4" mb="2">
                      Document Management
                    </Heading>

                    <Text size="3" color="gray" mb="4">
                      Access and update your documents. Keep all your important files organized and secure.
                    </Text>

                    <Button variant="outline" size="2">
                      Browse Documents
                    </Button>
                  </Card>
                </Box>
              </Tabs.Content>

              <Tabs.Content value="settings">
                <Box>
                  <Heading size="6" weight="medium" mb="4">
                    Settings
                  </Heading>

                  <Grid columns={{ initial: "1", md: "2" }} gap="4">
                    <Card variant="surface" className="p-6">
                      <Flex align="center" gap="3" mb="3">
                        <Box className="w-3 h-3 bg-green-500 rounded-full" />
                        <Heading size="4">Profile</Heading>
                      </Flex>

                      <Text size="2" color="gray" mb="4">
                        Edit your profile information and manage your account details.
                      </Text>

                      <Button variant="soft" size="2">
                        Edit Profile
                      </Button>
                    </Card>

                    <Card variant="surface" className="p-6">
                      <Flex align="center" gap="3" mb="3">
                        <Box className="w-3 h-3 bg-orange-500 rounded-full" />
                        <Heading size="4">Contact</Heading>
                      </Flex>

                      <Text size="2" color="gray" mb="4">
                        Update your contact information and communication preferences.
                      </Text>

                      <Button variant="soft" size="2">
                        Update Contact
                      </Button>
                    </Card>

                    <Card variant="surface" className="p-6">
                      <Flex align="center" gap="3" mb="3">
                        <Box className="w-3 h-3 bg-red-500 rounded-full" />
                        <Heading size="4">Security</Heading>
                      </Flex>

                      <Text size="2" color="gray" mb="4">
                        Manage your password, two-factor authentication, and security settings.
                      </Text>

                      <Button variant="soft" size="2">
                        Security Settings
                      </Button>
                    </Card>

                    <Card variant="surface" className="p-6">
                      <Flex align="center" gap="3" mb="3">
                        <Box className="w-3 h-3 bg-blue-500 rounded-full" />
                        <Heading size="4">Preferences</Heading>
                      </Flex>

                      <Text size="2" color="gray" mb="4">
                        Customize your dashboard experience and notification settings.
                      </Text>

                      <Button variant="soft" size="2">
                        Preferences
                      </Button>
                    </Card>
                  </Grid>
                </Box>
              </Tabs.Content>
            </Box>
          </Flex>
        </Tabs.Root>
      </Card>
    </Container>
  );
}
