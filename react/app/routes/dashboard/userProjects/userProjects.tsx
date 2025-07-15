import {
	Badge,
	Box,
	Button,
	Card,
	Flex,
	Grid,
	Heading,
	IconButton,
	Separator,
	Text,
} from "@radix-ui/themes";
import { useState } from "react";
import type { ProjectPresenter } from "~/types/project/project-presenter";
import { ProjectDetails } from "./projectDetails/projectDetails";
import { ProjectManagement } from "./projectManagement/projectManagement";

type Props = {
	projects: ProjectPresenter[];
};

export default function UserProjects({ projects }: Props) {
	const [selectedProject, setSelectedProject] =
		useState<ProjectPresenter | null>(null);
	const [managmentOpen, setManagementOpen] = useState(false);
	const [detailsOpen, setDetailsOpen] = useState(false);

	if (!projects || projects.length === 0) {
		return (
			<Card variant="surface" className="p-8 text-center">
				<Box className="w-16 h-16 bg-blue-100 rounded-full mx-auto mb-4 flex items-center justify-center">
					<Box className="w-8 h-8 bg-blue-500 rounded" />
				</Box>

				<Heading size="4" mb="2">
					No Projects Yet
				</Heading>

				<Text size="3" color="gray" mb="4">
					Get started by creating your first project. Projects help you organize
					your servers and deployments.
				</Text>

				<Button size="2">Create Your First Project</Button>
			</Card>
		);
	}

	return (
		<Box>
			{/* Projects Grid */}
			<Grid columns={{ initial: "1", sm: "2", lg: "3" }} gap="4">
				{projects.map(project => (
					<Card
						key={project.id}
						variant="surface"
						className="p-0 overflow-hidden hover:shadow-md transition-shadow"
					>
						<Box className="p-4">
							<Flex justify="between" align="start" mb="2">
								<Box className="flex-1 min-w-0">
									<Heading size="4" weight="medium" className="truncate">
										{project.name}
									</Heading>
									<Text size="2" color="gray">
										Owner: Victor Moraes
									</Text>
								</Box>

								<IconButton size="1" variant="ghost" className="ml-2">
									<Box className="w-1 h-1 bg-gray-500 rounded-full" />
									<Box className="w-1 h-1 bg-gray-500 rounded-full" />
									<Box className="w-1 h-1 bg-gray-500 rounded-full" />
								</IconButton>
							</Flex>
						</Box>

						<Box className="p-4">
							<Flex align="center" gap="2" mb="3">
								<Box className="w-2 h-2 bg-green-500 rounded-full" />
								<Text size="2" weight="medium">
									{project.servers?.length || 0} Server
									{project.servers?.length !== 1 ? "s" : ""}
								</Text>
							</Flex>

							{project.servers && project.servers.length > 0 && (
								<Box mb="4">
									<Text size="1" color="gray" mb="2" weight="medium">
										SERVERS
									</Text>
									<Flex direction="column" gap="1">
										{project.servers
											.slice(0, 3)
											.map((server: any, index: number) => (
												<Flex
													key={index}
													align="center"
													gap="2"
													className="py-1"
												>
													<Box
														className={
															(server.active ? "bg-green-400" : "bg-red-400") +
															" w-1.5 h-1.5 rounded-full"
														}
													/>
													<Text size="2" className="flex-1 truncate">
														{server.name || `Server ${index + 1}`}
													</Text>
													<Badge
														color={server.active ? "indigo" : "red"}
														variant="soft"
														size="1"
													>
														{server.active ? "Active" : "Inactive"}
													</Badge>
												</Flex>
											))}

										{project.servers.length > 3 && (
											<Text size="1" color="gray" className="pl-4">
												+{project.servers.length - 3} more servers
											</Text>
										)}
									</Flex>
								</Box>
							)}

							<Separator className="my-3" />

							<Flex gap="2">
								<Button
									size="2"
									variant="soft"
									className="flex-1"
									onClick={() => {
										setSelectedProject(project);
										setDetailsOpen(true);
									}}
								>
									View Details
								</Button>
								<Button
									size="2"
									variant="outline"
									className="flex-1"
									onClick={() => {
										setSelectedProject(project);
										setManagementOpen(true);
									}}
								>
									Manage
								</Button>
							</Flex>
						</Box>

						<Box className="px-4 py-2 border-t">
							<Flex justify="between" align="center">
								<Flex
									align="center"
									gap="2"
									hidden={project.servers.length < 1}
								>
									<Box
										className={`w-2 h-2 rounded-full animate-pulse ${
											project.status === "Healthy"
												? "bg-green-500"
												: project.status === "Deploying"
												? "bg-yellow-400"
												: "bg-gray-400"
										}`}
									/>
									<Text size="1" color="gray">
										{project.status}
									</Text>
								</Flex>

								<Text size="1" color="gray" hidden={project.servers.length < 1}>
									{`Last update: ${project.lastUpdate} ago`}
								</Text>
							</Flex>
						</Box>
					</Card>
				))}
			</Grid>

			{/* Only one modal outside the loop */}
			{selectedProject && detailsOpen && (
				<ProjectDetails
					project={selectedProject}
					open={true}
					onOpenChange={open => {
						if (!open) {
							setDetailsOpen(false);
							setSelectedProject(null);
						}
					}}
				/>
			)}

			{/* Only one modal outside the loop */}
			{selectedProject && managmentOpen && (
				<ProjectManagement
					project={selectedProject}
					open={true}
					onOpenChange={open => {
						if (!open) {
							setManagementOpen(false);
							setSelectedProject(null);
						}
					}}
				/>
			)}

			{/* Quick Stats */}
			<Card variant="surface" className="mt-6 p-4">
				<Heading size="4" mb="3">
					Quick Overview
				</Heading>
				<Grid columns={{ initial: "2", sm: "4" }} gap="4">
					<Box className="text-center">
						<Text size="6" weight="bold" className="block text-blue-600">
							{projects.length}
						</Text>
						<Text size="2" color="gray">
							Total Projects
						</Text>
					</Box>
					<Box className="text-center">
						<Text size="6" weight="bold" className="block text-green-600">
							{projects.reduce(
								(total, project) => total + (project.servers?.length || 0),
								0
							)}
						</Text>
						<Text size="2" color="gray">
							Total Servers
						</Text>
					</Box>
					<Box className="text-center">
						<Text size="6" weight="bold" className="block text-purple-600">
							{projects.filter(p => p.servers && p.servers.length > 0).length}
						</Text>
						<Text size="2" color="gray">
							Active Projects
						</Text>
					</Box>
					<Box className="text-center">
						<Text size="6" weight="bold" className="block text-orange-600">
							{Math.round(
								(projects.reduce(
									(total, project) => total + (project.servers?.length || 0),
									0
								) /
									projects.length) *
									10
							) / 10}
						</Text>
						<Text size="2" color="gray">
							Avg Servers/Project
						</Text>
					</Box>
				</Grid>
			</Card>

			{/* Recent Activity */}
			<Card variant="surface" className="mt-4 p-4">
				<Flex justify="between" align="center" mb="3">
					<Heading size="4">Recent Activity</Heading>
					<Button size="1" variant="ghost">
						View All
					</Button>
				</Flex>

				<Flex direction="column" gap="3">
					{projects.slice(0, 3).map((project, index) => (
						<Flex key={project.id} align="center" gap="3" className="py-2">
							<Box className="w-8 h-8 bg-blue-100 rounded-full flex items-center justify-center">
								<Text size="2" weight="bold" className="text-blue-600">
									{project.name.charAt(0)}
								</Text>
							</Box>
							<Box className="flex-1">
								<Text size="2" weight="medium">
									{project.name}
								</Text>
								<Text size="1" color="gray">
									{index === 0
										? "Server deployment completed"
										: index === 1
										? "Configuration updated"
										: "Health check passed"}
								</Text>
							</Box>
							<Text size="1" color="gray">
								{index === 0 ? "1h ago" : index === 1 ? "3h ago" : "5h ago"}
							</Text>
						</Flex>
					))}
				</Flex>
			</Card>
		</Box>
	);
}
