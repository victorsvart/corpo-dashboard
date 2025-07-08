import type { ProjectPresenter } from "~/types/project/project-presenter";
import {
  Dialog,
  Flex,
  Text,
  Box,
  Card,
  Button,
} from "@radix-ui/themes";
import type { ServerPresenter } from "~/types/server/server-presenter";

interface ProjectDetailsProps {
  project: ProjectPresenter;
  open: boolean;
  onOpenChange: (open: boolean) => void;
}

export function ProjectDetails({
  project,
  open,
  onOpenChange,
}: ProjectDetailsProps) {
  const handleClose = () => {
    onOpenChange(false);
  };

  return (
    <Dialog.Root open={open} onOpenChange={onOpenChange}>
      <Dialog.Content maxWidth="450px">
        <Dialog.Title>{project.name}</Dialog.Title>

        <Card variant="surface" mt="3">
          <Flex direction="column" gap="3">
            {project.servers.length > 0 && (
              <Box>
                <Flex direction="column" gap="3">
                  <Text size="4" weight="bold" mb="1">Servers</Text>
                  <Flex direction="column" gap="1">
                    {project.servers.map((server: ServerPresenter) => (
                      <Text key={server.id} size="2">• {server.name}</Text>
                    ))}
                  </Flex>
                </Flex>
              </Box>
            )}
          </Flex>
        </Card>

        <Card variant="surface" mt="3">
          <Flex direction="column" gap="3">
            <Box>
              <Text size="2" weight="bold" mb="1">Details: </Text>
              <Text size="2" color="gray">{project.details}</Text>
            </Box>
          </Flex>
        </Card>

        <Flex justify="end" mt="4">
          <Button variant="outline" size="2" type="button" onClick={handleClose}>
            Close
          </Button>
        </Flex>
      </Dialog.Content>
    </Dialog.Root>
  );
}
