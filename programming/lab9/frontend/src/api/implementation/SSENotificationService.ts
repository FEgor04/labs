import {
    NotificationCallback,
    NotificationService,
    parseNotifications
} from "../defs/notifications/NotificationService.tsx";
import {isElementOfType} from "react-dom/test-utils";

export class SSENotificationService implements NotificationService {
    private eventSource
    private callbacks: NotificationCallback[] = []

    addCallback(callback: NotificationCallback): void {
        this.callbacks.push(callback)
    }

    constructor() {
        this.setup()
    }

    private handleMessage(msg: MessageEvent<any>) {
        console.log()
        const notification = parseNotifications(msg.data)
        console.log(`New notification: ${JSON.stringify(notification)}`)
        this.callbacks.forEach(cb => {
            cb(notification)
        })
    }

    private setup() {
        this.eventSource = new EventSource("/api/notifications-stream")
        this.eventSource.onerror = (e: Event) => {
            console.log(`Event source error: ${e}`)
        }
        this.eventSource.onmessage = (msg) => {
            console.log(`New SSE message: ${msg.data}`)
            this.handleMessage(msg)
        }
        this.eventSource.onopen = e => console.log(`SSE connection open`)
    }

    reconnect(): void {
        this.eventSource.close()
        this.setup()
    }

}