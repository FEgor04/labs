import {HistoryManager} from "./interface/historyManager.ts";
import {HistoryEntry} from "./model.ts";

export class StorageHistoryManager implements HistoryManager {
    storage: Storage

    constructor(storage: Storage) {
        this.storage = storage
    }

    get(): HistoryEntry[] {
        return JSON.parse(this.storage.getItem("history") ?? "[]")
    }

    push(entry: HistoryEntry): void {
        const json = JSON.parse(this.storage.getItem("history") ?? "[]")
        json.push(entry)
        this.storage.setItem("history", JSON.stringify(json))

    }

    getLastEntry(): HistoryEntry | undefined {
        const history = this.get()
        if(history.length > 0) {
            return history[history.length - 1]
        }
        return undefined

    }
}