import type { ProjectPresenter } from "~/types/project/project-presenter";
import type { ServerPresenter } from "~/types/server/server-presenter";
import { useState } from "react";
import { ChevronDownIcon } from "@heroicons/react/24/outline";
import {
  Dialog,
  Flex,
  Text,
  Card,
  Button,
  Separator,
  Badge,
  Box,
  Avatar,
} from "@radix-ui/themes";

interface ProjectDetailsProps {
  project: ProjectPresenter;
  open: boolean;
  onOpenChange: (open: boolean) => void;
}

const mockTeamMembers = [
  {
    id: "1",
    name: "Victor Moraes",
    role: "Engineering",
    avatar:
      "https://preview.redd.it/death-stranding-avatar-v0-xko8w25g6hoc1.jpeg?width=640&crop=smart&auto=webp&s=3a7c4abb973043fda1a7de52861c18d1c71c6e24",
  },
  {
    id: "2",
    name: "Nathalia Severo",
    role: "Product Manager",
    avatar:
      "https://preview.redd.it/death-stranding-avatar-v0-xko8w25g6hoc1.jpeg?width=640&crop=smart&auto=webp&s=3a7c4abb973043fda1a7de52861c18d1c71c6e24",
  },
  {
    id: "3",
    name: "Giovanni Martins",
    role: "Design",
    avatar:
      "https://i.redd.it/profile-pic-v0-ha9nm0ge6nvb1.jpg?width=5000&format=pjpg&auto=webp&s=5a30483076018e7ebdcd87ff86c3565de525bc86",
  },
];

export function ProjectDetails({
  project,
  open,
  onOpenChange,
}: ProjectDetailsProps) {
  const [isTeamsOpen, setIsTeamsOpen] = useState(false);
  const hasServers = project?.servers?.length > 0;

  return (
    <Dialog.Root open={open} onOpenChange={onOpenChange}>
      <Dialog.Content size="4" className="rounded-xl shadow-lg">
        <Flex direction="column" gap="4">
          {/* Title */}
          <Dialog.Title>
            <Text size="4" weight="bold">
              {project.name}
            </Text>
          </Dialog.Title>

          {/* Servers Section */}
          <Card variant="surface">
            <Flex direction="column" gap="2">
              <Text size="1" weight="medium" color="gray">
                Servers
              </Text>

              {hasServers ? (
                <Flex direction="column" gap="2">
                  {project.servers.map((server: ServerPresenter) => (
                    <Badge key={server.id} variant="surface" size="2">
                      {server.name}
                    </Badge>
                  ))}
                </Flex>
              ) : (
                <Flex justify="between" align="center">
                  <Text size="2" color="gray">
                    No servers registered yet.
                  </Text>
                  <Button variant="soft" size="1">
                    Go to manage
                  </Button>
                </Flex>
              )}
            </Flex>
          </Card>

          {/* Details Section */}
          <Card variant="surface">
            <Flex direction="column" gap="2">
              <Text size="1" weight="medium" color="gray">
                Project Details
              </Text>

              <Text size="2" color="gray">
                {project.details || "No description available."}
              </Text>
            </Flex>
          </Card>

          <Separator my="1" />

          {/* Teams Accordion Section */}
          <Box>
            {/* Accordion Header */}
            <Button
              onClick={() => setIsTeamsOpen(!isTeamsOpen)}
              className="flex items-center justify-between w-full text-left focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 rounded-md p-1 transition-colors hover:bg-gray-800 mb-2"
            >
              <Text size="1" weight="medium" color="gray">
                Team Members ({mockTeamMembers.length})
              </Text>
              <ChevronDownIcon
                className={`w-4 h-4 transition-transform duration-200 ${isTeamsOpen ? "rotate-180" : ""
                  }`}
              />
            </Button>

            {/* Accordion Content */}
            <div
              className={`overflow-hidden transition-all duration-300 ease-in-out ${isTeamsOpen ? "max-h-96 opacity-100" : "max-h-0 opacity-0"
                }`}
            >
              <div className="pt-1">
                <Flex direction="column" gap="2">
                  {mockTeamMembers.map((member) => (
                    <Box
                      key={member.id}
                      className="animate-in fade-in-0 slide-in-from-top-1 duration-200"
                    >
                      <Card>
                        <Flex gap="2" align="center" p="2">
                          <Avatar
                            size="2"
                            src={member.avatar}
                            radius="full"
                            fallback={member.name[0]}
                          />
                          <Box>
                            <Text as="div" size="1" weight="bold">
                              {member.name}
                            </Text>
                            <Text as="div" size="1" color="gray">
                              {member.role}
                            </Text>
                          </Box>
                        </Flex>
                      </Card>
                    </Box>
                  ))}
                </Flex>
              </div>
            </div>
          </Box>

          <Separator my="1" />

          {/* Footer */}
          <Flex justify="end">
            <Button variant="outline" size="2" onClick={() => onOpenChange(false)}>
              Close
            </Button>
          </Flex>
        </Flex>
      </Dialog.Content>
    </Dialog.Root>
  );
}
