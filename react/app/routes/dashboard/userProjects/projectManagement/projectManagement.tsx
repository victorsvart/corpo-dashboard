import {
	Badge,
	Button,
	Card,
	Dialog,
	Flex,
	Separator,
	Text,
	TextField,
	TextArea,
	AlertDialog,
	DropdownMenu,
	IconButton,
	Tabs,
	Box,
	Switch,
	Avatar,
} from "@radix-ui/themes";
import {
	EllipsisHorizontalIcon,
	PlusIcon,
	TrashIcon,
	PencilIcon,
	CogIcon,
	ServerIcon,
	InformationCircleIcon,
	UserIcon,
	LockClosedIcon,
} from "@heroicons/react/24/outline";
import { useState } from "react";
import type { ProjectPresenter } from "~/types/project/project-presenter";
import type { ServerPresenter } from "~/types/server/server-presenter";

// Mock data
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

const mockActiveDeployments = [
	{
		id: "1",
		name: "Production v2.1.0",
		status: "running",
		server: "Server-01",
		deployedAt: "2 hours ago",
		branch: "main",
	},
	{
		id: "2",
		name: "Staging v2.2.0-beta",
		status: "running",
		server: "Server-02",
		deployedAt: "1 day ago",
		branch: "develop",
	},
	{
		id: "3",
		name: "Feature Branch",
		status: "failed",
		server: "Server-03",
		deployedAt: "3 days ago",
		branch: "feature/new-ui",
	},
];

interface ProjectManagementProps {
	project: ProjectPresenter;
	open: boolean;
	onOpenChange: (open: boolean) => void;
	onUpdateProject?: (project: Partial<ProjectPresenter>) => void;
	onDeleteProject?: (projectId: number) => void;
	onAddServer?: (projectId: number) => void;
	onRemoveServer?: (serverId: number) => void;
	onManageTeam?: (projectId: number) => void;
}

export function ProjectManagement({
	project,
	open,
	onOpenChange,
	onUpdateProject,
	onDeleteProject,
	onAddServer,
	onRemoveServer,
	onManageTeam,
}: ProjectManagementProps) {
	const [activeTab, setActiveTab] = useState("overview");
	const [editingProject, setEditingProject] = useState(false);
	const [editingName, setEditingName] = useState(project.name);
	const [editingDetails, setEditingDetails] = useState(project.details || "");
	const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);

	const hasServers = project?.servers?.length > 0;

	const handleSaveProject = () => {
		onUpdateProject?.({
			...project,
			name: editingName,
			details: editingDetails,
		});
		setEditingProject(false);
	};

	const handleCancelEdit = () => {
		setEditingName(project.name);
		setEditingDetails(project.details || "");
		setEditingProject(false);
	};

	const handleDeleteProject = () => {
		onDeleteProject?.(project.id);
		setDeleteDialogOpen(false);
		onOpenChange(false);
	};

	return (
		<>
			<Dialog.Root open={open} onOpenChange={onOpenChange}>
				<Dialog.Content size="4" className="rounded-xl shadow-lg max-w-2xl">
					<Flex direction="column" gap="4">
						{/* Header */}
						<Flex justify="between" align="center">
							<Dialog.Title>
								<Flex align="center" gap="2">
									<CogIcon className="w-5 h-5" />
									<Text size="4" weight="bold">
										{editingProject ? "Edit Project" : "Manage Project"}
									</Text>
								</Flex>
							</Dialog.Title>
							<DropdownMenu.Root>
								<DropdownMenu.Trigger>
									<Button variant="soft">
										Options
										<DropdownMenu.TriggerIcon />
									</Button>
								</DropdownMenu.Trigger>
								<DropdownMenu.Content>
									<DropdownMenu.Item onClick={() => setEditingProject(true)}>
										<PencilIcon className="w-4 h-4" />
										Edit Project
									</DropdownMenu.Item>
									<DropdownMenu.Item onClick={() => onManageTeam?.(project.id)}>
										<UserIcon className="w-4 h-4" />
										Manage Team
									</DropdownMenu.Item>
									<DropdownMenu.Separator />
									<DropdownMenu.Item
										color="red"
										onClick={() => setDeleteDialogOpen(true)}
									>
										<TrashIcon className="w-4 h-4" />
										Delete Project
									</DropdownMenu.Item>
								</DropdownMenu.Content>
							</DropdownMenu.Root>
						</Flex>

						{/* Project Name & Description */}
						<Card variant="surface">
							<Flex direction="column" gap="3">
								{editingProject ? (
									<>
										<Box>
											<Text size="2" weight="medium" color="gray" mb="1">
												Project Name
											</Text>
											<TextField.Root
												value={editingName}
												onChange={e => setEditingName(e.target.value)}
												placeholder="Enter project name"
											/>
										</Box>
										<Box>
											<Text size="2" weight="medium" color="gray" mb="1">
												Description
											</Text>
											<TextArea
												value={editingDetails}
												onChange={e => setEditingDetails(e.target.value)}
												placeholder="Enter project description"
												rows={3}
											/>
										</Box>
										<Flex gap="2" justify="end">
											<Button variant="soft" onClick={handleCancelEdit}>
												Cancel
											</Button>
											<Button onClick={handleSaveProject}>Save Changes</Button>
										</Flex>
									</>
								) : (
									<>
										<Flex justify="between" align="start">
											<Box>
												<Text size="3" weight="bold">
													{project.name}
												</Text>
											</Box>
											<Badge variant="soft" color="green">
												Active
											</Badge>
										</Flex>
										<Text size="2" color="gray" mt="1">
											{project.details || "No description available."}
										</Text>
									</>
								)}
							</Flex>
						</Card>

						{/* Tabs */}
						<Tabs.Root value={activeTab} onValueChange={setActiveTab}>
							<Tabs.List>
								<Tabs.Trigger value="overview">
									<InformationCircleIcon className="w-4 h-4" /> Overview
								</Tabs.Trigger>
								<Tabs.Trigger value="servers">
									<ServerIcon className="w-4 h-4" /> Servers (
									{project.servers?.length || 0})
								</Tabs.Trigger>
								<Tabs.Trigger value="settings">
									<LockClosedIcon className="w-4 h-4" />
									Settings
								</Tabs.Trigger>
							</Tabs.List>

							{/* Overview Tab */}
							<Tabs.Content value="overview">
								<Flex direction="column" gap="3">
									<Card variant="surface">
										<Flex direction="column" gap="2">
											<Text size="2" weight="medium">
												Project Statistics
											</Text>
											<Flex gap="6">
												<Box>
													<Text size="3" weight="bold">
														{project.servers?.length || 0}{" "}
													</Text>
													<Text size="1" color="gray">
														Servers
													</Text>
												</Box>
												<Box>
													<Text size="3" weight="bold">
														{mockActiveDeployments.length}{" "}
													</Text>
													<Text size="1" color="gray">
														Active Deployments
													</Text>
												</Box>
												<Box>
													<Text size="3" weight="bold">
														{mockTeamMembers.length}{" "}
													</Text>
													<Text size="1" color="gray">
														Team Members
													</Text>
												</Box>
											</Flex>
										</Flex>
									</Card>

									<Card variant="surface">
										<Flex direction="column" gap="3">
											<Text size="2" weight="medium">
												Active Deployments
											</Text>
											{mockActiveDeployments.length > 0 ? (
												<Flex direction="column" gap="2">
													{mockActiveDeployments.map(deployment => (
														<Card key={deployment.id} variant="classic">
															<Flex justify="between" align="center">
																<Flex direction="column" gap="1">
																	<Text size="2" weight="medium">
																		{deployment.name}
																	</Text>
																	<Text size="1" color="gray">
																		{deployment.server} • {deployment.branch} •{" "}
																		{deployment.deployedAt}
																	</Text>
																</Flex>
																<Badge
																	variant="soft"
																	color={
																		deployment.status === "running"
																			? "green"
																			: deployment.status === "failed"
																			? "red"
																			: "gray"
																	}
																>
																	{deployment.status}
																</Badge>
															</Flex>
														</Card>
													))}
												</Flex>
											) : (
												<Text size="2" color="gray">
													No active deployments.
												</Text>
											)}
										</Flex>
									</Card>

									<Card variant="surface">
										<Flex direction="column" gap="3">
											<Text size="2" weight="medium">
												Team Members
											</Text>
											{mockTeamMembers.length > 0 ? (
												<Flex direction="column" gap="2">
													{mockTeamMembers.map(member => (
														<Card key={member.id} variant="classic">
															<Flex gap="3" align="center" p="2">
																<Avatar
																	size="2"
																	src={member.avatar}
																	radius="full"
																	fallback={member.name[0]}
																/>
																<Box>
																	<Text as="div" size="2" weight="medium">
																		{member.name}
																	</Text>
																	<Text as="div" size="1" color="gray">
																		{member.role}
																	</Text>
																</Box>
															</Flex>
														</Card>
													))}
												</Flex>
											) : (
												<Text size="2" color="gray">
													No team members found.
												</Text>
											)}
										</Flex>
									</Card>
								</Flex>
							</Tabs.Content>

							{/* Servers Tab */}
							<Tabs.Content value="servers">
								<Card variant="surface">
									<Flex direction="column" gap="3">
										<Flex justify="between" align="center">
											<Text size="2" weight="medium">
												Server Management
											</Text>
											<Button
												variant="soft"
												size="2"
												onClick={() => onAddServer?.(project.id)}
											>
												<PlusIcon className="w-4 h-4" />
												Add Server
											</Button>
										</Flex>

										{hasServers ? (
											<Flex direction="column" gap="2">
												{project.servers.map((server: ServerPresenter) => (
													<Card key={server.id} variant="classic">
														<Flex justify="between" align="center">
															<Flex direction="column" gap="1">
																<Text size="2" weight="medium">
																	{server.name}
																</Text>
																<Text size="1" color="gray">
																	{server.status || "Unknown"} •{" "}
																	{server.region || "No region"}
																</Text>
															</Flex>
															<Flex gap="2" align="center">
																<Badge
																	variant="soft"
																	color={
																		server.status === "online"
																			? "green"
																			: "gray"
																	}
																>
																	{server.status || "offline"}
																</Badge>
																<DropdownMenu.Root>
																	<DropdownMenu.Trigger>
																		<IconButton
																			variant="ghost"
																			size="1"
																		></IconButton>
																	</DropdownMenu.Trigger>
																	<DropdownMenu.Content>
																		<DropdownMenu.Item>
																			Configure
																		</DropdownMenu.Item>
																		<DropdownMenu.Item>
																			View Logs
																		</DropdownMenu.Item>
																		<DropdownMenu.Separator />
																		<DropdownMenu.Item
																			color="red"
																			onClick={() =>
																				onRemoveServer?.(server.id)
																			}
																		>
																			Remove
																		</DropdownMenu.Item>
																	</DropdownMenu.Content>
																</DropdownMenu.Root>
															</Flex>
														</Flex>
													</Card>
												))}
											</Flex>
										) : (
											<Flex
												direction="column"
												align="center"
												justify="center"
												gap="3"
												py="6"
											>
												<ServerIcon className="w-8 h-8 text-gray-400" />
												<Text size="2" color="gray" align="center">
													No servers registered yet.
													<br />
													Add your first server to get started.
												</Text>
												<Button
													variant="soft"
													onClick={() => onAddServer?.(project.id)}
												>
													<PlusIcon className="w-4 h-4" />
													Add Server
												</Button>
											</Flex>
										)}
									</Flex>
								</Card>
							</Tabs.Content>

							{/* Settings Tab */}
							<Tabs.Content value="settings">
								<Flex direction="column" gap="3">
									<Card variant="surface">
										<Flex direction="column" gap="3">
											<Text size="2" weight="medium">
												Project Settings
											</Text>

											<Flex justify="between" align="center">
												<Box>
													<Text
														size="2"
														weight="medium"
														className="block md:inline"
													>
														Auto-deploy{" "}
													</Text>
													<Text
														size="1"
														color="gray"
														className="block md:inline"
													>
														Automatically deploy changes when code is pushed
													</Text>
												</Box>
												<Switch defaultChecked />
											</Flex>

											<Flex justify="between" align="center">
												<Box>
													<Text size="2" weight="medium">
														Email notifications{" "}
													</Text>
													<Text
														size="1"
														color="gray"
														className="block md:inline"
													>
														Receive email updates about deployments
													</Text>
												</Box>
												<Switch />
											</Flex>

											<Flex justify="between" align="center">
												<Box>
													<Text size="2" weight="medium">
														Public access{" "}
													</Text>
													<Text size="1" color="gray">
														Allow public access to project resources
													</Text>
												</Box>
												<Switch />
											</Flex>
										</Flex>
									</Card>

									<Card variant="surface">
										<Flex direction="column" gap="3">
											<Text size="2" weight="medium" color="red">
												Danger Zone
											</Text>
											<Flex justify="between" align="center">
												<Box>
													<Text size="2" weight="medium">
														Delete Project
													</Text>
													<Text size="1" color="gray">
														Permanently delete this project and all associated
														data
													</Text>
												</Box>
												<Button
													variant="soft"
													color="red"
													onClick={() => setDeleteDialogOpen(true)}
												>
													Delete
												</Button>
											</Flex>
										</Flex>
									</Card>
								</Flex>
							</Tabs.Content>
						</Tabs.Root>

						<Separator my="1" />

						{/* Footer */}
						<Flex justify="end">
							<Button
								variant="outline"
								size="2"
								onClick={() => onOpenChange(false)}
							>
								Close
							</Button>
						</Flex>
					</Flex>
				</Dialog.Content>
			</Dialog.Root>

			{/* Delete Confirmation Dialog */}
			<AlertDialog.Root
				open={deleteDialogOpen}
				onOpenChange={setDeleteDialogOpen}
			>
				<AlertDialog.Content>
					<AlertDialog.Title>Delete Project</AlertDialog.Title>
					<AlertDialog.Description>
						Are you sure you want to delete "{project.name}"? This action cannot
						be undone. All servers, deployments, and associated data will be
						permanently removed.
					</AlertDialog.Description>
					<Flex gap="3" mt="4" justify="end">
						<AlertDialog.Cancel>
							<Button variant="soft" color="gray">
								Cancel
							</Button>
						</AlertDialog.Cancel>
						<AlertDialog.Action>
							<Button variant="solid" color="red" onClick={handleDeleteProject}>
								Delete Project
							</Button>
						</AlertDialog.Action>
					</Flex>
				</AlertDialog.Content>
			</AlertDialog.Root>
		</>
	);
}
