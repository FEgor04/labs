import {HistoryEntry} from "../model.ts";

export interface HistoryManager {
    push(entry: HistoryEntry): void
    get(): HistoryEntry[]
    getLastEntry(): HistoryEntry | undefined
}