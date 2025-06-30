import {
  Dialog,
  Flex,
  Text,
  TextField,
  Button,
  Strong,
  Box,
} from "@radix-ui/themes";
import { useState } from "react";

interface ChangeUsernameProps {
  userName: string;
  onUsernameChange: (username: string) => void;
  open: boolean;
  onOpenChange: (open: boolean) => void;
}

export function ChangeUsername({
  userName,
  onUsernameChange,
  open,
  onOpenChange
}: ChangeUsernameProps) {
  const [newUsername, setNewUsername] = useState(userName);
  const [isValid, setIsValid] = useState(true);
  const [errorMessage, setErrorMessage] = useState("");

  const validateUsername = (username: string) => {
    if (username.length < 3) {
      setErrorMessage("Username must be at least 3 characters long");
      setIsValid(false);
      return false;
    }
    if (username.length > 20) {
      setErrorMessage("Username must be less than 20 characters");
      setIsValid(false);
      return false;
    }
    if (!/^[a-zA-Z0-9_]+$/.test(username)) {
      setErrorMessage("Username can only contain letters, numbers, and underscores");
      setIsValid(false);
      return false;
    }
    setErrorMessage("");
    setIsValid(true);
    return true;
  };

  const handleUsernameChange = (value: string) => {
    setNewUsername(value);
    validateUsername(value);
  };

  const handleSave = () => {
    if (validateUsername(newUsername) && newUsername !== userName) {
      onUsernameChange(newUsername);
      onOpenChange(false);
    }
  };

  const handleCancel = () => {
    setNewUsername(userName);
    setErrorMessage("");
    setIsValid(true);
    onOpenChange(false);
  };

  const hasChanges = newUsername !== userName;

  return (
    <Dialog.Root open={open} onOpenChange={onOpenChange}>
      <Dialog.Content maxWidth="450px">
        <Dialog.Title>Change Username</Dialog.Title>
        <Dialog.Description size="2" mb="4">
          Choose a new username for your account. This will be visible to other users.
        </Dialog.Description>

        <Flex direction="column" gap="3">
          <Box>
            <Text as="label" size="2" htmlFor="username" mb="1">
              <Strong>Username</Strong>
            </Text>
            <TextField.Root
              id="username"
              placeholder="Enter new username"
              value={newUsername}
              onChange={(e) => handleUsernameChange(e.target.value)}
              color={!isValid ? "red" : undefined}
            >
              <TextField.Slot>@</TextField.Slot>
            </TextField.Root>
            {!isValid && (
              <Text size="2" color="red" mt="1">
                {errorMessage}
              </Text>
            )}
          </Box>
        </Flex>

        <Flex gap="3" mt="4" justify="end">
          <Dialog.Close>
            <Button variant="soft" color="gray" onClick={handleCancel}>
              Cancel
            </Button>
          </Dialog.Close>
          <Button
            onClick={handleSave}
            disabled={!isValid || !hasChanges}
          >
            Save Changes
          </Button>
        </Flex>
      </Dialog.Content>
    </Dialog.Root>
  );
}
