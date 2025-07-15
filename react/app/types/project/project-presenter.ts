import type { ServerPresenter } from "../server/server-presenter";

export type ProjectPresenter = {
	id: number;
	name: string;
	servers: ServerPresenter[];
	status: string;
	lastUpdate: string;
	details: string;
};
